package _24;

class Worker implements Runnable {

  /*
  volatile : 確保 CPU 一定會向 RAM 取值, 而不會從 CPU cache 取值。
  重要 : 即使不加 volatile ，CPU 也可能向 RAM 取值，只是不保證。
   */
  private volatile boolean terminated;

  @Override
  public void run() {
    while (!terminated) {
      System.out.println("Working class is running...");
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public void setTerminated(boolean terminated) {
    this.terminated = terminated;
  }
}

public class Volatile {

  public static void main(String[] args) {
    Worker worker = new Worker();
    Thread t1 = new Thread(worker);
    t1.start();
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    worker.setTerminated(true);
    System.out.println("worker thread is terminated...?");
  }
}
