package com.company;

import com.company.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KunuzApplicationTests {
@Autowired
    ArticleService service;
    @Test
    void contextLoads() {
      service.last4();
      service.last4ByRegion(1);
      service.last4ByCategory(1);
    }

}
