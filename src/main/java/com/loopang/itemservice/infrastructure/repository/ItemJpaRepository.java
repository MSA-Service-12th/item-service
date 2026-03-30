package com.loopang.itemservice.infrastructure.repository;

import com.loopang.itemservice.domain.entity.Item.Item;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemJpaRepository extends JpaRepository<Item, UUID> {
}
