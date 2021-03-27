package _22;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Worker {

  private final Lock lock = new ReentrantLock();
  private final Condition condition = lock.newCondition();

  public static void main(String[] args) {
    Worker worker = new Worker();
    Thread t1 = new Thread(() -> {
      try {
        worker.produce();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
    Thread t2 = new Thread(() -> {
      try {
        worker.consume();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
    t1.start();
    t2.start();
    try {
      t1.join();
      t2.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void produce() throws InterruptedException {
    lock.lock();
    System.out.println("Producing...");
    Thread.sleep(2000);
    System.out.println("Finished producing .. ");
    condition.await();
    System.out.println("Produce again");
    lock.unlock();
  }

  public void consume() throws InterruptedException {
    lock.lock();
    System.out.println("Consuming...");
    Thread.sleep(2000);
    System.out.println("Finished consuming .. ");
    condition.signal();
    lock.unlock();
  }
}
