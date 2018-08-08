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
        final long timestamp = System.currentTimeMillis();
        // replace this with a known encoding if possible
        Charset encoding = Charset.defaultCharset();

        try {
            out.println("Started");
            handleFile(input, encoding);
        } catch (IOException e) {
            out.println(e.getMessage());
        }

        System.out.println("Executed in: " + ((System.currentTimeMillis() - timestamp) / 1000));
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
            //get only digits and letters that are lowercase
            if (Character.isDigit(ch) || (Character.isLetter(ch) && Character.isLowerCase(ch))) {
                //add character if not in map
                if(!asciiCodes.containsKey(ch)){
                    asciiCodes.put(ch, 1);
                    continue;
                }

                //count characters
                asciiCodes.merge(ch, 1, (v1, v2) ->
                {
                    return v1 + v2;
                });

            }
        }

        reader.close();

        if (!output.exists()) {
            //create file if not exists
            output.createNewFile();
        }

        try {
            //print all the characters for each one included. already ordered via SortedSet
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(output)));
            SortedSet<Character> keys = new TreeSet<>(asciiCodes.keySet());
            for (Character key : keys) {
                for(int i = 0; i < asciiCodes.get(key); i++){
                    writer.print(key);
                }
            }
            //flush write
            writer.flush();
            //close
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
