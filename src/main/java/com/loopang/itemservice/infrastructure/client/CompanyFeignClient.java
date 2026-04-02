package com.loopang.itemservice.infrastructure.client;

import com.loopang.itemservice.domain.service.CompanyData;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// 대안을 자세한 로직
@FeignClient(name = "company-service", fallbackFactory = CompanyFeignClientFallback.class)
public interface CompanyFeignClient {

  @GetMapping("/companies/{id}")
  CompanyData get(@PathVariable UUID id);
  // json 형식으로 에러를 출력하기위해 글로벌로 안넘아가게 처리.

}
