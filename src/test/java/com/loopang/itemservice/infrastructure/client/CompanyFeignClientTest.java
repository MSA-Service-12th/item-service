package com.loopang.itemservice.infrastructure.client;

import com.loopang.itemservice.domain.service.CompanyData;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class CompanyFeignClientTest {
  @Autowired
  CompanyFeignClient client;

  @Test
  void request_test() {
    CompanyData data = client.get(UUID.randomUUID());
    log.info("companyData: {}", data);
  }
}
