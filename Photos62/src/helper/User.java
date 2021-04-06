package helper;
import java.io.Serializable;
import java.util.ArrayList;
public class User {


    //private static final long serialVersionUID = 8177923271139908648L;
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
    public String AdmintoString() {
        return this.Songname + ":\n" + this.Artist;
    }
    public String fileString() {
        return this.username + "\n" + this.password + "\n" ;
    }
}
