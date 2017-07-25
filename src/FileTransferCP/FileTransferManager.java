package FileTransferCP;

import java.util.concurrent.Semaphore;

public class FileTransferManager implements TransferBehaviour {
    Semaphore semaphoreProducer;
    Semaphore semaphoreConsumer;
    TransferBehaviour fileProducer;
    TransferBehaviour fileConsumer;


    public FileTransferManager() {
        this.semaphoreProducer = new Semaphore(1);
        this.semaphoreConsumer = new Semaphore(0);
        this.start();
    }

    public void start() {
        TransferBuffer transferBuffer = new TransferBuffer(4096, 0);

        FileProducer fileProducer = new FileProducer(semaphoreProducer, semaphoreConsumer, transferBuffer);
        FileConsumer fileConsumer = new FileConsumer(semaphoreConsumer, semaphoreProducer, transferBuffer);

        this.fileProducer = fileProducer;
        this.fileConsumer = fileConsumer;

        new Thread(fileProducer).start();
        new Thread(fileConsumer).start();
    }

    public void stop() {
        this.fileProducer.stop();
        this.fileConsumer.stop();
    }

    public void checkProgress() {
        this.fileProducer.checkProgress();
        this.fileConsumer.checkProgress();
    }
}
