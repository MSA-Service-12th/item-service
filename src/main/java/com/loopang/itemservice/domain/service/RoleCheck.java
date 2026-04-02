package com.loopang.itemservice.domain.service;

import java.util.List;
import java.util.UUID;

public interface RoleCheck {
  boolean hasRole(String role);
  boolean hasRole(List<String> roles);
  boolean isMyCompany(UUID companyId);
  boolean isMyHub(UUID companyId);
}
