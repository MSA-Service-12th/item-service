package com.loopang.itemservice.domain.entity.Item;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class ItemName {

    @Column(length = 255, nullable = false)
    private String name;

    /**
     * Create an ItemName from the given raw name by normalizing and validating it.
     *
     * @param name the input name to convert into an ItemName
     * @return the created ItemName whose stored value is normalized (trimmed and uppercased)
     * @throws IllegalArgumentException if {@code name} is null or blank, or if the normalized value is longer than 255 characters
     */
    public static ItemName of(String name) {
        validate(name);
        String normalized = normalize(name);
        return new ItemName(normalized);
    }

    /**
     * Normalize an item name by trimming leading/trailing whitespace and converting all characters to upper case.
     *
     * @param name the input name; must not be null
     * @return the trimmed, upper-case name
     */
    private static String normalize(String name) {
        return name.trim().toUpperCase();
    }

    /**
     * Validate an item name and throw an exception for invalid values.
     *
     * @param name the item name to validate
     * @throws IllegalArgumentException if {@code name} is {@code null} or blank
     * @throws IllegalArgumentException if {@code name.length() > 255}
     */
    private static void validate(String name) {
        if(name == null || name.isBlank()) {
            throw new IllegalArgumentException("상품명은 필수이며 공백은 불가합니다.");
        }
        if (name.length() > 255) {
            throw new IllegalArgumentException("상품명은 255자 이하입니다.");
        }
    }

}