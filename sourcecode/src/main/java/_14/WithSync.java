package _14;

public class WithSync {

  public static int counter = 0;

  // 加上 synchronized 關鍵字, 確保 heap 變數不會在同一時間被修改
  public static synchronized void increment() {
    counter++;
  }
  public static void process() {
    Thread t1 = new Thread(new Runnable() {
      @Override
      public void run() {
        for (int i = 0; i < 10000; i++) {
          increment();
        }
      }
    });
    Thread t2 = new Thread(new Runnable() {
      @Override
      public void run() {
        for (int i = 0; i < 10000; i++) {
          increment();
        }
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

    System.out.println(counter);
  }

  public static void main(String[] args) {
    process();
  }
}
