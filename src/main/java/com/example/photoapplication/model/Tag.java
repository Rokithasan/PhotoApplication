package com.example.photoapplication.model;

import java.io.Serializable;

public class Tag implements Serializable {

    private static final long serialVersionUID = 2L;
    private String tagVal;

    TagType type ;

    public Tag(String tagVal) {
        this.tagVal = tagVal;
    }

    public String getTagVal() {
        return tagVal;
    }

    public void setTagVal(String tagVal) {
        this.tagVal = tagVal;
    }
    public String toString() {
        return type.toString() + " : " + this.tagVal;
    }
}
