package com.loopang.itemservice.infrastructure.security;

import com.loopang.itemservice.domain.service.RoleCheck;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityRoleCheck implements RoleCheck {

  // todo: security 도입 후 내부 구현 예정

  @Override
  public boolean hasRole(String role) {
    return false;
  }

  @Override
  public boolean hasRole(List<String> roles) {
    return false;
  }

  @Override
  public boolean isMyCompany(UUID companyId) {
    return false;
  }

  @Override
  public boolean isMyHub(UUID companyId) {
    return false;
  }
}
