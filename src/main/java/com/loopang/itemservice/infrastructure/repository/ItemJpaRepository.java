package com.loopang.itemservice.infrastructure.repository;

import com.loopang.itemservice.domain.model.Item;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemJpaRepository extends JpaRepository<Item, UUID> {

  boolean existsByNameAndAssociate_Company_Id(String name, UUID companyId);

  Optional<Item> findByIdAndDeletedAtIsNull(UUID itemId);
}
