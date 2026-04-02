package com.loopang.itemservice.presentation;

import com.loopang.common.response.CommonResponse;
import com.loopang.itemservice.application.ItemService;
import com.loopang.itemservice.presentation.dto.ItemRequestDto;
import com.loopang.itemservice.presentation.dto.ItemResponseDto;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/companies/{companyId}/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse<ItemResponseDto> create(@RequestBody @Valid ItemRequestDto request,
                                                                      @PathVariable UUID companyId) {
        ItemResponseDto response = itemService.create(request.getName(), companyId);
        return CommonResponse.success(response, "상품이 등록되었습니다.");
    }

}
