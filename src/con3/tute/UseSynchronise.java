package con3.tute;

class P extends Thread {
    //the shared synch instance
    protected Synchronise s;

    public P(Synchronise s)
    {
        this.s = s;
    }

    public void run() {
        for (int i=0; i<10; ++i) {
            task1p();
            s.synch();
            task2p();
        }
    }

    private void task1p()
    {
        System.out.println("1p");
    }

    private void task2p()
    {
        System.out.println("2p");
    }
}

class Q extends Thread {
    //the shared synch instance
    protected Synchronise s;

    public Q(Synchronise s)
    {
        this.s = s;
    }

    public void run() {
        for (int i=0; i<10; ++i) {
            task1q();
            s.synch();
            task2q();
        }
    }

    private void task1q()
    {
        System.out.println("1q");
    }

    private void task2q()
    {
        System.out.println("2q");
    }
}

class Synchronise {
    boolean flag = false;

    public synchronized void synch() {
        if (flag) {
            notify();
            flag = false;
        } else {
            // waiting for other thread
            flag = true;
            try {
                wait();
            } catch (InterruptedException e) { }
        }
    }
}

class UseSynchronise {
    public static void main(String [] args) {
        Synchronise s = new Synchronise();
        Thread p = new P(s);
        Thread q = new Q(s);
        p.start();
        q.start();

        try {
            p.join();
            q.join();
        } catch (InterruptedException e) {
            System.out.println("Exception: " + e.toString());
        }
    }
}
