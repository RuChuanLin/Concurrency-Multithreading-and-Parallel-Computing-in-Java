package _11;

class Runner1 implements Runnable {


  @Override
  public void run() {
    for (int i = 0; i < 10; i++) {
      try {
        Thread.sleep(10);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("Runner1 : " + i);
    }
  }
}

class Runner2 implements Runnable {

  @Override
  public void run() {
    for (int i = 0; i < 10; i++) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("Runner2 : " + i);
    }
  }
}

public class Main {

  public static void main(String[] args) {
/*
This is not parallel execution , but multithreaded execution (with time-slicing)
 */
    Thread t1 = new Thread(new Runner1());
    Thread t2 = new Thread(new Runner2());
    t1.start();
    t2.start();

    try {
      t1.join(); // join() 的作用 : 該線程執行完畢後才會繼續往下做
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.println("Finished with threads ... "); // 這行會最先印出
  }
}
