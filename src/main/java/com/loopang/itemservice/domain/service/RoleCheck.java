package com.loopang.itemservice.domain.service;

import com.loopang.itemservice.domain.model.UserType;
import java.util.UUID;

public interface RoleCheck {
  boolean isMyCompany(UUID targetCompanyId, UUID myCompanyId);

  boolean isMyHub(UUID targetHubId, UUID myHubId);

  void checkEdit(UserType userType, UUID targetCompanyId, UUID myCompanyId, UUID targetHubId, UUID myHubId);

  void checkDelete(UserType userType, UUID targetHubId, UUID myHubId);

  void checkCreate(UserType userType, UUID targetCompanyId, UUID myCompanyId);

  void checkSearch(UserType userType, UUID targetHubId, UUID myHubId);

}
