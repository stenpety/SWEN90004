package con02.lecture;

public class MyRunnable implements Runnable {
    public void run() {
        System.out.println("SWEN90004 runnable");
        for (int i=0; i < 10; ++i) {
            System.out.println("\t runnable " + i);
        }
    }
}
