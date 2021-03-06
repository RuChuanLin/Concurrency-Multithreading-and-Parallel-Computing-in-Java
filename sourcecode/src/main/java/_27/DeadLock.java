package _27;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/*
死鎖 :
現在有兩個 Lock 物件，和兩個 worker 方法。
用兩個執行緒啟動兩個 worker 方法，讓它們分別去 acquire 這兩個鎖。

因此， worker1 會取得 lock1 ； worker2 會取得 lock2
接著， worker1 會想要再取得 lock2; worker2 會想要再取得 lock1

此時，worker1 正在等待 lock2 釋出 ; worker2 正在等待 lock1 釋出
然而，lock1 已經被 worker1 取走 ; lock2 已經被 worker2 取走

而, worker1 必須要得到 lock2, 才會釋出 lock1 ， worker2 同理

因此，它們現在就在「互相等待對方釋出鎖」，但永遠也等不到
*/
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
    lock2.lock();
    System.out.println("Worker 2 acquires lock 2");
    try {
      Thread.sleep(300);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    lock1.lock();
    System.out.println("Worker 2 acquires lock 1");
    lock1.unlock();
    lock2.unlock();
    System.out.println("Worker 2 Finish work");
  }
}
