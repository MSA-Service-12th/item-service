package com.loopang.itemservice.domain.events;

import com.loopang.itemservice.domain.model.Item;

public interface ItemEvents {
    void itemChanged(Item item);
}
