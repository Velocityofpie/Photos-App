package model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Joshua Hernandez
 * @author John Lavin
 */

public class Album implements Serializable{
   private static final long serialVersionUID = 1L;
    private String name;
    private ArrayList<Photo> photos;
    private Photo NewestPhoto;

    public Album(String name) {
        this.name = name;
        photos = new ArrayList<Photo>();
    }

    public Date getEarliestDate() {
        Date date = photos.get(0).getDate();
        for (int i = 1; i < photos.size(); i++) {
            Photo p = photos.get(i);
            if (p.getDate().before(date)) {
                date = p.getDate();
            }
        }
        return date;
    }

    public Date getLatestDate() {
        return NewestPhoto.getDate();
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void addPhoto(Photo photo) {
        photos.add(photo);
        NewestPhoto = photo;
    }

    public ArrayList<Photo> getPhotos() {
        return this.photos;
    }

    public int getPhotoCount() {
        return this.photos.size();
    }

    public boolean equals(Album other) {
        return name.equals(other.name);
    }

    public String toString() {
        String result = "Album Name : " + name + "\nNumber of photos: " + photos.size() + "\nDATES: " + getEarliestDate() + " - " + getLatestDate();
        return result;
    }

    public Photo getNewestPhoto() {
        Photo res = NewestPhoto;
        for (Photo curr: photos) {
            if (curr.getDate().after(NewestPhoto.getDate())) {
                res = curr;
            }
        }
        return res;
    }

    public int getPhotoIndexByPhoto(Photo a) {
        for (int i = 0; i < photos.size(); i++)
            if (photos.get(i).getImgsrc().equals(a.getImgsrc()))
                return i;
        return -1;
    }
}
