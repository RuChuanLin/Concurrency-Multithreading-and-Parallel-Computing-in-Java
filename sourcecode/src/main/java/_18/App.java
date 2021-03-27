package _18;

class Process {
/*
product 方法會先執行
執行到一半時, 會遇到wait(), 此時線程會停下來，釋放出lock, 直到notify()被呼叫
才會繼續執行
 */
  public void produce() throws InterruptedException {
    synchronized (this) {
      System.out.println("It is producing...");
      wait();
      System.out.println("It is producing again!");
    }
  }

  public void consume() throws InterruptedException {
    Thread.sleep(1000); // sleep 1 sec, to make sure that this method will not be executed first

    /*
    這邊要非常注意 :
    呼叫 notify 後，並不會直接切換回 Thread1, 而是會繼續執行到完成整個 synchronized block
     */
    synchronized (this) {
      System.out.println("It is consuming things ~");
      notify();
      Thread.sleep(5000); // 雖然已經notify, 但還是會繼續執行接下來的代碼直到離開 sync block
    }
  }
}

public class App {

  public static void main(String[] args) {
    Process p = new Process();
    Thread t1 = new Thread(() -> {
      try {
        p.produce();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
    Thread t2 = new Thread(() -> {
      try {
        p.consume();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
    t1.start();
    t2.start();
  }
}
