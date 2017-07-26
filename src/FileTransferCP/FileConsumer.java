package FileTransferCP;

import javafx.concurrent.Task;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.Semaphore;

public class FileConsumer extends Task implements Runnable, TransferBehaviour {
    Semaphore semaphoreConsumer;
    Semaphore semaphoreProducer;
    private volatile boolean exit = false;
    private TransferBuffer transferBuffer;
    private String fileEnd = "/Users/jds/Learning/Java/ThreadSamples/TestTransfer/EndZip.zip";

    public FileConsumer(Semaphore semaphoreConsumer, Semaphore semaphoreProducer, TransferBuffer transferBuffer) {
        this.semaphoreConsumer = semaphoreConsumer;
        this.semaphoreProducer = semaphoreProducer;
        this.transferBuffer = transferBuffer;
    }

    public Void call() {
        System.out.println("Start Consumer");
        try(FileOutputStream fos = new FileOutputStream(new File(fileEnd))) {
            int length;
            byte[] buffer = new byte[1048576];
            while (!exit) {
                try {
                    System.out.println("Now waiting to acquire on Consumer thread");
                    semaphoreConsumer.acquire();
                    System.out.println("Consumer aquired.");
                    if(transferBuffer.getLength() > 0) {
                        fos.write(transferBuffer.getBuffer(), 0, transferBuffer.getLength());
                    }
                    transferBuffer.setLength(0);
                    System.out.println("Consumer released.");

                    semaphoreProducer.release();
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            System.out.println("Consumer Thread finished");
        }
        return null;
    }

    public void stop() {
        this.exit = true;
        semaphoreConsumer.release();
        System.out.println("Exit Consumer Called");
    }

    public void checkProgress() {

    }
}

