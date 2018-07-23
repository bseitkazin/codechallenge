package com.n26.challenge.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.n26.challenge.ChallengeApplicationTests;
import com.n26.challenge.entity.Statistic;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChallengeApplicationTests.class)
public class StatisticServiceTest {

  private static final Integer TEN_SEC_IN_MILLS = 10000;
  private static final Integer TWENTY_SEC_IN_MILLS = 20000;
  private static final Double MIN_VALUE = 1.0;
  private static final Double MAX_VALUE = 1000.0;

  @Autowired
  StatisticService statisticService;

  @Before
  public void setup() {
    // ensure that our datastorage is empty
    StatisticService.operations.clear();
  }

  @Test
  public void test_writeToOperation() {
    Instant instant = Instant.now();
    long timeStampMillis = instant.toEpochMilli();

    for (int i = 0; i < 6; i++) {
      double randomAmount = ThreadLocalRandom.current().nextDouble(MIN_VALUE, MAX_VALUE);
      timeStampMillis += timeStampMillis + TEN_SEC_IN_MILLS;
      statisticService.writeToOperation(timeStampMillis, randomAmount);
    }
    assertThat(StatisticService.operations.size(), is(6));

  }

  @Test
  public void test_calculateStatisticToTheLastMinute() {
    Instant instant = Instant.now();
    long timeStampMillis = instant.toEpochMilli();

    statisticService.writeToOperation(timeStampMillis, 100.0);
    statisticService.writeToOperation(timeStampMillis, 120.0);
    statisticService.writeToOperation(timeStampMillis + TEN_SEC_IN_MILLS, 140.0);
    statisticService.writeToOperation(timeStampMillis + TEN_SEC_IN_MILLS, 160.0);
    statisticService.writeToOperation(timeStampMillis + TWENTY_SEC_IN_MILLS, 180.0);
    statisticService.writeToOperation(timeStampMillis + TWENTY_SEC_IN_MILLS, 200.0);

    assertThat(StatisticService.operations.size(), is(3));

    Statistic statistic = statisticService.calculateStatisticToTheLastMinute();
    assertThat(statistic.getSum(), is(900.0));
    assertThat(statistic.getAvg(), is(150.0));
    assertThat(statistic.getMax(), is(200.0));
    assertThat(statistic.getMin(), is(100.0));
    assertThat(statistic.getCount(), is(6L));
  }

}
