package com.company;

import java.util.Map;

public class AsciiEntry implements Map.Entry<Integer, Boolean>{

    int key;
    boolean value;

    public AsciiEntry(int key, boolean value){
        this.key = key;
        this.value = value;
    }

    public Integer setKey(Integer key) {
        this.key = key;

        return key;
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public Integer getKey() {
        return key;
    }

    @Override
    public Boolean setValue(Boolean value) {
        this.value = value;

        return value;
    }
}