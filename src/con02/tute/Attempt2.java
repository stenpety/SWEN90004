package con02.tute;

import java.util.HashMap;

class Attempt2 implements Runnable {

    // Class properties
    String threadName;
    String oppThread;

    // Constructor
    public Attempt2(String threadName) {
        this.threadName = threadName;
        oppThread = "q".equals(this.threadName) ? "p" : "q"; // not very general solution...
    }

    // number of increments per thread
    static int N = 1000;

    // shared data
    static volatile int counter = 0;
    
    // protocol variables
    static volatile HashMap<String, Integer> turn = new HashMap<>();

    public void run() {
        int temp;
        for (int i = 0; i < N; ++i) {
            // non-critical section

            // pre-protocol section
            while (turn.get(this.oppThread) != 0) {
                // run idle
            }
            turn.put(this.threadName, 1); // set p/q = 1 lock
            // end of pre-protocol

            // critical section
            temp = counter;
            counter = temp + 1;
            // end of critical section

            // post-protocol section
            turn.put(this.threadName, 0); // set p/q = 0 unlock
            // end of post-protocol
        }
    }

    public static void main(String[] args) {
        int incorrect = 0;
        for (int i = 0; i < Integer.parseInt(args[0]); ++i) {
            counter = 0;
            turn.put("p", 0); // set p = 0
            turn.put("q", 0); // set q = 0
            Thread p = new Thread(new Attempt2("p"));
            Thread q = new Thread(new Attempt2("q"));
            p.start();
            q.start();
            try {
                p.join();
                q.join();
            } catch (InterruptedException e) {
                System.out.println("Error in joining threads P & Q: " + e.toString());
            }
            System.out.println("Run: " + i + "; counter value: " + counter);
            incorrect += counter != 2*N ? 1 : 0;
        }
        System.out.println("Incorrect counts: " + incorrect + "/" + args[0]);
    }
}
