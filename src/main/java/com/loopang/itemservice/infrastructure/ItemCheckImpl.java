package com.loopang.itemservice.infrastructure;

import com.loopang.itemservice.domain.exception.ItemConflictException;
import com.loopang.itemservice.domain.repository.ItemQueryRepository;
import com.loopang.itemservice.domain.service.ItemCheck;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ItemCheckImpl implements ItemCheck {

  private final ItemQueryRepository queryRepository;

  @Override
  public void checkDuplicated(String normalizedName, UUID companyId) {
    if(queryRepository.exists(normalizedName, companyId)) {
      throw new ItemConflictException("이미 등록된 상품입니다. 상품 이름: " + normalizedName);
    }
  }
}
