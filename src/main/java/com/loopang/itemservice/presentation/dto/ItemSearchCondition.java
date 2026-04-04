package com.loopang.itemservice.presentation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemSearchCondition {
  private String q;
  private String itemName;
  private String companyName;
  private String hubName;

  public String getQ() {
    return normalize(q);
  }

  public String getItemName() {
    return normalize(itemName);
  }

  public String getCompanyName() {
    return normalize(companyName);
  }

  public String getHubName() {
    return normalize(hubName);
  }

  private String normalize(String value) {
    if (value == null) return null;

    String trimmed = value.trim();
    if (trimmed.isEmpty()) return null;

    return trimmed;
  }
}
