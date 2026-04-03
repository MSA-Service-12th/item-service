package com.loopang.itemservice.infrastructure.events;

import com.loopang.common.event.Events;
import com.loopang.common.event.OutboxEvent;
import com.loopang.itemservice.domain.events.ItemChanged;
import com.loopang.itemservice.domain.events.ItemEvents;
import com.loopang.itemservice.domain.model.Item;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class ItemEventsImpl implements ItemEvents {

  @Override
  public void itemChanged(Item item) {
    ItemChanged payload = new ItemChanged(
        item.getId(),
        item.getName(),
        LocalDateTime.now()
    );

    Events.trigger(
        OutboxEvent.of(
            "ITEM",
            item.getId(),
            "item.changed",
            payload
        )
    );
  }
}
