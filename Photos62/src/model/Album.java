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

    /**
     * Constructor for the album
     * @param name
     */
    public Album(String name) {
        this.name = name;
        photos = new ArrayList<Photo>();
    }

    /**
     *
     * @return the Earlies photo the album
     */
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

    /**
     *
     * @return the newest photo
     */
    public Date getLatestDate() {
        return NewestPhoto.getDate();
    }

    /**
     * Gets the name of this album
     * @return the name of this album
     */

    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of this album
     * @param name  name of this album
     */

    public void setName(String name) {
        this.name = name;
    }
    /**
     * adds a photo the front
     *@param photo
     */
    public void addPhoto(Photo photo) {
        photos.add(photo);
        NewestPhoto = photo;
    }
    /**
     * Gets the photos in this album
     * @return an arraylist of photos
     */
    public ArrayList<Photo> getPhotos() {
        return this.photos;
    }

    /**
     *
     * @return the number of photos in this album
     */
    public int getPhotoCount() {
        return this.photos.size();
    }

    /**
     *  @return a string with the albums name, number of photos, Dates from the earlist the lastest
	 */

    public String toString() {
        String result = "Album Name : " + name + "\nNumber of photos: " + photos.size() + "\nDATES: " + getEarliestDate() + " - " + getLatestDate();
        return result;
    }

    /**
     *  @return the newest photo
     */

    public Photo getNewestPhoto() {
        Photo res = NewestPhoto;
        for (Photo curr: photos) {
            if (curr.getDate().after(NewestPhoto.getDate())) {
                res = curr;
            }
        }
        return res;
    }

    /**
     * @param a which it the given photo
     *  @return the photo requested
     */
    public int getPhotoIndexByPhoto(Photo a) {
        for (int i = 0; i < photos.size(); i++)
            if (photos.get(i).getImgsrc().equals(a.getImgsrc()))
                return i;
        return -1;
    }
}
