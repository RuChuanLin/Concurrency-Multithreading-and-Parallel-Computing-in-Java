package _38;

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
      System.out.println("Interrupting");
      Thread.currentThread().interrupt();
    }
  }
}


public class ThreadPool {

  public static void main(String[] args) {
    ExecutorService executor = Executors.newFixedThreadPool(10);
    for (int i = 0; i < 100; i++) {
      executor.execute(new Work(i + 1));
    }
    // We prevent the executor to execute any further tasks
    executor.shutdown();

    // terminate actual (running) tasks

    try {
      if (!executor.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
        executor.shutdownNow();
      }
    } catch (InterruptedException e) {
      executor.shutdownNow();
    }

  }
}
