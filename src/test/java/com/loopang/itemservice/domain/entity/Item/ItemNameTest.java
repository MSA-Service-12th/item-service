package com.loopang.itemservice.domain.entity.Item;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("ItemName 단위 테스트")
class ItemNameTest {

    @Test
    @DisplayName("유효한 이름으로 ItemName 생성 성공")
    void of_validName_success() {
        ItemName itemName = ItemName.of("테스트 상품");

        assertThat(itemName.getName()).isEqualTo("테스트 상품");
    }

    @Test
    @DisplayName("이름은 대문자로 정규화됨")
    void of_lowercaseName_normalizedToUppercase() {
        ItemName itemName = ItemName.of("test product");

        assertThat(itemName.getName()).isEqualTo("TEST PRODUCT");
    }

    @Test
    @DisplayName("이름 앞뒤 공백은 trim으로 제거됨")
    void of_nameWithLeadingTrailingSpaces_trimmed() {
        ItemName itemName = ItemName.of("  ITEM  ");

        assertThat(itemName.getName()).isEqualTo("ITEM");
    }

    @Test
    @DisplayName("이름 소문자와 앞뒤 공백이 함께 있어도 정규화됨")
    void of_lowercaseWithSpaces_normalizedCorrectly() {
        ItemName itemName = ItemName.of("  test item  ");

        assertThat(itemName.getName()).isEqualTo("TEST ITEM");
    }

    @Test
    @DisplayName("null 이름이면 NullPointerException 또는 IllegalArgumentException 발생")
    void of_nullName_throwsException() {
        // normalize() calls name.trim() which throws NPE when name is null
        assertThatThrownBy(() -> ItemName.of(null))
                .isInstanceOf(RuntimeException.class);
    }

    @ValueSource(strings = {"", "   ", "\t", "\n"})
    @ParameterizedTest(name = "이름이 공백 문자열 \"{0}\"이면 예외 발생")
    @DisplayName("공백으로만 이루어진 이름이면 예외 발생")
    void of_blankName_throwsException(String blankName) {
        assertThatThrownBy(() -> ItemName.of(blankName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품명은 필수이며 공백은 불가합니다.");
    }

    @Test
    @DisplayName("255자 이름 생성 성공 (경계값)")
    void of_exactly255Chars_success() {
        String name = "A".repeat(255);
        ItemName itemName = ItemName.of(name);

        assertThat(itemName.getName()).hasSize(255);
    }

    @Test
    @DisplayName("256자 이름이면 예외 발생 (경계값 초과)")
    void of_256Chars_throwsException() {
        String name = "A".repeat(256);

        assertThatThrownBy(() -> ItemName.of(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품명은 255자 이하입니다.");
    }

    @Test
    @DisplayName("공백 포함 256자 이름은 trim 후 255자 이하면 생성 성공")
    void of_256CharsWithLeadingSpaceTrimmedTo255_success() {
        // " " + "A"*255 → trim → "A"*255 which is exactly 255 chars
        String name = " " + "A".repeat(255);
        ItemName itemName = ItemName.of(name);

        assertThat(itemName.getName()).hasSize(255);
    }

    @Test
    @DisplayName("같은 이름으로 생성한 ItemName은 동등함")
    void equals_sameNormalizedName_areEqual() {
        ItemName name1 = ItemName.of("product");
        ItemName name2 = ItemName.of("PRODUCT");

        assertThat(name1).isEqualTo(name2);
    }

    @Test
    @DisplayName("다른 이름으로 생성한 ItemName은 동등하지 않음")
    void equals_differentNames_areNotEqual() {
        ItemName name1 = ItemName.of("PRODUCT A");
        ItemName name2 = ItemName.of("PRODUCT B");

        assertThat(name1).isNotEqualTo(name2);
    }

    @Test
    @DisplayName("같은 이름의 ItemName은 동일한 hashCode를 가짐")
    void hashCode_sameNormalizedName_sameHashCode() {
        ItemName name1 = ItemName.of("item");
        ItemName name2 = ItemName.of("ITEM");

        assertThat(name1.hashCode()).isEqualTo(name2.hashCode());
    }

    @Test
    @DisplayName("단일 문자 이름도 생성 성공")
    void of_singleCharName_success() {
        ItemName itemName = ItemName.of("A");

        assertThat(itemName.getName()).isEqualTo("A");
    }
}