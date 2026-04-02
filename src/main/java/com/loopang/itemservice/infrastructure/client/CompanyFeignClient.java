package com.loopang.itemservice.infrastructure.client;

import com.loopang.itemservice.domain.service.CompanyData;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "company-service")
public interface CompanyFeignClient {

  @GetMapping("/companies/{id}")
  CompanyData get(@PathVariable UUID id);

}
