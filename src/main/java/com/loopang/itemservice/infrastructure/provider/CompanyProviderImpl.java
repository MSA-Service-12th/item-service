package com.loopang.itemservice.infrastructure.provider;

import com.loopang.itemservice.domain.service.CompanyData;
import com.loopang.itemservice.domain.service.CompanyProvider;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyProviderImpl implements CompanyProvider {

  @Override
  public CompanyData get(UUID companyId) {
    return null;
  }
}
