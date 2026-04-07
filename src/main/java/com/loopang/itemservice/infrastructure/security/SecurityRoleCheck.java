package com.loopang.itemservice.infrastructure.security;

import com.loopang.itemservice.domain.exception.ItemForbiddenException;
import com.loopang.itemservice.domain.model.UserType;
import com.loopang.itemservice.domain.service.RoleCheck;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityRoleCheck implements RoleCheck {

  @Override
  public boolean isMyCompany(UUID targetCompanyId, UUID myCompanyId) {
    return targetCompanyId.equals(myCompanyId);
  }

  @Override
  public boolean isMyHub(UUID targetHubId, UUID myHubId) {
    return targetHubId.equals(myHubId);
  }

  @Override
  public void checkCreate(UserType userType, UUID targetCompanyId, UUID myCompanyId) {

    if (userType == null || userType == UserType.PENDING) {
      throw new ItemForbiddenException("상품 등록 권한이 없습니다.");
    }

    if (userType == UserType.MASTER) {
      return;
    }

    if (userType == UserType.HUB) {
      return;
    }

    if (userType == UserType.COMPANY) {
      if (!isMyCompany(targetCompanyId, myCompanyId)) {
        throw new ItemForbiddenException("본인 업체의 상품만 등록할 수 있습니다.");
      }
      return;
    }

    throw new ItemForbiddenException("상품 등록 권한이 없습니다.");
  }

  @Override
  public void checkEdit(UserType userType, UUID targetCompanyId, UUID myCompanyId, UUID targetHubId, UUID myHubId) {

    if (userType == null || userType == UserType.PENDING) {
      throw new ItemForbiddenException("상품 수정 권한이 없습니다.");
    }

    if (userType == UserType.MASTER) {
      return;
    }

    if (userType == UserType.HUB) {
      if(!isMyHub(targetHubId, myHubId)) {
        throw new ItemForbiddenException("담당 허브의 상품만 수정할 수 있습니다.");
      }
      return;
    }

    if (userType == UserType.COMPANY) {
      if (!isMyCompany(targetCompanyId, myCompanyId)) {
        throw new ItemForbiddenException("본인 업체의 상품만 수정할 수 있습니다.");
      }
      return;
    }

    throw new ItemForbiddenException("상품 수정 권한이 없습니다.");
  }

  @Override
  public void checkDelete(UserType userType, UUID targetHubId, UUID myHubId) {
    if (userType == null || userType == UserType.PENDING) {
      throw new ItemForbiddenException("상품 삭제 권한이 없습니다.");
    }

    if (userType == UserType.MASTER) {
      return;
    }

    if (userType == UserType.HUB) {
      if(!isMyHub(targetHubId, myHubId)) {
        throw new ItemForbiddenException("담당 허브의 상품만 삭제할 수 있습니다.");
      }
      return;
    }

    throw new ItemForbiddenException("상품 삭제 권한이 없습니다.");

  }

  @Override
  public void checkSearch(UserType userType, UUID targetHubId, UUID myHubId) {
    if (userType == null || userType == UserType.PENDING) {
      throw new ItemForbiddenException("상품 조회 권한이 없습니다.");
    }

    if (userType == UserType.MASTER) {
      return;
    }

    if (userType == UserType.HUB) {
      if(!isMyHub(targetHubId, myHubId)) {
        throw new ItemForbiddenException("담당 허브의 상품만 조회할 수 있습니다.");
      }
      return;
    }

    if (userType == UserType.COMPANY) {
      return;
    }
    throw new ItemForbiddenException("상품 조회 권한이 없습니다.");
  }

}
