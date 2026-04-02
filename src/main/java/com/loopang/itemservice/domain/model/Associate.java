package com.loopang.itemservice.domain.model;

import com.loopang.common.exception.CustomException;
import com.loopang.itemservice.domain.service.CompanyData;
import com.loopang.itemservice.domain.service.CompanyProvider;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Associate {
  @Embedded
  private Company company;

  @Embedded
  private Hub hub;

  protected Associate(UUID companyId, CompanyProvider provider) {
    if (companyId == null || provider == null) {
      throw new CustomException(HttpStatus.BAD_REQUEST, "업체 정보가 올바르지 않습니다.");
    }

    CompanyData data = provider.get(companyId);
    if (data == null) {
      throw new CustomException(HttpStatus.NOT_FOUND, "유효하지 않은 업체 입니다.");
    }

    this.company = new Company(companyId, data.companyName());
    this.hub = new Hub(data.hubId(), data.hubName());
  }
}
