package con02.lecture;

class CountMain extends Thread {
    static volatile int n = 0;

    public void run() {
      int temp;
      for (int i = 0; i < 10; i++) {
        temp = n;
        n = temp + 1;
      }
    }

    public static void main(String[] args) {
        CountMain p = new CountMain();
        CountMain q = new CountMain();
        p.start();
        q.start();
        try {
            p.join();
            q.join();
        } catch (InterruptedException e) { }
      System.out.println("The final value of n is " + n);
    }
}
