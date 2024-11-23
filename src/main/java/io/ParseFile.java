package io;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {
    final private File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public synchronized String getContent(Predicate<Character> filter, Character limit) throws IOException {
        InputStream input = new BufferedInputStream(new FileInputStream(file));
        String output = "";
        int data;
        while ((data = input.read()) > 0) {
            if (filter.test(limit)) {
                output += (char) data;
            }
        }
        return output;
    }
}