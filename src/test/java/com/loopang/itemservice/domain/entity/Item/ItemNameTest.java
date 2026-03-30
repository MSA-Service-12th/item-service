package com.loopang.itemservice.domain.entity.Item;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class ItemNameTest {

    @Test
    void of_정상_이름으로_생성() {
        ItemName itemName = ItemName.of("테스트상품");

        assertThat(itemName.getName()).isEqualTo("테스트상품");
    }

    @Test
    void of_이름이_대문자로_정규화됨() {
        ItemName itemName = ItemName.of("apple iphone");

        assertThat(itemName.getName()).isEqualTo("APPLE IPHONE");
    }

    @Test
    void of_이름_앞뒤_공백이_제거됨() {
        ItemName itemName = ItemName.of("  상품명  ");

        assertThat(itemName.getName()).isEqualTo("상품명");
    }

    @Test
    void of_앞뒤_공백_제거_후_대문자_변환() {
        ItemName itemName = ItemName.of("  hello world  ");

        assertThat(itemName.getName()).isEqualTo("HELLO WORLD");
    }

    @Test
    void of_이름이_null이면_NullPointerException() {
        assertThatThrownBy(() -> ItemName.of(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void of_이름이_빈_문자열이면_예외() {
        assertThatThrownBy(() -> ItemName.of(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품명은 필수이며 공백은 불가합니다.");
    }

    @Test
    void of_이름이_공백만_있으면_예외() {
        assertThatThrownBy(() -> ItemName.of("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품명은 필수이며 공백은 불가합니다.");
    }

    @Test
    void of_255자_이름_정상_생성() {
        String name = "a".repeat(255);

        ItemName itemName = ItemName.of(name);

        assertThat(itemName.getName()).isEqualTo(name.toUpperCase());
    }

    @Test
    void of_255자_초과_이름이면_예외() {
        String name = "a".repeat(256);

        assertThatThrownBy(() -> ItemName.of(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품명은 255자 이하입니다.");
    }

    @Test
    void of_앞뒤_공백_포함하여_255자이하면_trim_후_정상_생성() {
        String name = "  " + "a".repeat(253) + "  ";

        ItemName itemName = ItemName.of(name);

        assertThat(itemName.getName()).hasSize(253);
    }

    @Test
    void equals_같은_이름은_동등() {
        ItemName name1 = ItemName.of("상품A");
        ItemName name2 = ItemName.of("상품A");

        assertThat(name1).isEqualTo(name2);
    }

    @Test
    void equals_대소문자_다르면_정규화_후_같은_이름으로_동등() {
        ItemName name1 = ItemName.of("apple");
        ItemName name2 = ItemName.of("APPLE");

        assertThat(name1).isEqualTo(name2);
    }

    @Test
    void equals_다른_이름은_불동등() {
        ItemName name1 = ItemName.of("상품A");
        ItemName name2 = ItemName.of("상품B");

        assertThat(name1).isNotEqualTo(name2);
    }

    @Test
    void hashCode_같은_이름은_같은_해시코드() {
        ItemName name1 = ItemName.of("상품A");
        ItemName name2 = ItemName.of("상품A");

        assertThat(name1.hashCode()).isEqualTo(name2.hashCode());
    }
}