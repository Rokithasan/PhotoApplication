/**
 * The TagType class represents a type of tag in the photo application.
 * It contains information such as the name of the tag type and whether it is single-valued.
 */
package com.example.photoapplication.model;

import java.io.Serializable;

public class TagType implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private boolean single;

    /**
     * Constructs a TagType with the specified name.
     *
     * @param name The name of the tag type.
     */
    public TagType(String name) {
        this.name = name;
    }

    /**
     * Constructs a TagType with the specified name and single-valued flag.
     *
     * @param name The name of the tag type.
     * @param single Indicates whether the tag type is single-valued.
     */

    public TagType(String name, boolean single) {
        this.name = name;
        this.single = single;
    }

    /**
     * Retrieves the name of the tag type.
     *
     * @return The name of the tag type.
     */

    public String getName() {
        return name;
    }

    /**
     * Sets the name of the tag type.
     *
     * @param name The new name for the tag type.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Checks if the tag type is single-valued.
     *
     * @return true if the tag type is single-valued, otherwise false.
     */
    public boolean isSingle() {
        return single;
    }

    /**
     * Sets whether the tag type is single-valued.
     *
     * @param single true if the tag type should be single-valued, otherwise false.
     */
    public void setSingle(boolean single) {
        this.single = single;
    }

    /**
     * Returns a string representation of the TagType object.
     *
     * @return A string representation of the TagType object.
     */
    @Override
    public String toString() {
        return "TagType{" +
                "name='" + name + '\'' +
                '}';
    }
}
