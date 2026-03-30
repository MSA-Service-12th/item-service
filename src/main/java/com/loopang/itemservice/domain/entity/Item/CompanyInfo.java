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

    public static CompanyInfo of(UUID companyId, String companyName) {
        validateInfo(companyId, companyName);

        return new CompanyInfo (companyId, companyName);
    }

    private static void validateInfo(UUID id, String companyName) {
        if (id == null) {
            throw new IllegalArgumentException("업체 ID는 필수입니다.");
        }

        if (companyName == null || companyName.isBlank()) {
            throw new IllegalArgumentException("업체명은 필수이며, 공백은 불가합니다.");
        }
    }
}
