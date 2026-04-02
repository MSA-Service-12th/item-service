package com.loopang.itemservice.domain.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;


public record CompanyData(
    UUID hubId,
    String hubName,
    @JsonProperty("name")
    String companyName
) {}
