package com.loopang.itemservice.domain.service;

import java.util.UUID;

public interface ItemCheck {

  void checkDuplicated(String normalizedName, UUID companyId);
}
