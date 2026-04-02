package com.loopang.itemservice.domain.service;

import java.util.UUID;

public interface ItemCheck {
  boolean isDuplicated(String name, UUID companyId);
}
