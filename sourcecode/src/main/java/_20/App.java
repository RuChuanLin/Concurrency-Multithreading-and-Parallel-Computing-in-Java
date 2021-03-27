package _20;

import java.util.ArrayList;
import java.util.List;

class Processor {

  private static final int UPPER_LIMIT = 5;
  private static final int LOWER_LIMIT = 0;
  private static final Object lock = new Object();
  private final List<Integer> list = new ArrayList<>();
  private int value = 0;

  public void producer() throws InterruptedException {
    synchronized (lock) {
      while (true) {
        if (list.size() == UPPER_LIMIT) {
          System.out.println("Waiting for removing elements in the list");
          lock.wait();
        } else {
          System.out.println("Current value : " + value);
          list.add(value++);
          lock.notify();
        }
        Thread.sleep(500);
      }
    }
  }

  public void consumer() throws InterruptedException {
    synchronized (lock) {

      while (true) {
        if (list.size() == LOWER_LIMIT) {
          System.out.println("Waiting for adding elements in the list");
          value = 0;
          lock.wait();
        } else {
          System.out.println("Current value : " + list.remove(list.size() - 1));
          lock.notify();
        }
        Thread.sleep(500);
      }
    }
  }
}

public class App {

  public static void main(String[] args) {

    Processor p = new Processor();
    Thread t1 = new Thread(() -> {
      try {
        p.consumer();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
    Thread t2 = new Thread(() -> {
      try {
        p.producer();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
    t1.start();
    t2.start();
  }
}
