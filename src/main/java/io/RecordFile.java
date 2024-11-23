package io;

import java.io.*;

public class RecordFile {
    final private File file;
    final private String content;

    public RecordFile(File file, String content) {
        this.file = file;
        this.content = content;
    }

    public synchronized void saveContent() throws IOException {
        OutputStream o = new BufferedOutputStream(new FileOutputStream(file));
        for (int i = 0; i < content.length(); i++) {
            o.write(content.charAt(i));
        }
    }
}