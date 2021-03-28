package _27;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLock {

  private final Lock lock1 = new ReentrantLock(true);
  private final Lock lock2 = new ReentrantLock(true);

  public static void main(String[] args) {
    DeadLock deadLock = new DeadLock();
    new Thread(deadLock::worker1, "worker 1").start();
    new Thread(deadLock::worker2, "worker 2").start();
  }

  public void worker1() {
    lock1.lock();
    System.out.println("Worker 1 acquires lock 1");
    try {
      Thread.sleep(300);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    lock2.lock();
    System.out.println("Worker 1 acquires lock 2");
    lock1.unlock();
    lock2.unlock();
    System.out.println("Worker 1 Finish work");
  }
  public void worker2() {
    lock1.lock();
    System.out.println("Worker 2 acquires lock 2");
    try {
      Thread.sleep(300);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    lock2.lock();
    System.out.println("Worker 2 acquires lock 1");
    lock1.unlock();
    lock2.unlock();
    System.out.println("Worker 2 Finish work");
  }
}
