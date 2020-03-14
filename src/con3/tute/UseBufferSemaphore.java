package con3.tute;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * A bounded buffer maintains a fixed number of "slots". Items can be
 * inserted into and removed from the buffer. The buffer has a maximum
 * size.
 */

class BoundedBufferSemaphore {
    // the maximum size of the bounded buffer
    final public static int MAXSIZE = 10;

    // the buffer
    List<Integer> buffer;

    // Semaphores
    private Semaphore mutex;
    private Semaphore notEmpty;
    private Semaphore notFull;

    public BoundedBufferSemaphore() {
        buffer = new ArrayList<Integer>();
        mutex = new Semaphore(1); // main semaphore
        notEmpty = new Semaphore(0); // = number of elements in the buffer
        notFull = new Semaphore(MAXSIZE); // = number of empty slots for elements
    }

    /**
     * Add an element to the end of the buffer if it is not full
     *   wait for buffer to empty a slot otherwise
     * @param input element to add
     * @throws InterruptedException
     */
    public void put(int input) throws InterruptedException {
        try {
            notFull.acquire(); // -- number of slots
            mutex.acquire(); // get permission
        } catch (InterruptedException e) {}
        buffer.add(input);

        mutex.release(); // release permission
        notEmpty.release(); // ++ number of elements
    }

    /**
     * Take an element from the front of the buffer if it is not empty
     *   wait for elements to appear otherwise
     * @return element from the front of the buffer
     * @throws InterruptedException
     */
    public int get() throws InterruptedException {
        try {
            notEmpty.acquire(); // -- number of elements
            mutex.acquire(); // get permission
        } catch (InterruptedException e) {}
        int result = buffer.remove(0);

        mutex.release(); // release permission
        notFull.release(); // ++ number of slots
        return result;
    }

    public int size() {
        int result = buffer.size();
        return result;
    }
}

/**
 * An instance of the Producer class produces new integers at random
 * intervals, and inserts them into a bounded buffer.
 */

class ProducerSemaphore extends Thread {
    // the buffer in which to insert new integers
    BoundedBufferSemaphore buffer;

    public ProducerSemaphore(BoundedBufferSemaphore buffer) {
        this.buffer = buffer;
    }

    public void run() {
        Random random = new Random();

        try {
            while (true) {

                //insert a random integer 0..999
                int next = random.nextInt(1000);
                buffer.put(next);

                //sleep for a random period between
                //0 and 50 milliseconds
                int sleep = random.nextInt(50);
                Thread.sleep(sleep);

                System.err.println("b.size() = " + buffer.size());
            }
        } catch (InterruptedException e) {}
    }
}

/**
 * An instance of the Consumer class consumes integers from a bounded
 * buffer at random intervals.
 */

class ConsumerSemaphore extends Thread {
    // the buffer in which to insert new integers
    BoundedBufferSemaphore buffer;

    public ConsumerSemaphore(BoundedBufferSemaphore buffer) {
        this.buffer = buffer;
    }

    public void run() {
        Random random = new Random();

        try {
            while (true) {

                //get the next integer from the buffer
                int next = buffer.get();

                System.err.println("next = " + next);

                //sleep for a random period between
                //0 and 49 milliseconds
                int sleep = random.nextInt(50);
                Thread.sleep(sleep);
            }
        } catch (InterruptedException ignored) {}
    }
}

public class UseBufferSemaphore {
    public static void main(String [] args) {

        BoundedBufferSemaphore buffer = new BoundedBufferSemaphore();
        ProducerSemaphore p = new ProducerSemaphore(buffer);
        ConsumerSemaphore c = new ConsumerSemaphore(buffer);

        p.start();
        c.start();
    }
}
