package com.loopang.itemservice.domain.events;

import java.time.LocalDateTime;
import java.util.UUID;

public record ItemChanged(
    UUID itemId, //어떤 상품인지
    String itemName, // 변경된 이름
    LocalDateTime occurredAt // 이벤트 발생 시각
) {}

