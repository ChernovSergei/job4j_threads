package threads;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        var file = new File("temp.xml");
        try (var input = new URL(url).openStream();
            var output = new FileOutputStream(file)) {
            var dataBuffer = new byte[512];
            int bytesRead;
            long downLoadTime;
            int actualSpeed;
            while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                var downLoadAt = System.nanoTime();
                output.write(dataBuffer, 0, dataBuffer.length);
                downLoadTime = System.nanoTime() - downLoadAt;
                actualSpeed = bytesRead * 1000000 / (int) downLoadTime;
                if (actualSpeed > speed) {
                    Thread.sleep(actualSpeed / speed);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
