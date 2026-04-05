package com.loopang.itemservice.domain.repository;

import com.loopang.itemservice.domain.model.Item;
import com.loopang.itemservice.presentation.dto.ItemResponseDto;
import com.loopang.itemservice.presentation.dto.ItemSearchCondition;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemQueryRepository {
  boolean exists(String name, UUID companyId);

  Optional<Item> findById(UUID itemId);

  Page<ItemResponseDto> search(Pageable pageable, ItemSearchCondition request);

  Optional<Item> findByIdAndDeletedAtIsNull(UUID itemId);
}
