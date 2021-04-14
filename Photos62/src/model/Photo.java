package model;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javafx.scene.image.Image;

public class Photo implements java.io.Serializable {


    private static final long serialVersionUID = 1L;
    //private Image image;
    private ArrayList<Tag> tags;
    private String name, caption;
    private Calendar date;
    private String imgsrc;


    public Photo(String name, Calendar date, String imgsrc) {
        this.name = name;
        this.caption = "";
        //this.image = image;
        this.date = date;
        this.tags = new ArrayList<Tag>();
        this.date.set(Calendar.MILLISECOND, 0);
        //image = new ImagesSerial();
        this.imgsrc = imgsrc;
    }
    /*
    public ImagesSerial getImagesSerial() {
        return image;
    }


    public Image getImage() {
        return image;
    }

     */

    public String getImgsrc() {
        return imgsrc;
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


    public Date getDate() {
        return date.getTime();
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }


    public boolean equals(Photo other) {
        return this.getImgsrc().equalsIgnoreCase(other.getImgsrc());
    }
}