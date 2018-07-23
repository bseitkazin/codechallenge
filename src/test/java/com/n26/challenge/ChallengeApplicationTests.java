package com.n26.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import com.n26.challenge.service.StatisticService;

@ComponentScan(basePackageClasses = {StatisticService.class})
@SpringBootApplication
public class ChallengeApplicationTests {

  public static void main(String[] args) {
    SpringApplication.run(ChallengeApplicationTests.class, args);
  }

}
