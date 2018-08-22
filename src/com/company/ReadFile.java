package com.company;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

public class ReadFile{
    private final File input = new File("H:\\original.txt");
    private HashMap<Character, AsciiEntry> asciiCodes = new HashMap<>();
    private final File output = new File("H:\\output2.txt");
    static boolean finished = false;

    public static char lastReaded = 0;

    static long read;
    static long write;
    static long timestamp = System.currentTimeMillis();

    public void handleFile(File file, Charset encoding)
            throws IOException {
        try (InputStream in = new FileInputStream(file);
             // buffer for efficiency
             Reader buffer = new BufferedReader(new InputStreamReader(in, encoding))) {
            handleCharacters(buffer);
        }
    }

    private void handleCharacters(Reader reader)
            throws IOException {
        int r;

        while ((r = reader.read()) != -1) {
            char ch = (char) r;
            //get only digits and letters that are lowercase
            if (Character.isDigit(ch) || (Character.isLetter(ch) && Character.isLowerCase(ch) && (int)ch < 123)) {
                //add character if not in map
                if(!asciiCodes.containsKey(ch)){
                    asciiCodes.put(ch, new AsciiEntry(1, false));
                    continue;
                }

                //count characters
                asciiCodes.merge(ch, new AsciiEntry(1, false), (v1, v2) ->
                {
                    v1.setKey(v1.getKey() + v2.getKey());
                    return v1;
                });

            }
        }

        reader.close();
        System.out.println("Read in: " + ((System.currentTimeMillis() - timestamp) / 10) + " seconds");
        //create file if not exists

        try {
            if(!output.exists()){
                output.createNewFile();
            }
            //print all the characters for each one included. already ordered via SortedSet
            BufferedWriter writer = new BufferedWriter(new FileWriter(output));
            SortedSet<Character> keys = new TreeSet<>(asciiCodes.keySet());
            MyThread initialThread = new MyThread(asciiCodes, keys.first(), writer, lastReaded);
            initialThread.start();
            initialThread.sleep(1000);
            keys.remove(keys.first());

            for (Character key : keys) {
                //if character hasnt been read by a thread and last one was
                MyThread thread = new MyThread(asciiCodes, key, writer, lastReaded);
                thread.start();
                thread.sleep(1000);
            }

            //flush write
            writer.flush();
            //close
            writer.close();
            finished = true;
            System.out.println("Writed in: " + ((System.currentTimeMillis() - timestamp) / 10) + " seconds");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}