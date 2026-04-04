package com.loopang.itemservice.infrastructure.repository;

import com.loopang.itemservice.domain.model.Item;
import com.loopang.itemservice.domain.repository.ItemQueryRepository;
import com.loopang.itemservice.domain.repository.ItemRepository;
import com.loopang.itemservice.presentation.dto.ItemResponseDto;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ItemQueryRepositoryImpl implements ItemQueryRepository, ItemRepository {

  // todo: QueryDsl 도입예정
  private final ItemJpaRepository jpaRepository;
  private final ItemCustomRepository itemCustomRepository;

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

  @Override
  public Page<ItemResponseDto> search(String keyword, Pageable normalizePageable, String itemName, String companyName, String hubName) {
    return itemCustomRepository.search(keyword, normalizePageable, itemName, companyName, hubName);
  }
}
