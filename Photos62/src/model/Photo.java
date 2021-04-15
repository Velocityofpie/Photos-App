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

    /**
     * Constructor
     * @param name the name of the photo
     * @param date the last modified date
     */

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

    /**
     * @return the Caption of this photo
     */

    public String getCaption() {
        return caption;
    }

    /**
     * @return the tag of this photo
     */

    public ArrayList<Tag> getTags() {
        return tags;
    }

    /**
     * @return the directory of this photo
     */

    public String getImgsrc() {
        return imgsrc;
    }

    /**
     * @return the name of this photo
     */

    public String getName() {
        return name;
    }

    /**
     * @return the date/time of this photo
     */

    public Date getDate() {
        return date.getTime();
    }

    /**
     * @return the changes the caption or sets it of this photo
     */

    public void setCaption(String caption) {
        this.caption = caption;
    }

    /**
     * @return the name of this photo
     */

    public String toString() {
        return name;
    }

    /**
     *
     * @return the tag of this photo
     */

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    /**
     *@param tag takes in tag and checks if it exits
     * @return if the tag of this photo exist
     */

    public boolean tagExists(Tag tag) {
        for (Tag t : tags) {
            if (t.equals(tag)) {
                return true;
            }
        }
        return false;
    }

    /**
     *@param tag takes in tag and checks if it exits
     * @return the index of the tag
     */

    public int getTagIndex(Tag tag) {
        for (int i = 0; i < tags.size(); i++) {
            if (tag.equals(tags.get(i))) {
                return i;
            }
        }
        return -1;
    }

    /**
     *@param index of the tag
     * removes tag
     */

    public void removeTag(int index) {
        tags.remove(index);
        return;
    }

    /**
     *
     * @return the date in the formate of yyyy-MM-dd
     */

    public LocalDate getLocalDate() {
        Date date = getDate();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String inactiveDate = null;
        inactiveDate = format1.format(date);

        LocalDate result = LocalDate.parse(inactiveDate);
        return result;
    }

    /**
     * Compares this photo to another
     *
     * @return true or false depending if it does equals
     * @param other the photo to be compared to
     */

    public boolean equals(Photo other) {
        return this.getImgsrc().equalsIgnoreCase(other.getImgsrc());
    }
}