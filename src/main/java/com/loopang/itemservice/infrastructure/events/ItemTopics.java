package com.loopang.itemservice.infrastructure.events;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "topics.item")
public record ItemTopics(
    String updated
) {

}
