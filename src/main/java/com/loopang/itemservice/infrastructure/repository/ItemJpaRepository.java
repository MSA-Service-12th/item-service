package com.loopang.itemservice.infrastructure.repository;

import com.loopang.itemservice.domain.model.Item;
import com.loopang.itemservice.domain.repository.ItemRepository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemJpaRepository extends JpaRepository<Item, UUID>, ItemRepository {

//    boolean existsByCompanyInfo_IdAndName(UUID companyId, ItemName name);
}
