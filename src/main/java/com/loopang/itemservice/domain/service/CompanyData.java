package com.loopang.itemservice.domain.service;

import java.util.UUID;

public record CompanyData(
    UUID hubId,
    String hubName,
    String companyName
) {}
