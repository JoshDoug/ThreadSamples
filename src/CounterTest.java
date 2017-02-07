/**
 * Created by joshstringfellow on 07/02/2017.
 * Testing the counting of a shared variable between threads
 */

public class CounterTest extends Thread
{
    private int id; // thread number

    private static int counter;

    public CounterTest(int i)
    {
        id = i;
    }

    /* run method of the thread */
    public void run()
    {
        int n;
        System.out.println("Thread " + id + " running");

        for (n=0; n < 10000000; n++) incrementCounter();

        System.out.println("Thread " + id + " finished -> counter = " + counter);

    }

    synchronized static void incrementCounter()
    {
        counter++;
    }

    public static void main(String[] args)
    {
        final int N = 4;

       /* Initialise counter to zero */
        counter = 0;

        CounterTest[] thread = new CounterTest[N];

        System.out.println("Counter Test ...");
        System.out.println("Press Enter to continue");
        try{System.in.read();}
        catch(Exception e){}

        for (int i = 0; i < N; i++)
        {
            /* initialise each thread */
            thread[i] = new CounterTest(i+1);
	    /* start each thread */
            thread[i].start();
        }


    }
}

