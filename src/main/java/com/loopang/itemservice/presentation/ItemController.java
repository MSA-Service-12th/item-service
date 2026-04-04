package com.loopang.itemservice.presentation;

import com.loopang.common.response.CommonResponse;
import com.loopang.common.response.PageInfo;
import com.loopang.itemservice.application.ItemService;
import com.loopang.itemservice.presentation.dto.ItemRequestDto;
import com.loopang.itemservice.presentation.dto.ItemResponseDto;
import com.loopang.itemservice.presentation.dto.ItemSearchRequestDto;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/companies/{companyId}/items")
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse<ItemResponseDto> create(@RequestBody @Valid ItemRequestDto request,
                                                  @PathVariable UUID companyId) {
        ItemResponseDto response = itemService.create(request.getName(), companyId);
        return CommonResponse.success(response, "상품이 등록되었습니다.");
    }

    @PatchMapping("/items/{itemId}")
    public CommonResponse<ItemResponseDto> update(@RequestBody @Valid ItemRequestDto request,
                                                  @PathVariable UUID itemId) {
        ItemResponseDto response = itemService.update(request.getName(), itemId);
        return CommonResponse.success(response, "상품이 수정되었습니다.");
    }

    @DeleteMapping("/items/{itemId}")
    public CommonResponse<ItemResponseDto> delete(@PathVariable UUID itemId) {

        // todo: security 도입 후 수정
        UUID userId = UUID.randomUUID();
        ItemResponseDto response = itemService.delete(itemId, userId);
        return CommonResponse.success(response, "상품이 삭제되었습니다.");
    }

    @GetMapping("/items")
    public CommonResponse<List<ItemResponseDto>> getItems(
        Pageable pageable,
        @ModelAttribute ItemSearchRequestDto request
    )
    {
        // todo: 권한 처리
        Page<ItemResponseDto> page = itemService.search(pageable, request);
        return CommonResponse.success(page.getContent(), "상품이 성공적으로 조회되었습니다.", PageInfo.from(page));
    }

    @GetMapping("/items/{itemId}")
    public CommonResponse<ItemResponseDto> getItem(@PathVariable UUID itemId)
    {
        // todo: 권한 처리
        ItemResponseDto response = itemService.getItem(itemId);
        return CommonResponse.success(response, "상품이 성공적으로 조회되었습니다.");
    }

}
