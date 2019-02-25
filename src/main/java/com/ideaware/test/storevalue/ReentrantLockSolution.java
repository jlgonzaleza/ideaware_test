package com.ideaware.test.storevalue;

import java.math.BigInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class ReentrantLockSolution {

  private static final Lock lock = new ReentrantLock();

  private static long counter = 0;

  public static void increase(int i)
  {
    lock.lock();
    try
    {
      counter += factorial(BigInteger.valueOf(i));
      System.out.println(i + " " +counter);
    }
    finally
    {
      lock.unlock();
    }
  }

  public static void main(String[] args) throws Exception{


    ExecutorService executor = Executors.newFixedThreadPool(2);

    IntStream.range(1, 21)
        .forEach(i -> executor.submit(() ->
          ReentrantLockSolution.increase(i)
        ));
    executor.awaitTermination(1, TimeUnit.SECONDS);
    executor.shutdown();

    System.out.println("Final result: " + counter);
  }

  private static long factorial(BigInteger n) {
    BigInteger factorial = BigInteger.valueOf(1);
    for (int i = 1; i <= n.intValue(); i++) {
      factorial = factorial.multiply(BigInteger.valueOf(i));
    }
    return factorial.longValue();
  }
}
