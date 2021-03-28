package _27;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/*
現在把 worker2 改成先去取 lock1 , 再取 lock2

此時兩個線程啟動時, 兩個 worker 其一會先取到 lock1，另一邊會被block住

而在一邊完成後，因為 lock2 沒有被任何 thread 鎖住，所以可以取到

 */
public class DeadLockFix {

  private final Lock lock1 = new ReentrantLock(true);
  private final Lock lock2 = new ReentrantLock(true);

  public static void main(String[] args) {
    DeadLockFix deadLock = new DeadLockFix();
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
