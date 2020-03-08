package con02.tute;

class Attempt5s implements Runnable {

    // Class properties
    String threadName;

    // Constructor
    public Attempt5s(String threadName) {
        this.threadName = threadName;
    }

    // number of increments per thread
    static int N = 1000;

    // shared data
    static volatile int counter = 0;
    
    // protocol variables
    static volatile int turn;
    static volatile int p;
    static volatile int q;

    public void run() {
        int temp;
        for (int i = 0; i < N; ++i) {
            // non-critical section

            // pre-protocol section
            if (threadName.equals("p")) p = 1; else q = 1;// set p/q = 1 lock

            while ( 0 != (threadName.equals("p") ? q : p)) {
                if (turn == (threadName.equals("p") ? 2 : 1)) {
                    if (threadName.equals("p")) p = 0; else q = 0; // p/q = 0
                    while (turn == (threadName.equals("p") ? 2 : 1)) { } // idle
                    if (threadName.equals("p")) p = 1; else q = 1; // p/q = 1
                }
            }
            // end of pre-protocol

            // critical section
            temp = counter;
            counter = temp + 1;
            // end of critical section

            // post-protocol section
            turn = threadName.equals("p") ? 2 : 1;
            if (threadName.equals("p")) p = 0; else q = 0; // set p/q = 0 unlock
            // end of post-protocol
        }
    }

    public static void main(String[] args) {
        int incorrect = 0;
        for (int i = 0; i < Integer.parseInt(args[0]); ++i) {
            counter = 0;
            turn = 1;
            p = 0; // static
            q = 0; // static
            Thread p = new Thread(new Attempt5s("p"));
            Thread q = new Thread(new Attempt5s("q"));
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
