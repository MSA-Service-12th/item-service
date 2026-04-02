package com.loopang.itemservice.infrastructure.repository;

import com.loopang.itemservice.domain.repository.ItemQueryRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ItemQueryRepositoryImpl implements ItemQueryRepository {

  @Override
  public boolean exists(String name, UUID companyId) {
    // todo: QueryDsl 도입예정
    return false;
  }
}
