package com.loopang.itemservice.domain.entity.Item;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Item 단위 테스트")
class ItemTest {

    private static final UUID COMPANY_ID = UUID.randomUUID();
    private static final String COMPANY_NAME = "테스트 업체";
    private static final UUID HUB_ID = UUID.randomUUID();
    private static final String HUB_NAME = "서울 허브";
    private static final ItemName ITEM_NAME = ItemName.of("테스트 상품");

    @Test
    @DisplayName("유효한 값으로 Item 생성 성공")
    void of_validInput_success() {
        Item item = Item.of(COMPANY_ID, COMPANY_NAME, HUB_ID, HUB_NAME, ITEM_NAME);

        assertThat(item).isNotNull();
        assertThat(item.getCompanyInfo()).isNotNull();
        assertThat(item.getHubInfo()).isNotNull();
        assertThat(item.getName()).isNotNull();
    }

    @Test
    @DisplayName("생성된 Item의 CompanyInfo 값 검증")
    void of_validInput_companyInfoCorrect() {
        Item item = Item.of(COMPANY_ID, COMPANY_NAME, HUB_ID, HUB_NAME, ITEM_NAME);

        assertThat(item.getCompanyInfo().getId()).isEqualTo(COMPANY_ID);
        assertThat(item.getCompanyInfo().getName()).isEqualTo(COMPANY_NAME);
    }

    @Test
    @DisplayName("생성된 Item의 HubInfo 값 검증")
    void of_validInput_hubInfoCorrect() {
        Item item = Item.of(COMPANY_ID, COMPANY_NAME, HUB_ID, HUB_NAME, ITEM_NAME);

        assertThat(item.getHubInfo().getId()).isEqualTo(HUB_ID);
        assertThat(item.getHubInfo().getName()).isEqualTo(HUB_NAME);
    }

    @Test
    @DisplayName("생성된 Item의 ItemName 값 검증")
    void of_validInput_itemNameCorrect() {
        Item item = Item.of(COMPANY_ID, COMPANY_NAME, HUB_ID, HUB_NAME, ITEM_NAME);

        assertThat(item.getName()).isEqualTo(ITEM_NAME);
    }

    @Test
    @DisplayName("새로 생성된 Item의 ID는 null (JPA 저장 전)")
    void of_newItem_idIsNull() {
        Item item = Item.of(COMPANY_ID, COMPANY_NAME, HUB_ID, HUB_NAME, ITEM_NAME);

        assertThat(item.getId()).isNull();
    }

    @Test
    @DisplayName("업체 ID가 null이면 예외 발생")
    void of_nullCompanyId_throwsException() {
        assertThatThrownBy(() -> Item.of(null, COMPANY_NAME, HUB_ID, HUB_NAME, ITEM_NAME))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("업체 ID는 필수입니다.");
    }

    @Test
    @DisplayName("업체명이 null이면 예외 발생")
    void of_nullCompanyName_throwsException() {
        assertThatThrownBy(() -> Item.of(COMPANY_ID, null, HUB_ID, HUB_NAME, ITEM_NAME))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("업체명은 필수이며, 공백은 불가합니다.");
    }

    @Test
    @DisplayName("업체명이 공백이면 예외 발생")
    void of_blankCompanyName_throwsException() {
        assertThatThrownBy(() -> Item.of(COMPANY_ID, "   ", HUB_ID, HUB_NAME, ITEM_NAME))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("업체명은 필수이며, 공백은 불가합니다.");
    }

    @Test
    @DisplayName("허브 ID가 null이면 예외 발생")
    void of_nullHubId_throwsException() {
        assertThatThrownBy(() -> Item.of(COMPANY_ID, COMPANY_NAME, null, HUB_NAME, ITEM_NAME))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("허브 정보는 필수입니다.");
    }

    @Test
    @DisplayName("허브 이름이 null이면 예외 발생")
    void of_nullHubName_throwsException() {
        assertThatThrownBy(() -> Item.of(COMPANY_ID, COMPANY_NAME, HUB_ID, null, ITEM_NAME))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("허브 정보는 필수입니다.");
    }

    @Test
    @DisplayName("ItemName이 null이면 NullPointerException 발생 없이 Item이 생성됨 (name 필드만 null)")
    void of_nullItemName_itemCreatedWithNullName() {
        // Item.of doesn't validate the ItemName parameter - it's assigned directly
        Item item = Item.of(COMPANY_ID, COMPANY_NAME, HUB_ID, HUB_NAME, null);

        assertThat(item.getName()).isNull();
    }

    @Test
    @DisplayName("서로 다른 업체/허브 정보로 생성한 Item은 독립적인 상태를 가짐")
    void of_differentInputs_createIndependentItems() {
        UUID anotherCompanyId = UUID.randomUUID();
        UUID anotherHubId = UUID.randomUUID();
        ItemName anotherName = ItemName.of("다른 상품");

        Item item1 = Item.of(COMPANY_ID, COMPANY_NAME, HUB_ID, HUB_NAME, ITEM_NAME);
        Item item2 = Item.of(anotherCompanyId, "다른 업체", anotherHubId, "부산 허브", anotherName);

        assertThat(item1.getCompanyInfo().getId()).isNotEqualTo(item2.getCompanyInfo().getId());
        assertThat(item1.getHubInfo().getId()).isNotEqualTo(item2.getHubInfo().getId());
        assertThat(item1.getName()).isNotEqualTo(item2.getName());
    }
}