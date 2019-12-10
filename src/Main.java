import con02.lecture.MyThread;
import con02.lecture.MyRunnable;
import con02.lecture.Count;

public class Main {

    public static void main(String[] argv) {

        System.out.println("Hello");

        /*
        Thread myThread = new MyThread();
        myThread.start();

        Thread myRunnable = new Thread(new MyRunnable());
        myRunnable.start();
        */

        Count p = new Count();
        Count q = new Count();
        p.start();
        q.start();
        try {
            p.join();
            q.join();
        } catch (InterruptedException e) {
            // ---
        }
        System.out.println("The final value of n is " + Count.getN());

    }
}
