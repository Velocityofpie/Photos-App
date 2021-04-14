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


    public User(String username,String password) {
        this.username = username;
        this.password = password;
        albums = new ArrayList<Album>();
    }

    public void readObject(ObjectInputStream test) throws IOException, ClassNotFoundException {
        test.defaultReadObject();
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<Album> getAlbums() {
        return albums;
    }

    public String toString() {
        return this.username;
    }


    public void addAlbum(String albumName) {
        albums.add(new Album(albumName));
    }
    public void addAlbum(Album a) {
        albums.add(a);
    }
    public void addPhotoToAlbum(Photo p, int albumIndex) {
        albums.get(albumIndex).addPhoto(p);
    }
    public boolean albumNameExists(String albumName) {
        for (Album a: albums) {
            if (a.getName().equalsIgnoreCase(albumName)) {
                return true;
            }
        }
        return false;
    }

    public int getAlbumIndexByAlbum(Album a) {
        for (int i = 0; i < albums.size(); i++)
            if (albums.get(i).getName().equals(a.getName()))
                return i;
        return -1;
    }


    public Album getAlbumByName(String name) {
        for(Album a : albums)
        {
            if(a.getName().equals(name))
                return a;
        }
        return null;
    }


    public void removeAlbum(Album album)
    {
        albums.remove(album);
    }

        public boolean equals(User other) {
        return this.username.equals(other.username);
    }

    public String AFileString() {
        return this.username + "\n" + this.password + "\n" ;
    }

}
