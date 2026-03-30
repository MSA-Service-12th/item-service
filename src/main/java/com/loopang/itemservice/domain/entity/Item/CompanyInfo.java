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
     * Create a CompanyInfo for the given company identifier and name.
     *
     * @param companyId   the company UUID; must not be null
     * @param companyName the company name; must not be null or blank
     * @return            a new CompanyInfo instance with the provided id and name
     * @throws IllegalArgumentException if `companyId` is null or `companyName` is null or blank
     */
    public static CompanyInfo of(UUID companyId, String companyName) {
        validateInfo(companyId, companyName);

        return new CompanyInfo (companyId, companyName);
    }

    /**
     * Validates the company identifier and company name.
     *
     * @param id          the company UUID; must not be {@code null}
     * @param companyName the company name; must not be {@code null} or blank
     * @throws IllegalArgumentException if {@code id} is {@code null} or if {@code companyName} is {@code null} or blank
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
