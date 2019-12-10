package con02.lecture;

public class Count extends Thread {
    static volatile int n = 0;

    public void run() {
      int temp;
      for (int i = 0; i < 10; i++) {
        temp = n;
        n = temp + 1;
      }
    }

    public static int getN() {
        return n;
    }
}
