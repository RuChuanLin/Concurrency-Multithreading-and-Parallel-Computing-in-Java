package _29;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicVariable {

  private static final AtomicInteger atomicInteger = new AtomicInteger(0);

  public static void main(String[] args) {
    Thread t1 = new Thread(AtomicVariable::increment);
    Thread t2 = new Thread(AtomicVariable::increment);
    t1.start();
    t2.start();
    try {
      t1.join();
      t2.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("counter = " + atomicInteger);
  }

  private static void increment() {
    for (int i = 0; i < 10000; i++) {
      atomicInteger.incrementAndGet();
    }
  }
}
