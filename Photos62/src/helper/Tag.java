package helper;
import java.io.Serializable;

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
}
