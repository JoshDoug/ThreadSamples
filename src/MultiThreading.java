/**
 * Created by joshstringfellow on 07/02/2017.
 * Multi-Threading application
 */

import java.util.Random;

public class MultiThreading extends Thread
{

    private int id; // thread number

    private static double[] shareddata;

    public MultiThreading(int i)
    {
        id = i;
    }

    /* run method of the thread */
    public void run()
    {
        int a;

        Random generator = new Random();

        System.out.println("Thread " + id + " running");
        long t = System.currentTimeMillis()/1000;

        if (id==1) {
            for (int i=0; i < 10; i++) {
                for (a=0; a < 10000000; a++) {
                    shareddata[a]=Math.cos(a+Math.sqrt(a*generator.nextDouble()));
                }
            }
        }
//        if (id==2) {
//          for (int i=0; i < 10; i++) {
//            for (a=5000000; a < 10000000; a++) {
//                shareddata[a]=Math.cos(a+Math.sqrt(a*generator.nextDouble()));
//            }
//          }
//        }

        System.out.println("Thread " + id + " took " + (System.currentTimeMillis()/1000 - t) + " seconds");


    }

    public static void main(String[] args)
    {
        final int N = 1;

        shareddata = new double[10000000];

        System.out.println("Starting Multi-threading...");

        MultiThreading[] thread = new MultiThreading[N];

        System.out.println("Press Enter to continue");
        try{System.in.read();}
        catch(Exception e){}

        for (int i = 0; i < N; i++)
        {
            /* initialise each thread */
            thread[i] = new MultiThreading(i+1);
	    /* start each thread */
            thread[i].start();
        }
    }
}