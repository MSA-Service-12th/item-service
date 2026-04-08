package com.loopang.itemservice.infrastructure.repository;

import com.loopang.itemservice.domain.model.QItem;
import com.loopang.itemservice.presentation.dto.ItemResponseDto;
import com.loopang.itemservice.presentation.dto.ItemSearchCondition;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ItemCustomRepository {

  private final JPAQueryFactory queryFactory;

  public Page<ItemResponseDto> search(Pageable pageable, ItemSearchCondition request, UUID myHubId) {
    QItem item = QItem.item;

    // 통합 검색 (상품명 + 회사명 + 허브명)
    BooleanExpression keywordCondition = keywordCondition(item, request.getQ());
    // 개별 필터 조건
    BooleanExpression itemNameCondition = itemNameCondition(item, request.getItemName());
    BooleanExpression companyNameCondition = companyNameCondition(item, request.getCompanyName());
    BooleanExpression hubNameCondition = hubNameCondition(item, request.getHubName());
    BooleanExpression deletedCondition = item.deletedAt.isNull();
    BooleanExpression hubIdCondition = hubIdCondition(item, myHubId); // 권한이 "HuB"인 경우 본인 허브만 조회

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
            , hubNameCondition
            , deletedCondition
            , hubIdCondition)
        .orderBy(getOrderSpecifiers(pageable, item))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    Long total = queryFactory
        .select(item.count())
        .from(item)
        .where(keywordCondition
            , itemNameCondition
            , companyNameCondition
            , hubNameCondition
            , deletedCondition
            , hubIdCondition)
        .fetchOne();

    return new PageImpl<>(content, pageable, total == null ? 0 : total);
  }

  private BooleanExpression hubIdCondition(QItem item, UUID myHubId) {
    if (myHubId == null) {
      return null;
    }
    return item.associate.hub.id.eq(myHubId);
  }

  private OrderSpecifier<?>[] getOrderSpecifiers(Pageable pageable, QItem item) {
    List<OrderSpecifier<?>> orders = new ArrayList<>();

    for (Sort.Order order : pageable.getSort()) {
      Order direction = order.isAscending() ? Order.ASC : Order.DESC;

      switch (order.getProperty()) {
        case "name" ->
            orders.add(new OrderSpecifier<>(direction, item.name));
        case "companyName" ->
            orders.add(new OrderSpecifier<>(direction, item.associate.company.name));
        case "hubName" ->
            orders.add(new OrderSpecifier<>(direction, item.associate.hub.name));
        case "createdAt" ->
            orders.add(new OrderSpecifier<>(direction, item.createdAt));
        default -> {
        }
      }
    }

    if (orders.isEmpty()) {
      orders.add(new OrderSpecifier<>(Order.DESC, item.createdAt));
    }

    return orders.toArray(new OrderSpecifier[0]);
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
    if (keyword == null|| keyword.isBlank()) {
      return null;
    }
    return item.name.containsIgnoreCase(keyword)
        .or(item.associate.company.name.containsIgnoreCase(keyword))
        .or(item.associate.hub.name.containsIgnoreCase(keyword));
  }

}
