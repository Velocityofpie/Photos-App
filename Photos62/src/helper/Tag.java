package helper;
import java.io.Serializable;

public class Tag implements Serializable {
    private static final long serialVersionUID = 1L;


    private String name, value;


    public Tag(String name, String value) {
        this.name = name;
        this.value = value;
    }


    public String getName() {
        return name;
    }


    public String getValue() {
        return value;
    }


    public boolean equals(Tag other) {
        return name.equals(other.name) && value.equals(other.value);
    }

    public String toString() {
        return name + " - " + value;
    }
}
