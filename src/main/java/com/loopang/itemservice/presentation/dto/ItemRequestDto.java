package com.loopang.itemservice.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ItemRequestDto {

    @NotBlank
    private String name;

}
