package com.loopang.itemservice.infrastructure;

import com.loopang.itemservice.domain.repository.ItemQueryRepository;
import com.loopang.itemservice.domain.service.ItemCheck;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class ItemCheckImpl implements ItemCheck {

  private final ItemQueryRepository queryRepository;

  @Override
  public boolean isDuplicated(String name, UUID companyId) {
    if (!StringUtils.hasText(name)) return false;

    return queryRepository.exists(name, companyId);
  }
}
