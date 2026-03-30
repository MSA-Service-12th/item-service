package com.loopang.itemservice.domain.entity.Item;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.UUID;
import org.junit.jupiter.api.Test;

class ItemTest {

    private static final UUID COMPANY_ID = UUID.randomUUID();
    private static final String COMPANY_NAME = "테스트업체";
    private static final UUID HUB_ID = UUID.randomUUID();
    private static final String HUB_NAME = "서울 허브";
    private static final ItemName ITEM_NAME = ItemName.of("테스트상품");

    @Test
    void of_정상_파라미터로_Item_생성() {
        Item item = Item.of(COMPANY_ID, COMPANY_NAME, HUB_ID, HUB_NAME, ITEM_NAME);

        assertThat(item).isNotNull();
        assertThat(item.getCompanyInfo()).isNotNull();
        assertThat(item.getHubInfo()).isNotNull();
        assertThat(item.getName()).isNotNull();
    }

    @Test
    void of_companyInfo_필드가_올바르게_설정됨() {
        Item item = Item.of(COMPANY_ID, COMPANY_NAME, HUB_ID, HUB_NAME, ITEM_NAME);

        assertThat(item.getCompanyInfo().getId()).isEqualTo(COMPANY_ID);
        assertThat(item.getCompanyInfo().getName()).isEqualTo(COMPANY_NAME);
    }

    @Test
    void of_hubInfo_필드가_올바르게_설정됨() {
        Item item = Item.of(COMPANY_ID, COMPANY_NAME, HUB_ID, HUB_NAME, ITEM_NAME);

        assertThat(item.getHubInfo().getId()).isEqualTo(HUB_ID);
        assertThat(item.getHubInfo().getName()).isEqualTo(HUB_NAME);
    }

    @Test
    void of_name_필드가_올바르게_설정됨() {
        Item item = Item.of(COMPANY_ID, COMPANY_NAME, HUB_ID, HUB_NAME, ITEM_NAME);

        assertThat(item.getName()).isEqualTo(ITEM_NAME);
    }

    @Test
    void of_새로_생성된_Item은_id가_null() {
        Item item = Item.of(COMPANY_ID, COMPANY_NAME, HUB_ID, HUB_NAME, ITEM_NAME);

        assertThat(item.getId()).isNull();
    }

    @Test
    void of_companyId가_null이면_예외() {
        assertThatThrownBy(() -> Item.of(null, COMPANY_NAME, HUB_ID, HUB_NAME, ITEM_NAME))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("업체 ID는 필수입니다.");
    }

    @Test
    void of_companyName이_null이면_예외() {
        assertThatThrownBy(() -> Item.of(COMPANY_ID, null, HUB_ID, HUB_NAME, ITEM_NAME))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("업체명은 필수이며, 공백은 불가합니다.");
    }

    @Test
    void of_companyName이_공백이면_예외() {
        assertThatThrownBy(() -> Item.of(COMPANY_ID, "   ", HUB_ID, HUB_NAME, ITEM_NAME))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("업체명은 필수이며, 공백은 불가합니다.");
    }

    @Test
    void of_hubId가_null이면_예외() {
        assertThatThrownBy(() -> Item.of(COMPANY_ID, COMPANY_NAME, null, HUB_NAME, ITEM_NAME))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("허브 정보는 필수입니다.");
    }

    @Test
    void of_hubName이_null이면_예외() {
        assertThatThrownBy(() -> Item.of(COMPANY_ID, COMPANY_NAME, HUB_ID, null, ITEM_NAME))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("허브 정보는 필수입니다.");
    }

    @Test
    void of_서로_다른_Item은_별개_객체() {
        Item item1 = Item.of(COMPANY_ID, COMPANY_NAME, HUB_ID, HUB_NAME, ITEM_NAME);
        Item item2 = Item.of(COMPANY_ID, COMPANY_NAME, HUB_ID, HUB_NAME, ITEM_NAME);

        assertThat(item1).isNotSameAs(item2);
    }
}