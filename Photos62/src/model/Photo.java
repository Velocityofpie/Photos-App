package model;
import java.util.ArrayList;
import java.util.Calendar;
import javafx.scene.image.Image;

public class Photo implements java.io.Serializable {


    private static final long serialVersionUID = 1L;
    private ArrayList<Tag> tags;
    private String name, caption;
    private Serializable image;
    private Calendar date;


    public Photo(String name, Serializable image, Calendar date) {
        this.name = name;
        this.caption = "";
        this.image = image;
        this.date = date;
        this.tags = new ArrayList<Tag>();
        this.date.set(Calendar.MILLISECOND, 0);
    }


    public Photo(String name, Image image, Calendar date) {
        this.name = name;
        this.caption = "";

        this.date = date;
        this.tags = new ArrayList<Tag>();
        this.date.set(Calendar.MILLISECOND, 0);
    }


    public String getName() {
        return name;
    }


    public String getCaption() {
        return caption;
    }





    public ArrayList<Tag> getTags() {
        return tags;
    }


    public Calendar getDate() {
        return date;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }


    public boolean equals(Photo other) {
        return this.name.equals(other.name);
    }
}