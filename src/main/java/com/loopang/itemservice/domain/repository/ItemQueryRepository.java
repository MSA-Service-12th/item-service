package com.loopang.itemservice.domain.repository;

import java.util.UUID;

public interface ItemQueryRepository {
  boolean exists(String name, UUID companyId);
}
