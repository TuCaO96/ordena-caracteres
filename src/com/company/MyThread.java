package com.company;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

class MyThread extends Thread{
    private HashMap<Character,AsciiEntry> asciiCodes;
    boolean finished = false;
    char key;
    BufferedWriter writer;
    char lastReaded;

    public MyThread(HashMap<Character, AsciiEntry> codes, char key, BufferedWriter writer, char lastReaded){
        asciiCodes = codes;
        this.key = key;
        this.writer = writer;
        this.lastReaded = lastReaded;
    }

    public boolean isFinished() {
        return finished;
    }

    @Override
    public void run() {
        try {
            while (!finished){
                if(!asciiCodes.get(key).getValue() && ((int)key - 1 == (int)lastReaded
                        || lastReaded == 0)){
                    //then character is marked as readed
                    for(int i = 0; i < asciiCodes.get(key).getKey(); i++){
                        writer.write(key);
                    }

                    asciiCodes.get(key).setValue(true);
                    ReadFile.lastReaded = key;
                }
                else if(key < lastReaded){
                    this.finished = true;
                }
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}