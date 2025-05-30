package io.piped;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.channels.Pipe;

public class PipedUsage {

    public static void main(String[] args) throws IOException {
        final PipedInputStream input = new PipedInputStream();
        final PipedOutputStream output = new PipedOutputStream();

        Thread firstThread = new Thread(() -> {
           try {
               output.write("Job4j".getBytes());
               output.close();
           } catch (Exception e) {
               e.printStackTrace();
           }
        });

        Thread secondThread = new Thread (() -> {
           try {
               int character;
               while ((character = input.read()) != -1) {
                   System.out.print((char) character);
               }
               input.close();
           } catch (Exception e) {
               e.printStackTrace();
           }
        });

        input.connect(output);
        firstThread.start();
        secondThread.start();
    }
}
