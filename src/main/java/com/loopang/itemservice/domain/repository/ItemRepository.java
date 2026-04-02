package com.loopang.itemservice.domain.repository;


import com.loopang.itemservice.domain.model.Item;

public interface ItemRepository {
    Item save(Item item);
}
