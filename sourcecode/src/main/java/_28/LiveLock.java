package _28;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
活鎖 :

和死鎖很像，都是兩個 worker 都要去 acquire 兩個 lock 物件

不同之處在於，如果取不到 Lock 物件，則會直接去解鎖該物件，然後重取一遍

最終結果就是 : 兩個 worker 都會在獲取自己的物件和解鎖對方的物件中庸庸碌碌度過一生。

 */
public class LiveLock {

  private final Lock lock1 = new ReentrantLock(true);
  private final Lock lock2 = new ReentrantLock(true);

  public static void main(String[] args) {
    LiveLock liveLock = new LiveLock();
    System.out.println("start");
    new Thread(liveLock::worker1, "worker 1").start();
    new Thread(liveLock::worker2, "worker 2").start();
  }

  public void worker1() {
    while (true) {
      try {
        lock1.tryLock(50, TimeUnit.MILLISECONDS);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("Worker1 acquires the lock1");
      System.out.println("Worker1 tries to get the lock2");

      if (lock2.tryLock()) {
        System.out.println("Worker1 acquires the lock2");
        lock2.unlock();
      } else {
        System.out.println("Worker1 can not acquire lock2");
        continue;
      }
      break;
    }
    lock1.unlock();
    lock2.unlock();
    System.out.println("Worker 1 Finish work");
  }

  public void worker2() {
    while (true) {
      try {
        lock2.tryLock(50, TimeUnit.MILLISECONDS);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("Worker2 acquires the lock2");
      System.out.println("Worker2 tries to get the lock1");

      if (lock1.tryLock()) {
        System.out.println("Worker2 acquires the lock1");
        lock1.unlock();
      } else {
        System.out.println("Worker2 can not acquire lock1");
        continue;
      }
      break;
    }
    lock1.unlock();
    lock2.unlock();
    System.out.println("Worker 2 Finish work");
  }
}
