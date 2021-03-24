package _12;

class DaemonThread implements Runnable {

  @Override
  public void run() {
    while (true) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("Daemon thread is running ...");
    }
  }
}

class NormalThread implements Runnable {

  @Override
  public void run() {
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("Normal thread finished execution ...");
  }
}

/*
Daemon thread的特性 :
在所有的worker thread結束後會結束
在以下的代碼中, 將t1設成daemon thread, 然後是無限迴圈, 會無限印出文字
t2是 worker thread, 在3秒後會自動停止

可以觀察到在worker thread 結束後, 整個程序就結束了
 */
public class Main {

  public static void main(String[] args) {
    Thread t1 = new Thread(new DaemonThread());
    Thread t2 = new Thread(new NormalThread());
    t1.setDaemon(true);
    t1.start();
    t2.start();
  }
}
