package _15;

public class AppBetterVersion {

  public static int counter1 = 0;
  public static int counter2 = 0;


  // 將 synchronized 寫在方法裡, 而不是方法關鍵字
  public static void increment1() {
    synchronized (AppBetterVersion.class) {
      counter1++;
    }

  }

  public static void increment2() {
    synchronized (AppBetterVersion.class) {
      counter2++;
    }
  }

  public static void process() {
    Thread t1 = new Thread(() -> {
      for (int i = 0; i < 10000; i++) {
        increment1();
      }
    });
    Thread t2 = new Thread(() -> {
      for (int i = 0; i < 10000; i++) {
        increment2();
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

    System.out.println(counter1);
    System.out.println(counter2);
  }

  public static void main(String[] args) {
    process();
  }
}
