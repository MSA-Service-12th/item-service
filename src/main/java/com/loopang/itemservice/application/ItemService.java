package com.loopang.itemservice.application;


import com.loopang.itemservice.domain.events.ItemEvents;
import com.loopang.itemservice.domain.exception.ItemForbiddenException;
import com.loopang.itemservice.domain.exception.ItemNotFoundException;
import com.loopang.itemservice.domain.model.Item;
import com.loopang.itemservice.domain.model.UserType;
import com.loopang.itemservice.domain.repository.ItemQueryRepository;
import com.loopang.itemservice.domain.repository.ItemRepository;
import com.loopang.itemservice.domain.service.CompanyProvider;
import com.loopang.itemservice.domain.service.ItemCheck;
import com.loopang.itemservice.domain.service.RoleCheck;
import com.loopang.itemservice.presentation.dto.ItemResponseDto;
import com.loopang.itemservice.presentation.dto.ItemSearchCondition;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemQueryRepository itemQueryRepository;
    private final RoleCheck roleCheck;
    private final ItemCheck itemCheck;
    private final CompanyProvider companyProvider;
    private final ItemEvents itemEvents;

    // 상품 등록
    public ItemResponseDto create(String name, UUID targetCompanyId, UserType userType, UUID myCompanyId) {

        Item item = Item.builder()
            .name(name)
            .targetCompanyId(targetCompanyId)
            .userType(userType)
            .companyProvider(companyProvider)
            .roleCheck(roleCheck)
            .itemCheck(itemCheck)
            .myCompanyId(myCompanyId)
            .build();

        itemRepository.save(item);

        return ItemResponseDto.from(item);

    }


    // 상품 수정
    public ItemResponseDto update(String name, UUID itemId, UserType userType, UUID myCompanyId,
        UUID myHubId) {
        Item item = findItem(itemId);
        item.changeName(name, roleCheck, itemCheck, itemEvents, userType, myCompanyId, myHubId);

        return ItemResponseDto.from(item);
    }

    // 상품 삭제
    public ItemResponseDto delete(UUID itemId, UserType userType, UUID userId, UUID myHubId) {
        Item item = findItem(itemId);
        item.delete(myHubId, roleCheck, userType, userId);

        return ItemResponseDto.from(item);
    }

    private Item findItem(UUID itemId) {
        return itemQueryRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("존재하지 않는 상품입니다."));
    }

    // 전체 조회
    public Page<ItemResponseDto> search(
        Pageable pageable,
        ItemSearchCondition request,
        UserType userType, UUID myHubId) {

        // 권한 검사 (PENDING, 인증되지 않은 사용자 차단)
        if (userType == null || userType == UserType.PENDING) {
            throw new ItemForbiddenException("상품 조회 권한이 없습니다.");
        }
        // 권한이 'HUB'인 경우 본인 소속 허브의 상품만 조회
        UUID targetHubId = (userType == UserType.HUB) ? myHubId : null;
        return itemQueryRepository.search(pageable, request, targetHubId);
    }


    // 단건 조회
    public ItemResponseDto getItem(UUID itemId, UserType userType, UUID myHubId) {
        Item item = itemQueryRepository.findByIdAndDeletedAtIsNull(itemId)
            .orElseThrow(() -> new ItemNotFoundException("존재하지 않는 상품입니다."));

        UUID targetHubId = item.getAssociate().getHub().getId();


        roleCheck.checkSearch(userType, targetHubId, myHubId);
        return ItemResponseDto.from(item);
    }
}
