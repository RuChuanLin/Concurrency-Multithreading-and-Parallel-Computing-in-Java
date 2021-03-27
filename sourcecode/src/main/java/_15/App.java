package _15;

public class App {

  public static int counter1 = 0;
  public static int counter2 = 0;

  /*
因為 App object 只有一個 lock, 因此 synchronized 方法不能被"同時"執行。
"同時" : time-slicing algorithm 會處理兩個線程
   */
  public static synchronized void increment1() {
    counter1++;
  }

  public static synchronized void increment2() {
    counter2++;
  }

  /*
  當 t1 呼叫了 increment1 時, 它會取得 App object 的 intrinsic lock。這是唯一的, 所以 t2 此時無法取得。
  當 t1 結束時, 會釋放出 intrinsic lock , 讓 t2 可以取用


   */
  public static void process() {
    Thread t1 = new Thread(new Runnable() {
      @Override
      public void run() {
        for (int i = 0; i < 10000; i++) {
          increment1();
        }
      }
    });
    Thread t2 = new Thread(new Runnable() {
      @Override
      public void run() {
        for (int i = 0; i < 10000; i++) {
          increment2();
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

    System.out.println(counter1);
    System.out.println(counter2);
  }

  public static void main(String[] args) {
    process();
  }
}
