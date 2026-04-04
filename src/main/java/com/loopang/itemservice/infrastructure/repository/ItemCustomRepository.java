package com.loopang.itemservice.infrastructure.repository;

import com.loopang.itemservice.domain.model.QItem;
import com.loopang.itemservice.presentation.dto.ItemResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ItemCustomRepository {

  private final JPAQueryFactory queryFactory;

  public Page<ItemResponseDto> search(String keyword, Pageable pageable, String itemName, String companyName, String hubName) {
    QItem item = QItem.item;

    // 통합 검색 (상품명 + 회사명 + 허브명)
    BooleanExpression keywordCondition = keywordCondition(item, keyword);
    // 개별 필터 조건
    BooleanExpression itemNameCondition = itemNameCondition(item, itemName);
    BooleanExpression companyNameCondition = companyNameCondition(item, companyName);
    BooleanExpression hubNameCondition = hubNameCondition(item, hubName);

    List<ItemResponseDto> content = queryFactory
        .select(Projections.constructor(
            ItemResponseDto.class,
            item.id,
            item.name,
            item.associate.company.id,
            item.associate.company.name,
            item.associate.hub.id,
            item.associate.hub.name

        ))
        .from(item)
        .where(keywordCondition
            , itemNameCondition
            , companyNameCondition
            , hubNameCondition)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    Long total = queryFactory
        .select(item.count())
        .from(item)
        .where(keywordCondition
            , itemNameCondition
            , companyNameCondition
            , hubNameCondition)
        .fetchOne();

    return new PageImpl<>(content, pageable, total == null ? 0 : total);
  }

  private BooleanExpression hubNameCondition(QItem item, String hubName) {
    if (hubName == null || hubName.isBlank()) {
      return null;
    }
    return item.associate.hub.name.containsIgnoreCase(hubName);
  }

  private BooleanExpression companyNameCondition(QItem item, String companyName) {
    if (companyName == null || companyName.isBlank()) {
      return null;
    }
    return item.associate.company.name.containsIgnoreCase(companyName);
  }

  private BooleanExpression itemNameCondition(QItem item, String itemName) {
    if (itemName == null || itemName.isBlank()) {
      return null;
    }
    return item.name.containsIgnoreCase(itemName);
  }


  private BooleanExpression keywordCondition(QItem item, String keyword) {
    if (keyword == null) {
      return null;
    }
    return item.name.containsIgnoreCase(keyword)
        .or(item.associate.company.name.containsIgnoreCase(keyword))
        .or(item.associate.hub.name.containsIgnoreCase(keyword));
  }

}
