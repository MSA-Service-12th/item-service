package com.loopang.itemservice.domain.repository;


import com.loopang.itemservice.domain.model.Item;
import java.util.Optional;
import java.util.UUID;

public interface ItemRepository {
    Item save(Item item);

    Optional<Item> findById(UUID itemId);

}
