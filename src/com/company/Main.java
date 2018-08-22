package com.company;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

import static java.lang.System.out;

public class Main {
    private static final File input = new File("H:\\original.txt");
    private static HashMap<Character, AsciiEntry> asciiCodes = new HashMap<>();
    private static final File output = new File("H:\\output2.txt");

    static boolean finished = false;

    static char lastReaded = 0;

    static long read;
    static long write;
    static long timestamp = System.currentTimeMillis();

    public static HashMap<Character, AsciiEntry> getAsciiCodes(){
        return asciiCodes;
    }

    public static void main(String[] args) {
        final long timestamp = System.currentTimeMillis();

        // replace this with a known encoding if possible
        Charset encoding = Charset.defaultCharset();

        try {
            out.println("Started at " + new Date().toString());
            ReadFile readFile = new ReadFile();
            readFile.handleFile(input, encoding);
        } catch (IOException e) {
            out.println(e.getMessage());
        }

        System.out.println("Executed in: " + ((System.currentTimeMillis() - timestamp) / 10) + " seconds");
    }
}
