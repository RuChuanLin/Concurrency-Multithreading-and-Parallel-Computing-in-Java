package _36;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Work implements Runnable {

  private final int id;

  public Work(int id) {
    this.id = id;
  }

  @Override
  public void run() {
    System.out
        .println("ID :" + id + ", Thread : " + Thread.currentThread().getName() + ", Thread ID : " +
            Thread.currentThread().getId());
    long duration = (long) (Math.random() * 5);
    try {
      TimeUnit.SECONDS.sleep(duration);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}


public class FixedThreadExecutor {

  public static void main(String[] args) {
    ExecutorService executorService = Executors.newFixedThreadPool(2);
    for (int i = 0; i < 10; i++) {
      executorService.execute(new Work(i + 1));
    }
    // We need to shut down the executor !!!!

  }
}
