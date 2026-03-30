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

    public static ItemName of(String name) {
        String normalized = normalize(name);
        validate(normalized);
        return new ItemName(normalized);
    }

    private static String normalize(String name) {
        return name.trim().toUpperCase();
    }

    private static void validate(String name) {
        if(name == null || name.isBlank()) {
            throw new IllegalArgumentException("상품명은 필수이며 공백은 불가합니다.");
        }
        if (name.length() > 255) {
            throw new IllegalArgumentException("상품명은 255자 이하입니다.");
        }
    }

}
