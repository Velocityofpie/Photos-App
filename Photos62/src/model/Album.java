package model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
        Calendar date = photos.get(0).getDate();
        for (int i = 1; i < photos.size(); i++) {
            Photo p = photos.get(i);
            if (p.getDate().before(date)) {
                date = p.getDate();
            }
        }
        return date.getTime();
    }

    public Date getLatestDate() {
        return NewestPhoto.getDate().getTime();
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
        String result = "NAME: " + name + "\nPHOTO COUNT: " + photos.size() + "\nDATES: " + getEarliestDate() + " - " + getLatestDate();

        return result;
    }
}
