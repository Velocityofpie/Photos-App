package model;
import java.io.Serializable;
import java.util.ArrayList;
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


    public String getUsername() {
        return username;
    }


    public ArrayList<Album> getAlbums() {
        return albums;
    }


    public String toString() {
        return this.username;
    }


    public boolean equals(User other) {
        return this.username.equals(other.username);
    }

    public String FileString() {
        return "UserName:"+this.username + "\n" + "Password:" +this.password + "\n" ;
    }
}
