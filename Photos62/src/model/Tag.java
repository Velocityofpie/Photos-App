package model;
import java.io.Serializable;

/**
 * @author Joshua Hernandez
 * @author John Lavin
 */

public class Tag implements Serializable {
    private String tagName;

    private String tagValue;


    public Tag(String tagName, String tagValue) {

        this.tagName = tagName;
        this.tagValue = tagValue;
    }

    public String getTag() {

        return "(" + this.tagName + ", " + this.tagValue + ")";
    }


    public String getTagName() {

        return this.tagName;
    }

    public String getTagValue() {

        return this.tagValue;
    }

    public void setTagName(String tagName) {

        this.tagName = tagName;
    }


    public void setTagValue(String tagValue) {

        this.tagValue = tagValue;
    }

    public boolean equals(Tag other) {
        if ((other.getTagName().equals(tagName)) && (other.getTagValue().equals(tagValue))) {
            return true;
        }
        return false;
    }

    public String toString() {
        return getTag();
    }
}
