package con02.tute;

class Attempt1 implements Runnable {

    // Class properties
    int myTurn;

    public Attempt1(int myTurn) {
        this.myTurn = myTurn;
    }

    // number of increments per thread
    static int N = 1000;

    // shared data
    static volatile int counter = 0;
    
    // protocol variables
    static volatile int turn = 1;

    public void run() {
        int temp;
        for (int i = 0; i < N; ++i) {
        // non-critical section

        // pre-protocol section
        while (turn != myTurn) {
            ; // run idle
        }
        // end of pre-protocol

        // critical section
        temp = counter;
        counter = temp + 1;
        // end of critical section

        // post-protocol section
        turn = myTurn == 1 ? 2 : 1; // not very general solution..
        // end of post-protocol
        }
    }

    public static void main(String[] args) {
        int incorrect = 0;
        for (int i = 0; i < Integer.parseInt(args[0]); ++i) {
            counter = 0;
            Thread p = new Thread(new Attempt1(1)); // turn 1
            Thread q = new Thread(new Attempt1(2)); // turn 2
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
