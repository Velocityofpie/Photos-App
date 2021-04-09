package model;
import java.util.ArrayList;
import java.util.Calendar;
import javafx.scene.image.Image;

public class Photo implements java.io.Serializable {


    private static final long serialVersionUID = 1L;
    private ImagesSerial image;
    private ArrayList<Tag> tags;
    private String name, caption;
    private Calendar date;


    public Photo(String name, ImagesSerial image, Calendar date) {
        this.name = name;
        this.caption = "";
        this.image = image;
        this.date = date;
        this.tags = new ArrayList<Tag>();
        this.date.set(Calendar.MILLISECOND, 0);
        image = new ImagesSerial();
    }
    public ImagesSerial getImagesSerial() {
        return image;
    }


    public Image getImage() {
        return image.getImage();
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