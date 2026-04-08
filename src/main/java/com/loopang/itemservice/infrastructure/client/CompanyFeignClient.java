package com.loopang.itemservice.infrastructure.client;

import com.loopang.itemservice.domain.service.CompanyData;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "company-service", fallbackFactory = CompanyFeignClientFallback.class)
public interface CompanyFeignClient {

    // json 형식으로 에러를 출력하기위해 글로벌로 안넘아가게 처리 -> try catch x
  @GetMapping("/api/companies/{companyId}")
  CompanyData get(@PathVariable("companyId") UUID companyId);

}
