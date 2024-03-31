package com.example.photoapplication.model;

import java.io.Serializable;

public class TagType implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private boolean single;

    public TagType(String name) {
        this.name = name;
    }

    public TagType(String name, boolean single) {
        this.name = name;
        this.single = single;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSingle() {
        return single;
    }

    public void setSingle(boolean single) {
        this.single = single;
    }

    @Override
    public String toString() {
        return "TagType{" +
                "name='" + name + '\'' +
                '}';
    }
}
