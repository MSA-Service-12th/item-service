package com.loopang.itemservice.presentation.dto;

import lombok.Getter;

@Getter
public class ItemSearchRequestDto {
  private String q;
  private String itemName;
  private String companyName;
  private String hubName;
}
