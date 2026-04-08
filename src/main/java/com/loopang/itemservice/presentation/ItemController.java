package com.loopang.itemservice.presentation;

import com.loopang.common.response.CommonResponse;
import com.loopang.common.response.PageInfo;
import com.loopang.itemservice.application.ItemService;
import com.loopang.itemservice.domain.model.UserType;
import com.loopang.itemservice.presentation.dto.ItemRequestDto;
import com.loopang.itemservice.presentation.dto.ItemResponseDto;
import com.loopang.itemservice.presentation.dto.ItemSearchCondition;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/companies/{targetCompanyId}/items")
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse<ItemResponseDto> create(
        @RequestBody @Valid ItemRequestDto request,
        @PathVariable UUID targetCompanyId,
        @RequestHeader(value = "X-User-Role") String userRole,
        @RequestHeader(value = "X-User-Company-Id") UUID myCompanyId) {

        return CommonResponse.success(
            itemService.create(request.getName(), targetCompanyId, UserType.from(userRole), myCompanyId),
            "상품이 등록되었습니다.");
    }

    @PatchMapping("/items/{itemId}")
    public CommonResponse<ItemResponseDto> update(
        @RequestBody @Valid ItemRequestDto request,
        @PathVariable UUID itemId,
        @RequestHeader(value = "X-User-Role") String userRole,
        @RequestHeader(value = "X-User-Company-Id") UUID myCompanyId,
        @RequestHeader(value = "X-User-Hub-Id") UUID myHubId) {
        return CommonResponse.success(itemService.update(request.getName(), itemId, UserType.from(userRole), myCompanyId, myHubId),
            "상품이 수정되었습니다.");
    }

    @DeleteMapping("/items/{itemId}")
    public CommonResponse<ItemResponseDto> delete(
        @PathVariable UUID itemId,
        @RequestHeader(value = "X-User-Role") String userRole,
        @RequestHeader(value = "X-User-UUID") UUID userId,
        @RequestHeader(value = "X-User-Hub-Id") UUID myHubId) {
        return CommonResponse.success(itemService.delete(itemId,UserType.from(userRole), userId, myHubId),
            "상품이 삭제되었습니다.");
    }

    @GetMapping("/items")
    public CommonResponse<List<ItemResponseDto>> getItems(
        Pageable pageable,
        @ModelAttribute ItemSearchCondition request,
        @RequestHeader(value = "X-User-Role") String userRole,
        @RequestHeader(value = "X-User-Hub-Id") UUID myHubId
    )
    {
        Page<ItemResponseDto> page = itemService.search(pageable, request, UserType.from(userRole), myHubId);
        return CommonResponse.success(page.getContent(), "상품이 성공적으로 조회되었습니다.", PageInfo.from(page));
    }

    @GetMapping("/items/{itemId}")
    public CommonResponse<ItemResponseDto> getItem(
        @PathVariable UUID itemId,
        @RequestHeader(value = "X-User-Role") String userRole,
        @RequestHeader(value = "X-User-Hub-Id") UUID myHubId
    )
    {
        ItemResponseDto response = itemService.getItem(itemId, UserType.from(userRole), myHubId);
        return CommonResponse.success(response, "상품이 성공적으로 조회되었습니다.");
    }

}
