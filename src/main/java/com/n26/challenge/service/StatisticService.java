package com.n26.challenge.service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;
import com.n26.challenge.entity.Statistic;

@Service
public interface StatisticService {

  public static final int THRESHOLD = 16;
  public static ConcurrentHashMap<Long, List<Double>> operations = new ConcurrentHashMap<>();

  public void writeToOperation(Long timestamp, Double amount);

  public Statistic calculateStatisticToTheLastMinute();
}
