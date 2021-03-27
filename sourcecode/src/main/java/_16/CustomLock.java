package _16;


public class CustomLock {

  public static int counter1 = 0;
  public static int counter2 = 0;

  private static final Object lock1 = new Object();
  private static final Object lock2 = new Object();

  /*
  現在 increment1 和 increment2 方法裡面的 sync 取鎖並不是向 class 取，而是自訂一個物件來當 lock。
  因為兩者的 lock 不同, 所以他們可以"同時"執行。
  **「同時」不意味平行處理，而是用 time-slicing 的方式來做到類似同時處理的概念**

  在以下的範例中，increment1 和 increment2 分別對 counter loop增加10000。
  如果鎖是同一把, 則會等到一個方法結束後, 再去執行另一個方法, 打印出來的結果會是 increment1連續出現10000筆, 再出現increment2 10000筆(或相反)

  但現在鎖換成不同的, 所以increment1和increment2會輪流出現(time-slicing)。
   */
  public static void increment1() {
    synchronized (lock1) {
      for (int i = 0; i < 10000; i++) {
        counter1++;
        System.out.println("increment1");
      }
    }

  }

  public static void increment2() {
    synchronized (lock2) {
      for (int i = 0; i < 10000; i++) {
        counter2++;
        System.out.println("increment2");
      }
    }
  }

  public static void process() {
    Thread t1 = new Thread(CustomLock::increment1);
    Thread t2 = new Thread(CustomLock::increment2);
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
