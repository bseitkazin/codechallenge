package com.n26.challenge.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.n26.challenge.entity.Statistic;

/**
 * 
 * @author BSeitkazin Default Implementation of StatisticService
 *
 */

@Service
public class StatisticServiceImpl implements StatisticService {

  /**
   * function to write data into datastore
   */
  public void writeToOperation(Long timestamp, Double amount) {
    /* find the key by that timestamp */
    if (operations.get(timestamp) != null) {
      /* add amount, to the proper timestamp transaction */
      operations.get(timestamp).add(amount);
    } else {
      /* we didn't find the key on that timestamp, so create it */
      List<Double> list = new ArrayList<>();
      list.add(amount);
      operations.put(timestamp, list);
    }

  }

  /**
   * function to calculate statistic
   */
  public Statistic calculateStatisticToTheLastMinute() {

    /* if we have no operations, simply response object with 0 values */
    if (operations.isEmpty()) {
      return Statistic.zerofy();
    }

    /* get current timestamp in millis */
    Instant instant = Instant.now();
    long timeStampMillis = instant.toEpochMilli();

    /* create Statistic with default values, including max and min values */
    Statistic calcStat = Statistic.defaultStatistic();

    /* calculate statistic */
    operations.forEach((k, v) -> {
      /* we calculate statistic only for the last 60 sec */
      if (timeStampMillis - k < 60) {
        /* we can have many values for one timestamp, so we calculate count of amounts */
        calcStat.setCount(calcStat.getCount() + v.size());
        double innerSum = 0;
        for (Double amount : v) {
          /* sum of amounts for one timestamp */
          innerSum += amount;
          /* check max value */
          if (calcStat.getMax() < amount) {
            calcStat.setMax(amount);
          }
          /* check min value */
          if (calcStat.getMin() > amount) {
            calcStat.setMin(amount);
          }
        }
        /* write all sum */
        calcStat.setSum(calcStat.getSum() + innerSum);
        /* we have all data to calculate avg value */
        calcStat.setAvg(calcStat.getSum() / calcStat.getCount());
      }
    });

    return calcStat;
  }

}
