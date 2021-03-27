package _14;

public class WithoutSync {

  public static int counter = 0;

  /*
  兩個線程, 分別向heap取counter變數, 對其修改值, 修改步驟 :
    1. 取變數 (讀)
    2. 加1, 改變值 (改)
    3. 寫回memory (寫)
  三個步驟並不是"原子操作", 因此可能會發生多線程在同一時間取得counter, 造成取得的值相同
  如果取值相同, 分別進行加1後, 得到的值還是會相同
  此時如果把值寫回counter, 整體來說會少1
   */
  public static void process() {
    Thread t1 = new Thread(new Runnable() {
      @Override
      public void run() {
        for (int i = 0; i < 10000; i++) {
          counter++;
        }
      }
    });
    Thread t2 = new Thread(new Runnable() {
      @Override
      public void run() {
        for (int i = 0; i < 10000; i++) {
          counter++;
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
