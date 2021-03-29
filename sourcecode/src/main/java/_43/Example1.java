package _43;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class Worker1 implements Runnable {

  private final BlockingQueue<Integer> blockingQueue;

  public Worker1(BlockingQueue<Integer> blockingQueue) {
    this.blockingQueue = blockingQueue;
  }

  @Override
  public void run() {
    int counter = 0;
    while (true) {
      try {
        blockingQueue.put(counter);
        System.out.println("Putting items to the queue, count : " + counter);
        counter++;
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}

class Worker2 implements Runnable {

  private final BlockingQueue<Integer> blockingQueue;

  public Worker2(BlockingQueue<Integer> blockingQueue) {
    this.blockingQueue = blockingQueue;
  }

  @Override
  public void run() {
    while (true) {
      try {
        int element = blockingQueue.take();
        System.out.println("Taking items away from the queue, number : " + element);

        Thread.sleep(300);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}

public class Example1 {

  public static void main(String[] args) {
    BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
    Worker1 w1 = new Worker1(queue);
    Worker2 w2 = new Worker2(queue);
    new Thread(w1).start();
    new Thread(w2).start();
  }
}
