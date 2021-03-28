package _32;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/*
   Semaphore(int permit, boolean fair)

   Semaphore 主要的功能其實和 lock , wait()/notify() 相似
   都是讓執行序取共用資源，取到共用資源的線程可以開始工作，工作完後釋出資源。
   未取到資源的線程則等待共用資源釋出，取得後才會開始工作。

   只是semaphore可以更簡單地指定資源數量，從而輕鬆指定同一時段要有幾個線程在工作。

   在以下的範例中，有一個下載器，每下載一個東西要花2秒，如果要下載12次，單線程的話要花24秒。

   若要多線程，也不希望一次開12個線程。
   Semaphore可以限制一次只開比方說3個線程在工作。
   當線程啟動時，會 acquire semaphore permit
   如果沒有acquire到 permit , 則線程休眠, 直到有 permit 釋出。

 */
public class App {

  public static void main(String[] args) {
    ExecutorService executorService = Executors.newCachedThreadPool();
    for (int i = 0; i < 12; i++) {
      executorService.execute(Downloader.INSTANCE::downloadData);
    }
  }

  enum Downloader {
    INSTANCE;
    private final Semaphore semaphore = new Semaphore(3, true);

    public void downloadData() {
      try {
        semaphore.acquire();
        download();
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        semaphore.release();
      }
    }

    private void download() {
      System.out.println("Downloading data from the web...");
      try {
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

}
