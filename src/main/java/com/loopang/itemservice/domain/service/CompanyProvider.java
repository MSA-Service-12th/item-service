package com.loopang.itemservice.domain.service;

import java.util.UUID;

public interface CompanyProvider {
  CompanyData get(UUID companyId);
}
