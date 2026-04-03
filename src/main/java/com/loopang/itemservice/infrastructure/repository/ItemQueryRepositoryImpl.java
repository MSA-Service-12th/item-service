package com.loopang.itemservice.infrastructure.repository;

import com.loopang.itemservice.domain.model.Item;
import com.loopang.itemservice.domain.repository.ItemQueryRepository;
import com.loopang.itemservice.domain.repository.ItemRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ItemQueryRepositoryImpl implements ItemQueryRepository, ItemRepository {

  // todo: QueryDsl 도입예정
  private final ItemJpaRepository jpaRepository;

  @Override
  public Item save(Item item) {
    return jpaRepository.save(item);
  }

  @Override
  public boolean exists(String name, UUID companyId) {
    return jpaRepository.existsByNameAndAssociate_Company_Id(name, companyId);
  }

  @Override
  public Optional<Item> findById(UUID itemId) {
    return jpaRepository.findById(itemId);
  }
}
