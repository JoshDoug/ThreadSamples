/**
 * Created by joshstringfellow on 07/02/2017.
 * Mutual exclusion using the Semaphore class
 */

import java.util.concurrent.Semaphore;


public class SemTest extends Thread {

    private int id; // thread id
    private static int sharednumber; // static shared data is available in all instances of threads
    static private Semaphore sem; // reference to the semaphore

    public SemTest(int i)
    {
        id = i;
    }

    /* critical part of the processing: incrementing shared variable by one */
    private void critical()
    {
        sharednumber++;
    }

    /* run method of the thread */
    public void run()
    {
        int n;
        System.out.println("Thread " + id + " running");

        for (n=0; n < 1000000; n++)
        {
            try
            {
                sem.acquire(); // get permit to access critical section
                critical();
                sem.release(); // release permit after access to the critical section

            } catch (InterruptedException e) { }
        }

        System.out.println("Thread " + id + " finished -> sharednumber = " + sharednumber);
    }

    public static void main(String[] args)
    {
        final int N = 2; // number of threads

        System.out.println("Starting ...");

        /* Initialise semaphore with 1 permit   */
        sem = new Semaphore(1);

        /* Create array for two Threads */
        SemTest[] thread = new SemTest[N];

        for (int i = 0; i < N; i++)
        {
            /* Initialise thread */
            thread[i] = new SemTest(i+1);
	    /*start thread */
            thread[i].start();
        }
    }
}