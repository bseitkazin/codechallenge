package com.n26.challenge.entity;

/**
 * 
 * @author BSeitkazin
 *
 *         Simple POJO Object to store
 *         calculated statistic values
 */

public class Statistic {

  double sum;
  double avg;
  double max;
  double min;
  long count;

  /*
   * creates Statistic Object with values equaled 0
   * we need this Object, when we want to return empty 
   * Statistic Response
   */
  public static Statistic zerofy() {
    Statistic statistic = new Statistic();
    statistic.setSum(0);
    statistic.setAvg(0);
    statistic.setMax(0);
    statistic.setMin(0);
    statistic.setMax(0);
    return statistic;
  }

  /*
   * creates Statistic Object with default values
   * by default, we need MAX value for min val
   * and, we need MIN value for max value
   * to calculate them
   */
  public static Statistic defaultStatistic() {
    Statistic statistic = new Statistic();
    statistic.setSum(0);
    statistic.setAvg(0);
    statistic.setMax(Integer.MIN_VALUE);
    statistic.setMin(Integer.MAX_VALUE);
    statistic.setMax(0);
    return statistic;
  }

  public double getSum() {
    return sum;
  }

  public void setSum(double sum) {
    this.sum = sum;
  }

  public double getAvg() {
    return avg;
  }

  public void setAvg(double avg) {
    this.avg = avg;
  }

  public double getMax() {
    return max;
  }

  public void setMax(double max) {
    this.max = max;
  }

  public double getMin() {
    return min;
  }

  public void setMin(double min) {
    this.min = min;
  }

  public long getCount() {
    return count;
  }

  public void setCount(long count) {
    this.count = count;
  }


}
