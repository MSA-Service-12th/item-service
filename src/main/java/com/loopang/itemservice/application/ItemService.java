package com.loopang.itemservice.application;

import com.loopang.itemservice.domain.events.ItemEvents;
import com.loopang.itemservice.domain.exception.ItemNotFoundException;
import com.loopang.itemservice.domain.model.Item;
import com.loopang.itemservice.domain.repository.ItemRepository;
import com.loopang.itemservice.domain.service.CompanyProvider;
import com.loopang.itemservice.domain.service.ItemCheck;
import com.loopang.itemservice.domain.service.RoleCheck;
import com.loopang.itemservice.presentation.dto.ItemResponseDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final RoleCheck roleCheck;
    private final ItemCheck itemCheck;
    private final CompanyProvider companyProvider;
    private final ItemEvents itemEvents;

    // 상품 등록
    public ItemResponseDto create(String name, UUID companyId) {

        Item item = Item.builder()
            .name(name)
            .companyId(companyId)
            .companyProvider(companyProvider)
            .roleCheck(roleCheck)
            .itemCheck(itemCheck)
            .build();

        itemRepository.save(item);

        return ItemResponseDto.from(item);

    }


    // 상품 수정
    public ItemResponseDto update(String name, UUID itemId) {
        Item item = getItem(itemId);
        item.changeName(name, roleCheck, itemCheck, itemEvents);

        return ItemResponseDto.from(item);
    }

    private Item getItem(UUID itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("존재하지 않는 상품입니다."));
    }

}
