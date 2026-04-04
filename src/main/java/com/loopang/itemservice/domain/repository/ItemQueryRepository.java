package com.loopang.itemservice.domain.repository;

import com.loopang.itemservice.presentation.dto.ItemResponseDto;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemQueryRepository {
  boolean exists(String name, UUID companyId);

  Page<ItemResponseDto> search(String keyword, Pageable normalizePageable, String itemName, String companyName, String hubName);

}
