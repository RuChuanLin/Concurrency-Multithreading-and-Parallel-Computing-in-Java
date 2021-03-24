package _9;

class Runner3 extends Thread {

  @Override
  public void run() {
    for (int i = 0; i < 10; i++) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("Runner3 : " + i);
    }
  }
}

class Runner4 extends Thread {

  @Override
  public void run() {
    for (int i = 0; i < 10; i++) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("Runner4 : " + i);
    }
  }
}

public class Sleep {

  public static void main(String[] args) {
    /*
This is not parallel execution , but multithreaded execution (with time-slicing)
 */
    Thread t3 = new Thread(new Runner3());
    Thread t4 = new Thread(new Runner4());
    t3.start();
    t4.start();
  }
}
