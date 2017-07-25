package FileTransferCP;

import javafx.concurrent.Task;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class FileProducer extends Task implements Runnable, TransferBehaviour {
    Semaphore semaphoreProducer;
    Semaphore semaphoreConsumer;
    private volatile boolean exit = false;
    private TransferBuffer transferBuffer;
    private String fileStart = "/Users/jds/Learning/Java/ThreadSamples/TestTransfer/StartZip.zip";
    private TransferBehaviour transferManager;

    public FileProducer(Semaphore semaphoreProducer, Semaphore semaphoreConsumer, TransferBuffer transferBuffer, TransferBehaviour transferManager) {
        this.semaphoreProducer = semaphoreProducer;
        this.semaphoreConsumer = semaphoreConsumer;
        this.transferBuffer = transferBuffer;
        this.transferManager = transferManager;
    }

    public Void call() {
        System.out.println("Start Producer");
        try (FileInputStream fis = new FileInputStream(new File(fileStart))) {
            int length;
            byte[] buffer = new byte[1048576];
            while ((length = fis.read(buffer)) > 0 && !exit) {
                try {
                    semaphoreProducer.acquire();

                    System.out.println("Producer acquired.");
                    transferBuffer.setBuffer(buffer, length);
                    System.out.println("Producer released.");

                    semaphoreConsumer.release();
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            System.out.println("Producer Thread finished");
        }
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(1));
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        transferManager.stop();
        System.out.println("Exiting Producer Now");
        return null;
    }

    public void stop() {
        this.exit = true;
        System.out.println("Exit Producer Called");
    }

    public void checkProgress() {

    }
}
