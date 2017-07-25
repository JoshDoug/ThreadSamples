package FileTransferCP;

import javafx.concurrent.Task;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class FileProducer extends Task implements Runnable, TransferBehaviour {
    Semaphore semaphoreProducer;
    Semaphore semaphoreConsumer;
    private volatile boolean exit = false;
    private TransferBuffer transferBuffer;
    private String fileStart = "/Users/jds/Learning/Java/ThreadSamples/TestTransfer/TestFile.csv";

    public FileProducer(Semaphore semaphoreProducer, Semaphore semaphoreConsumer, TransferBuffer transferBuffer) {
        this.semaphoreProducer = semaphoreProducer;
        this.semaphoreConsumer = semaphoreConsumer;
        this.transferBuffer = transferBuffer;
    }

    public Void call() {
        while (!exit) {
            try {
                semaphoreProducer.acquire();
                System.out.println("Producer acquired.");
                Thread.sleep(TimeUnit.SECONDS.toMillis(1));
                semaphoreConsumer.release();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            System.out.println("Exit: " + exit);
        }
        return null;
    }

    public void stop() {
        this.exit = true;
    }

    public void checkProgress() {

    }
}
