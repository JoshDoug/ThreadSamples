/**
 * Created by joshstringfellow on 07/02/2017.
 * Consumer producer problem in Java
 */

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ConsumerProducer extends Thread
{

    private int id; // thread id
    private static int sharednumber; // static shared data is available in all instances of threads
    static private Semaphore sem1; // reference to the semaphore 1
    static private Semaphore sem2; // reference to the semaphore 2

    public ConsumerProducer(int i)
    {
        id = i;
    }

    /* depositing value into shared number buffer */
    private void deposit(int n)
    {
        sharednumber=n;
    }

    /* retrieving value from shared number buffer */
    private int retrieve()
    {
        return sharednumber;
    }


    /* run method of the thread */
    public void run()
    {
        int n;

        if (id == 1) // thread 1 is producer
        {
            for (n=1; n <= 100; n++)          {
                try // produce 100 values
                {
                    // todo: regulate access with sem 1
                    sem1.acquire();
                    deposit(n);
                    sem2.release();
                } catch (InterruptedException ex) {
                    Logger.getLogger(ConsumerProducer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }


        if (id == 2) // thread 2 is consumer
        {
            for (n=1; n <= 100; n++)          {
                try // retrieve 100 values
                {
                    // todo: regulate access with sem 2
                    sem2.acquire();
                    System.out.println( retrieve());
                    sem1.release();
                } catch (InterruptedException ex) {
                    Logger.getLogger(ConsumerProducer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static void main(String[] args)
    {
        final int N = 2; // number of threads

        System.out.println("Starting ...");

        /* Initialise two semaphores with 1 permit   */
        sem1 = new Semaphore(1);  // sem1 regulates depositing
        sem2 = new Semaphore(1);  // sem2 regulates retrieving

        // todo: acquire a permit for semaphore 2 to wait
        try {
            sem2.acquire();
        } catch(InterruptedException e) {

        }

        /* Create array for N Threads */
        ConsumerProducer[] thread = new ConsumerProducer[N];

        for (int i = 0; i < N; i++)
        {
            /* Initialise thread */
            thread[i] = new ConsumerProducer(i+1);
	        /* start thread */
            thread[i].start();
        }
    }
}