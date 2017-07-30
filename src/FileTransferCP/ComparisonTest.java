package FileTransferCP;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ComparisonTest {
    private static volatile boolean exit = false;

    public static void main(String[] args) {
        Date start = new Date();

        String fileStart = "/Users/jds/Learning/Java/ThreadSamples/TestTransfer/StartZip.zip";
        String fileEnd = "/Users/jds/Learning/Java/ThreadSamples/TestTransfer/EndZip.zip";

        try(FileInputStream fis = new FileInputStream(new File(fileStart));
            FileOutputStream fos = new FileOutputStream(new File(fileEnd))) {

            int length;
            byte[] buffer = new byte[4096];

            while((length = fis.read(buffer)) > 0 && !exit) {
                fos.write(buffer, 0, length);
            }

        } catch (IOException io) {
            io.printStackTrace();
        }

        Date end = new Date();
        System.out.println(TimeUnit.MILLISECONDS.toSeconds(start.getTime() - end.getTime()));
    }
}
