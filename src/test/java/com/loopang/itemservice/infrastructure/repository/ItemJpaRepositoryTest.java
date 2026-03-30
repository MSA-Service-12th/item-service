package com.loopang.itemservice.infrastructure.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.loopang.itemservice.domain.entity.Item.Item;
import com.loopang.itemservice.domain.entity.Item.ItemName;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@Import(JpaTestConfig.class)
@ActiveProfiles("test")
@DisplayName("ItemJpaRepository 통합 테스트")
class ItemJpaRepositoryTest {

    @Autowired
    private ItemJpaRepository itemJpaRepository;

    private static final UUID COMPANY_ID = UUID.randomUUID();
    private static final String COMPANY_NAME = "테스트 업체";
    private static final UUID HUB_ID = UUID.randomUUID();
    private static final String HUB_NAME = "서울 허브";

    private Item createTestItem(String itemNameStr) {
        return Item.of(COMPANY_ID, COMPANY_NAME, HUB_ID, HUB_NAME, ItemName.of(itemNameStr));
    }

    @Test
    @DisplayName("Item 저장 후 UUID가 할당됨")
    void save_newItem_idAssigned() {
        Item item = createTestItem("저장 테스트 상품");

        Item saved = itemJpaRepository.save(item);

        assertThat(saved.getId()).isNotNull();
    }

    @Test
    @DisplayName("저장된 Item을 ID로 조회 성공")
    void findById_existingItem_returnsItem() {
        Item saved = itemJpaRepository.save(createTestItem("조회 테스트 상품"));

        Optional<Item> found = itemJpaRepository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(saved.getId());
    }

    @Test
    @DisplayName("존재하지 않는 ID로 조회하면 empty Optional 반환")
    void findById_nonExistentId_returnsEmpty() {
        Optional<Item> found = itemJpaRepository.findById(UUID.randomUUID());

        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("저장된 Item의 CompanyInfo 조회 성공")
    void save_item_companyInfoPersisted() {
        Item saved = itemJpaRepository.save(createTestItem("업체 정보 테스트"));

        Item found = itemJpaRepository.findById(saved.getId()).orElseThrow();

        assertThat(found.getCompanyInfo().getId()).isEqualTo(COMPANY_ID);
        assertThat(found.getCompanyInfo().getName()).isEqualTo(COMPANY_NAME);
    }

    @Test
    @DisplayName("저장된 Item의 HubInfo 조회 성공")
    void save_item_hubInfoPersisted() {
        Item saved = itemJpaRepository.save(createTestItem("허브 정보 테스트"));

        Item found = itemJpaRepository.findById(saved.getId()).orElseThrow();

        assertThat(found.getHubInfo().getId()).isEqualTo(HUB_ID);
        assertThat(found.getHubInfo().getName()).isEqualTo(HUB_NAME);
    }

    @Test
    @DisplayName("저장된 Item의 ItemName 조회 성공")
    void save_item_itemNamePersisted() {
        ItemName expectedName = ItemName.of("상품명 테스트");
        Item item = Item.of(COMPANY_ID, COMPANY_NAME, HUB_ID, HUB_NAME, expectedName);

        Item saved = itemJpaRepository.save(item);
        Item found = itemJpaRepository.findById(saved.getId()).orElseThrow();

        assertThat(found.getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("여러 Item 저장 후 전체 조회")
    void findAll_multipleItems_returnsAll() {
        itemJpaRepository.save(createTestItem("상품 A"));
        itemJpaRepository.save(createTestItem("상품 B"));
        itemJpaRepository.save(createTestItem("상품 C"));

        List<Item> items = itemJpaRepository.findAll();

        assertThat(items).hasSize(3);
    }

    @Test
    @DisplayName("Item 삭제 후 조회 시 empty Optional 반환")
    void delete_existingItem_notFoundAfterDeletion() {
        Item saved = itemJpaRepository.save(createTestItem("삭제 테스트 상품"));
        UUID savedId = saved.getId();

        itemJpaRepository.deleteById(savedId);

        assertThat(itemJpaRepository.findById(savedId)).isEmpty();
    }

    @Test
    @DisplayName("count()는 저장된 Item 수를 반환")
    void count_afterSavingItems_returnsCorrectCount() {
        itemJpaRepository.save(createTestItem("카운트 상품 1"));
        itemJpaRepository.save(createTestItem("카운트 상품 2"));

        long count = itemJpaRepository.count();

        assertThat(count).isEqualTo(2);
    }

    @Test
    @DisplayName("existsById()는 존재하는 Item에 대해 true 반환")
    void existsById_existingItem_returnsTrue() {
        Item saved = itemJpaRepository.save(createTestItem("존재 확인 상품"));

        assertThat(itemJpaRepository.existsById(saved.getId())).isTrue();
    }

    @Test
    @DisplayName("existsById()는 존재하지 않는 Item에 대해 false 반환")
    void existsById_nonExistentItem_returnsFalse() {
        assertThat(itemJpaRepository.existsById(UUID.randomUUID())).isFalse();
    }
}