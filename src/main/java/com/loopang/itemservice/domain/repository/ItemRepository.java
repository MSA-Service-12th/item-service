package com.loopang.itemservice.domain.repository;


import com.loopang.itemservice.domain.model.Item;
import com.loopang.itemservice.presentation.dto.ItemResponseDto;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepository {
    Item save(Item item);

    Optional<Item> findById(UUID itemId);

  Page<ItemResponseDto> search(String keyword, Pageable normalizePageable, String itemName, String companyName, String hubName);
}
