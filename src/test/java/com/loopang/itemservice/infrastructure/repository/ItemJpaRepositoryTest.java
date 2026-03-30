package com.loopang.itemservice.infrastructure.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.loopang.itemservice.domain.entity.Item.Item;
import com.loopang.itemservice.domain.entity.Item.ItemName;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@Import(TestJpaAuditingConfig.class)
@TestPropertySource(properties = {
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect"
})
class ItemJpaRepositoryTest {

    @Autowired
    private ItemJpaRepository itemJpaRepository;

    private static final UUID COMPANY_ID = UUID.randomUUID();
    private static final String COMPANY_NAME = "테스트업체";
    private static final UUID HUB_ID = UUID.randomUUID();
    private static final String HUB_NAME = "서울 허브";

    @Test
    void save_Item_저장_후_id_발급() {
        Item item = Item.of(COMPANY_ID, COMPANY_NAME, HUB_ID, HUB_NAME, ItemName.of("상품명"));

        Item saved = itemJpaRepository.save(item);

        assertThat(saved.getId()).isNotNull();
    }

    @Test
    void findById_저장된_Item_조회() {
        Item item = Item.of(COMPANY_ID, COMPANY_NAME, HUB_ID, HUB_NAME, ItemName.of("조회테스트상품"));
        Item saved = itemJpaRepository.save(item);

        Optional<Item> found = itemJpaRepository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(saved.getId());
    }

    @Test
    void findById_존재하지_않는_id_빈_Optional_반환() {
        UUID nonExistentId = UUID.randomUUID();

        Optional<Item> found = itemJpaRepository.findById(nonExistentId);

        assertThat(found).isEmpty();
    }

    @Test
    void save_Item_companyInfo_정확히_저장됨() {
        Item item = Item.of(COMPANY_ID, COMPANY_NAME, HUB_ID, HUB_NAME, ItemName.of("업체정보테스트"));
        Item saved = itemJpaRepository.save(item);

        Item found = itemJpaRepository.findById(saved.getId()).orElseThrow();

        assertThat(found.getCompanyInfo().getId()).isEqualTo(COMPANY_ID);
        assertThat(found.getCompanyInfo().getName()).isEqualTo(COMPANY_NAME);
    }

    @Test
    void save_Item_hubInfo_정확히_저장됨() {
        Item item = Item.of(COMPANY_ID, COMPANY_NAME, HUB_ID, HUB_NAME, ItemName.of("허브정보테스트"));
        Item saved = itemJpaRepository.save(item);

        Item found = itemJpaRepository.findById(saved.getId()).orElseThrow();

        assertThat(found.getHubInfo().getId()).isEqualTo(HUB_ID);
        assertThat(found.getHubInfo().getName()).isEqualTo(HUB_NAME);
    }

    @Test
    void save_Item_name_정확히_저장됨() {
        ItemName itemName = ItemName.of("저장테스트상품");
        Item item = Item.of(COMPANY_ID, COMPANY_NAME, HUB_ID, HUB_NAME, itemName);
        Item saved = itemJpaRepository.save(item);

        Item found = itemJpaRepository.findById(saved.getId()).orElseThrow();

        assertThat(found.getName()).isEqualTo(itemName);
    }

    @Test
    void findAll_저장된_복수_Item_조회() {
        itemJpaRepository.save(Item.of(COMPANY_ID, COMPANY_NAME, HUB_ID, HUB_NAME, ItemName.of("상품1")));
        itemJpaRepository.save(Item.of(COMPANY_ID, COMPANY_NAME, HUB_ID, HUB_NAME, ItemName.of("상품2")));

        List<Item> items = itemJpaRepository.findAll();

        assertThat(items).hasSize(2);
    }

    @Test
    void delete_저장된_Item_삭제후_조회_불가() {
        Item item = Item.of(COMPANY_ID, COMPANY_NAME, HUB_ID, HUB_NAME, ItemName.of("삭제테스트상품"));
        Item saved = itemJpaRepository.save(item);
        UUID savedId = saved.getId();

        itemJpaRepository.deleteById(savedId);

        assertThat(itemJpaRepository.findById(savedId)).isEmpty();
    }

    @Test
    void count_저장된_Item_개수_반환() {
        itemJpaRepository.save(Item.of(COMPANY_ID, COMPANY_NAME, HUB_ID, HUB_NAME, ItemName.of("카운트상품1")));
        itemJpaRepository.save(Item.of(COMPANY_ID, COMPANY_NAME, HUB_ID, HUB_NAME, ItemName.of("카운트상품2")));
        itemJpaRepository.save(Item.of(COMPANY_ID, COMPANY_NAME, HUB_ID, HUB_NAME, ItemName.of("카운트상품3")));

        long count = itemJpaRepository.count();

        assertThat(count).isEqualTo(3);
    }
}