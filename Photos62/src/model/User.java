package model;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Joshua Hernandez
 * @author John Lavin
 */

public class User implements Serializable {


    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private ArrayList<Album> albums;

    /**
     * Constructor
     * @param username the person's username
     * @param password stores the password relating to the username
     *  makes a new album
     */

    public User(String username,String password) {
        this.username = username;
        this.password = password;
        albums = new ArrayList<Album>();
    }
    /**
     * @param test
     * @return convert the data file into useable data for the project
     * @throws IOException
     * @throws ClassNotFoundException
     */

    public void readObject(ObjectInputStream test) throws IOException, ClassNotFoundException {
        test.defaultReadObject();
    }

    /**
     * @return the user's username
     */

    public String getUsername() {
        return username;
    }

    /**
     * @return the a arraylist of albums that belong to that user
     */

    public ArrayList<Album> getAlbums() {
        return albums;
    }

    /**
     * @return the user's username in a readable string
     */

    public String toString() {
        return this.username;
    }

    /**
     * adds a new album name
     * @param albumName the album's name
     * @return the a new list of album's name with the added album name
     */

    public void addAlbum(String albumName) {
        albums.add(new Album(albumName));
    }

    /**
     * adds a new album
     * @param a the album
     * @return the new album with the added album
     */

    public void addAlbum(Album a) {
        albums.add(a);
    }

    /**
     * adds photos into the album
     * @param p,albumIndex
     * @return the album with the new photos addd
     */

    public void addPhotoToAlbum(Photo p, int albumIndex) {
        albums.get(albumIndex).addPhoto(p);
    }

    /**
     * looks through the list of album's namme to see if the album exist
     * @param albumName
     * @return true if it exist and false it doesn't
     */

    public boolean albumNameExists(String albumName) {
        for (Album a: albums) {
            if (a.getName().equalsIgnoreCase(albumName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * looks through the list of album to gets the album's index if the album exist
     * @param a
     * @return the album's index that was being searched for
     */

    public int getAlbumIndexByAlbum(Album a) {
        for (int i = 0; i < albums.size(); i++)
            if (albums.get(i).getName().equals(a.getName()))
                return i;
        return -1;
    }
    /**
     * looks through the list of album to gets the album if the album exist
     * @param name
     * @return the album that was being searched for
     */

    public Album getAlbumByName(String name) {
        for(Album a : albums)
        {
            if(a.getName().equals(name))
                return a;
        }
        return null;
    }

    /**
     * removes the album
     * @param album
     * @return the new list of albums
     */

    public void removeAlbum(Album album)
    {
        albums.remove(album);
    }

    /**
     * checks for duplicates usernames
     * @param other
     * @return true or false depend if there are duplicates usernames
     */

    public boolean equals(User other) {
        return this.username.equals(other.username);
    }
    /**
    * @return a string of the usernames and password
     */
    public String AFileString() {
        return this.username + "\n" + this.password + "\n" ;
    }

}
