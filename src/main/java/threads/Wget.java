package threads;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String loadFileName;

    public Wget(String url, int speed, String loadFileName) {
        this.url = url;
        this.speed = speed;
        this.loadFileName = loadFileName;
    }

    @Override
    public void run() {
        var file = new File(loadFileName);
        try (var input = new URL(url).openStream();
            var output = new FileOutputStream(file)) {
            var dataBuffer = new byte[512];
            int bytesRead;
            int totalBytes = 0;
            long totalTime = 0;
            int oneSecond = 1000000000;
            var currentTimeMills = System.nanoTime();
            while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                output.write(dataBuffer, 0, dataBuffer.length);
                totalTime = totalTime + System.nanoTime() - currentTimeMills;
                totalBytes = totalBytes + bytesRead;
            }
            if (totalBytes >= speed && totalTime <= oneSecond) {
                Thread.sleep((oneSecond - totalTime) / 1000000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length == 0) {
            throw new IllegalArgumentException("Arguments are not assigned to the main method");
        }
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String loadFileName = args[2];
        Thread wget = new Thread(new Wget(url, speed, loadFileName));
        wget.start();
        wget.join();
    }
}
