package con02.tute;

class Count extends Thread {

    // number of increments per thread
    static int N = 1000;

    // shared data
    static volatile int counter = 0;
    
    // protocol variables
    // ...

    public void run() {
        int temp;
        for (int i = 0; i < N; i++) {
        // non-critical section

        // pre-protocol section

        // critical section
        temp = counter;
        counter = temp + 1;

        // post-protocol section
        }
    }

    public static void main(String[] args) {
        int incorrect = 0;
        for (int i = 0; i < Integer.parseInt(args[0]); ++i) {
            counter = 0;
            Count p = new Count();
            Count q = new Count();
            p.start();
            q.start();
            try { p.join(); q.join(); }
            catch (InterruptedException e) { }
            System.out.println("The final value of the counter is " + counter);
            incorrect += counter != 2*N ? 1 : 0;
        }
        System.out.println("Incorrect counts: " + incorrect + "/" + args[0]);
    }
}
