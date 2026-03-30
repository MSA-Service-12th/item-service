package com.loopang.itemservice.domain.entity.Item;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access =AccessLevel.PROTECTED)
public class CompanyInfo {

    @Column(name = "company_id")
    private UUID id;

    @Column(length = 100, name = "company_name")
    private String name;

    /**
     * Creates a new CompanyInfo value object from the given company identifier and name.
     *
     * @param companyId   the company's UUID; must not be {@code null}
     * @param companyName the company's name; must not be {@code null} or blank
     * @return            a new CompanyInfo initialized with the provided id and name
     * @throws IllegalArgumentException if {@code companyId} is {@code null} or {@code companyName} is {@code null} or blank
     */
    public static CompanyInfo of(UUID companyId, String companyName) {
        validateInfo(companyId, companyName);

        return new CompanyInfo (companyId, companyName);
    }

    /**
     * Validates company identifier and name, throwing if either is invalid.
     *
     * @param id the company UUID to validate; must not be {@code null}
     * @param companyName the company name to validate; must not be {@code null} or blank
     * @throws IllegalArgumentException if {@code id} is {@code null}, or if {@code companyName} is {@code null} or blank
     */
    private static void validateInfo(UUID id, String companyName) {
        if (id == null) {
            throw new IllegalArgumentException("업체 ID는 필수입니다.");
        }

        if (companyName == null || companyName.isBlank()) {
            throw new IllegalArgumentException("업체명은 필수이며, 공백은 불가합니다.");
        }
    }
}
