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
     * Creates an ItemName from the provided input by normalizing and validating it.
     *
     * @param name the raw item name to normalize and validate (may contain leading/trailing whitespace or mixed case)
     * @return the created ItemName with a normalized value (trimmed and converted to uppercase)
     * @throws IllegalArgumentException if the normalized name is null, blank, or longer than 255 characters
     */
    public static ItemName of(String name) {
        String normalized = normalize(name);
        validate(normalized);
        return new ItemName(normalized);
    }

    /**
     * Normalize an item name by removing leading and trailing whitespace and converting letters to upper case.
     *
     * @param name the input name to normalize
     * @return the normalized name with surrounding whitespace removed and letters converted to upper case
     */
    private static String normalize(String name) {
        return name.trim().toUpperCase();
    }

    /**
     * Validates a normalized item name and enforces required constraints.
     *
     * @param name the normalized item name (trimmed and uppercased)
     * @throws IllegalArgumentException if `name` is null or blank, or if its length is greater than 255
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
