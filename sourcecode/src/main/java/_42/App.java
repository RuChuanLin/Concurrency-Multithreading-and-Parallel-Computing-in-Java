package _42;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Worker implements Runnable {

  private final int id;
  private final Random random;
  private final CyclicBarrier cyclicBarrier;

  public Worker(int id, CyclicBarrier cyclicBarrier) {
    this.id = id;
    this.cyclicBarrier = cyclicBarrier;
    this.random = new Random();
  }

  @Override
  public void run() {
    doWork();

  }

  private void doWork() {
    System.out.println("Thread with id : " + id + " starts working ...");
    try {
      Thread.sleep(random.nextInt(3000));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.println("Thread with id : " + id + " finishes");
    try {
      cyclicBarrier.await();
      // await 結束後, 可以繼續處理事情
      System.out.println("ID : " + id + ", after await() ... ");
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (BrokenBarrierException e) {
      e.printStackTrace();
    }
  }
}

public class App {

  public static void main(String[] args) {
    ExecutorService executorService = Executors.newFixedThreadPool(5);
    // 當所有任務結束時(N reaches 0), 會執行 callback function
    CyclicBarrier cyclicBarrier = new CyclicBarrier(5,
        () -> System.out.println("All tasks are finished"));
    for (int i = 0; i < 5; i++) {
      executorService.execute(new Worker(i + 1, cyclicBarrier));
    }
    executorService.shutdown();
  }
}
