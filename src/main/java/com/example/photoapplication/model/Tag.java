package com.example.photoapplication.model;

import java.io.Serializable;

public class Tag implements Serializable {


    private static final long serialVersionUID = 2L;
    private String tagVal;

    /**
     * Constructor for creating new tag value.
     *
     * @param val value of the tag
     */
    public Tag(String val) {
        this.tagVal = val;
    }

    /**
     * Method for returning the tag value.
     *
     * @return tag value
     */
    public String getTagValue() {
        return tagVal;
    }

    /**
     * Method for setting the new tag value.
     *
     * @param tagValue new tag value
     */
    public void setTagValue(String tagValue) {
        this.tagVal = tagValue;
    }

    /**
     * Return tag value in string format.
     *
     * @return string value of the tag.
     */
    public String toString() {
        return this.tagVal;
    }
}
