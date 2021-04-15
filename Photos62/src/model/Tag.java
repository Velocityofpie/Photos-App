package model;
import java.io.Serializable;

/**
 * @author Joshua Hernandez
 * @author John Lavin
 */

public class Tag implements Serializable {

    private String tagName;

    private String tagValue;

    /**
     * Constructor
     * @param tagName the name of the tage's name
     * @param tagValue the the tags value
     */

    public Tag(String tagName, String tagValue) {

        this.tagName = tagName;
        this.tagValue = tagValue;
    }

    /**
     * @return tag's name + its value
     */

    public String getTag() {

        return "(" + this.tagName + ", " + this.tagValue + ")";
    }


    /**
     * @return tag's name
     */

    public String getTagName() {

        return this.tagName;
    }

    /**
     * @return tag's value
     *
     */

    public String getTagValue() {

        return this.tagValue;
    }
    /**
     * @param other is the other tag being compared to along with its value
     * @return true or false if the tags are equal
     */

    public boolean equals(Tag other) {
        if ((other.getTagName().equals(tagName)) && (other.getTagValue().equals(tagValue))) {
            return true;
        }
        return false;
    }

    /**
     * @return tag's name and value and puts into a string to be read
     *
     */
    public String toString() {
        return getTag();
    }
}
