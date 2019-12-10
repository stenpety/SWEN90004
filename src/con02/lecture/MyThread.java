package con02.lecture;

public class MyThread  extends Thread {

    public void run() {
        System.out.println("SWEN90004 thread");
        for (int i=0; i < 10; ++i) {
            System.out.println("\t thread " + i);
        }
    }
}
