package FileTransferCP;

import java.util.concurrent.Semaphore;

public class FileTransferManager implements TransferBehaviour {
    Semaphore semaphoreProducer;
    Semaphore semaphoreConsumer;
    TransferBehaviour fileProducer;
    TransferBehaviour fileConsumer;
    Thread fileProd;
    Thread fileCons;


    public FileTransferManager() {
        this.semaphoreProducer = new Semaphore(1);
        this.semaphoreConsumer = new Semaphore(0);
        this.start();
    }

    public void start() {
        TransferBuffer transferBuffer = new TransferBuffer();

        FileProducer fileProducer = new FileProducer(semaphoreProducer, semaphoreConsumer, transferBuffer, this);
        FileConsumer fileConsumer = new FileConsumer(semaphoreConsumer, semaphoreProducer, transferBuffer);

        this.fileProducer = fileProducer;
        this.fileConsumer = fileConsumer;

        this.fileProd = new Thread(fileProducer, "File Producer");
        this.fileCons = new Thread(fileConsumer, "File Consumer");

        this.fileProd.start();
        this.fileCons.start();
    }

    public void stop() {
        System.out.println("Transfer Manager Stop called!");
        this.fileProducer.stop();
        this.fileConsumer.stop();
    }

    public void checkProgress() {
        this.fileProducer.checkProgress();
        this.fileConsumer.checkProgress();
    }
}
