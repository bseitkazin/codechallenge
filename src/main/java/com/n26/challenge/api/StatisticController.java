package com.n26.challenge.api;

import java.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.n26.challenge.entity.Statistic;
import com.n26.challenge.service.StatisticService;

@Controller
@RequestMapping("/")
public class StatisticController {

  @Autowired
  StatisticService statisticService;

  @RequestMapping(value = {"/transactions"}, method = RequestMethod.POST)
  public ResponseEntity<Void> makeTransaction(@RequestParam(required = true) final Double amount,
      @RequestParam(required = true) final Long timestamp) {

    /*
     * calculate in millis the current time
     */
    Instant instant = Instant.now();
    long timeStampMillis = instant.toEpochMilli();

    /*
     * check the transactions time
     * if transaction older than 60 sec,
     * then we return 204 code with empty body, 
     * else we return 201 code and write transaction
     */
    if (timeStampMillis - timestamp > 60) {
      return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    } else {
      statisticService.writeToOperation(timestamp, amount);
      return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
  }

  @RequestMapping(value = {"/statistics"}, method = RequestMethod.GET)
  public ResponseEntity<Statistic> getStatistics() {

    /* get calculated statistic for the last 60 sec*/
    Statistic statistic = statisticService.calculateStatisticToTheLastMinute();

    /* if in some case we got null */
    if (statistic == null) {
      return new ResponseEntity<Statistic>(HttpStatus.NOT_FOUND);
    }

    /* return Statistic object */
    return new ResponseEntity<Statistic>(statistic, HttpStatus.OK);
  }
}
