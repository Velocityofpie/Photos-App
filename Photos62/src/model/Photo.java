package model;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javafx.scene.image.Image;

/**
 * @author Joshua Hernandez
 * @author John Lavin
 */

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

    public String toString() {
        return name;
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public boolean tagExists(Tag tag) {
        for (Tag t : tags) {
            if (t.equals(tag)) {
                return true;
            }
        }
        return false;
    }

    public int getTagIndex(Tag tag) {
        for (int i = 0; i < tags.size(); i++) {
            if (tag.equals(tags.get(i))) {
                return i;
            }
        }
        return -1;
    }

    public void removeTag(int index) {
        tags.remove(index);
        return;
    }

    public LocalDate getLocalDate() {
        Date date = getDate();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String inactiveDate = null;
        inactiveDate = format1.format(date);

        LocalDate result = LocalDate.parse(inactiveDate);
        return result;
    }

    public boolean equals(Photo other) {
        return this.getImgsrc().equalsIgnoreCase(other.getImgsrc());
    }
}