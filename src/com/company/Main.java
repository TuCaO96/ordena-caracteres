package com.company;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

import static java.lang.System.out;

public class Main {
    private static final File input = new File("H:\\original.txt");
    private static final File output = new File("H:\\output2.txt");
    private static Map<Character,Integer> asciiCodes = new HashMap<>();

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        final long timestamp = System.currentTimeMillis();
        // replace this with a known encoding if possible
        Charset encoding = Charset.defaultCharset();

        try {
            out.println("Iniciado em: " + timestamp);
            handleFile(input, encoding);
        } catch (IOException e) {
            out.println(e.getMessage());
        }

        System.out.println("Executado em: " + System.currentTimeMillis());
    }

    private static void handleFile(File file, Charset encoding)
            throws IOException {
        try (InputStream in = new FileInputStream(file);
             // buffer for efficiency
             Reader buffer = new BufferedReader(new InputStreamReader(in, encoding))) {
            handleCharacters(buffer);
        }
    }

    private static void handleCharacters(Reader reader)
            throws IOException {
        ArrayList<Character> characters = new ArrayList<>();

        int r;

        while ((r = reader.read()) != -1) {
            char ch = (char) r;
            if (Character.isDigit(ch) || (Character.isLetter(ch) && Character.isLowerCase(ch))) {
                if(!asciiCodes.containsKey(ch)){
                    asciiCodes.put(ch, 1);
                    continue;
                }

                asciiCodes.merge(ch, 1, (v1, v2) ->
                {
                    return v1 + v2;
                });

            }
        }

        reader.close();

        if (!output.exists()) {
            output.createNewFile();
        }

        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(output)));
            SortedSet<Character> keys = new TreeSet<>(asciiCodes.keySet());
            for (Character key : keys) {
                for(int i = 0; i < asciiCodes.get(key); i++){
                    writer.print(key);
                }
            }
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
