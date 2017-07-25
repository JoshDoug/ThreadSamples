package FileTransferCP;

import javafx.concurrent.Task;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class FileConsumer extends Task implements Runnable, TransferBehaviour {
    Semaphore semaphoreConsumer;
    Semaphore semaphoreProducer;
    private volatile boolean exit = false;
    private TransferBuffer transferBuffer;

    public FileConsumer(Semaphore semaphoreConsumer, Semaphore semaphoreProducer, TransferBuffer transferBuffer) {
        this.semaphoreConsumer = semaphoreConsumer;
        this.semaphoreProducer = semaphoreProducer;
        this.transferBuffer = transferBuffer;
    }

    public Void call() {
        while (!exit) {
            try {
                semaphoreConsumer.acquire();
                System.out.println("Consumer aquired.");
                Thread.sleep(TimeUnit.SECONDS.toMillis(1));
                semaphoreProducer.release();
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

