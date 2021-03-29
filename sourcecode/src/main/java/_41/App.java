package _41;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {

  public static void main(String[] args) {
    CountDownLatch countDownLatch = new CountDownLatch(5);
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    for (int i = 0; i < 5; i++) {
      executorService.execute(new Worker(i + 1, countDownLatch));
    }
    try {
      countDownLatch.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    executorService.shutdown();
  }
}

class Worker implements Runnable {

  private final int id;
  private final CountDownLatch countDownLatch;

  public Worker(int id, CountDownLatch countDownLatch) {
    this.id = id;
    this.countDownLatch = countDownLatch;
  }

  @Override
  public void run() {
    doWork();
    countDownLatch.countDown();
  }

  private void doWork() {
    System.out.println("Thread with id : " + id + " starts working ...");
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}