package controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import model.User;


public class AdminController {

    @FXML
    Button btnAdd, btnEdit, btnDelete;
    @FXML
    TextField txtUsername;

    @FXML
    ListView<User> lvUsers; //list of users
    private ObservableList<User> obsList; //observable list that stores strings for everything in listview
    private ArrayList<User> alUser;

    public void start(ArrayList<User> users) {

        alUser = users;
        obsList = FXCollections.observableArrayList(users);
        lvUsers.setItems(obsList);

        // set listener for the items
        lvUsers.getSelectionModel().selectedIndexProperty().addListener((obsList, oldVal, newVal) -> showItem());
    }

    //puts the values of the selected item into the textfields
    private void showItem() {

        //get the song from the obsList
        int index = lvUsers.getSelectionModel().getSelectedIndex();
        User s = obsList.get(index);

        //put necessary values in the text field
        txtUsername.setText(s.getUsername());

    }

    public void convert (ActionEvent e) throws IOException {

        Button b = (Button) e.getSource();
        if (b == btnAdd) {
            String addUsername = txtUsername.getText();
            User addThisUser = new User(addUsername, "");
            add(addThisUser);
        } else if (b == btnDelete) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Need Confirmation");
            alert.setContentText("Are you ok with this?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                delete();
            } else {
                // ... user chose CANCEL or closed the dialog
            }
        } else if (b == btnEdit) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Need Confirmation");
            alert.setContentText("Are you ok with this?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                edit();
            } else {
                // ... user chose CANCEL or closed the dialog
            }
        }
    }

    public void storeUsers (ArrayList<User> u, int i) throws IOException {
        FileOutputStream file = new FileOutputStream("Photos62/data/data.txt");
        ObjectOutputStream output = new ObjectOutputStream(file);
        if (i == 0) {
            User a = new User("admin", "admin");
            output.writeObject(a);
            output.writeObject(new User("stock", "stock"));
        } else {
            for (User curr: u) {
                output.writeObject(curr);
            }
        }



        output.close();
        file.close();
    }



    private void add(User u) throws IOException {

        String name = txtUsername.getText();
        int n = obsList.size();
        for (int i = 0; i < n; i++) {
            User curr = obsList.get(i);
            String currUsername = curr.getUsername();
            if (currUsername.equals(name)) {
                duplicateUserAlert();
                return;
            }

        }

        obsList.add(u);
        alUser.add(u);
        lvUsers.setItems(obsList);

        //store data
        storeUsers(alUser, 1);
    }

    private void edit() throws IOException {
        int index = lvUsers.getSelectionModel().getSelectedIndex();
        User selectedUser = lvUsers.getSelectionModel().getSelectedItem();
        User editedUser = new User(txtUsername.getText(), "");
        //findspot
        //if it returns -1 then that user already exists

        delete();
        add(editedUser);

    }

    private void delete() throws IOException {
        int index = lvUsers.getSelectionModel().getSelectedIndex();
        obsList.remove(index);
        alUser.remove(index);

        int n = obsList.size();

        //if we deleted the last item, make sure the new last item is selected
        //if not, make sure the next item is selected, which is now at the removed index anyways
        if (index == n) {
            lvUsers.getSelectionModel().select(index-1);

        } else if (obsList.isEmpty()) {
            //don't select anything since the list is empty. wait for a new add to select
        } else {
            lvUsers.getSelectionModel().select(index);
        }
        //store data
        storeUsers(alUser, 1);
    }

    private void duplicateUserAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(
                "That user is already in the library.");

        String content = "Please change the name of the user";

        alert.setContentText(content);
        alert.showAndWait();
    }


    //    public void convert(ActionEvent e) {
//
//        Button b = (Button)e.getSource();
//        if (b == btnAdd) {
//            String songname = tfName.getText();
//            String artist = tfArtist.getText();
//            String album = tfAlbum.getText();
//            String year = tfYear.getText();
//
//
//            Songs addThisSong = new Songs(songname, artist, album, year);
//
//            Alert alert = new Alert(AlertType.CONFIRMATION);
//            alert.setTitle("Confirmation Dialog");
//            alert.setHeaderText("Need Confirmation");
//            alert.setContentText("Are you ok with this?");
//
//            Optional<ButtonType> result = alert.showAndWait();
//            if (result.get() == ButtonType.OK){
//                add(addThisSong);
//            } else {
//                // ... user chose CANCEL or closed the dialog
//            }
//
//
//        } else if (b == btnEdit){
//            Alert alert = new Alert(AlertType.CONFIRMATION);
//            alert.setTitle("Confirmation Dialog");
//            alert.setHeaderText("Need Confirmation");
//            alert.setContentText("Are you ok with this?");
//
//            Optional<ButtonType> result = alert.showAndWait();
//            if (result.get() == ButtonType.OK){
//                edit();
//            } else {
//                // ... user chose CANCEL or closed the dialog
//            }
//
//        } else if (b == btnDelete){
//            Alert alert = new Alert(AlertType.CONFIRMATION);
//            alert.setTitle("Confirmation Dialog");
//            alert.setHeaderText("Need Confirmation");
//            alert.setContentText("Are you ok with this?");
//
//            Optional<ButtonType> result = alert.showAndWait();
//            if (result.get() == ButtonType.OK){
//                delete();
//            } else {
//                // ... user chose CANCEL or closed the dialog
//            }
//
//        }
//    }



//    public ArrayList<Songs> newSongList;
//
//    public ArrayList<Songs> Filesloder(String file) throws IOException {
//        File songfile = new File(file);
//        newSongList = new ArrayList<Songs>(1);
//        if(!songfile.exists()) {
//            songfile.createNewFile();
//        }
//
//        FileReader reader = new FileReader(songfile);
//        BufferedReader BReader = new BufferedReader(reader);
//
//        String line = "";
//        while(line != null) {
//            Songs temp = new Songs(line, line, line, line);
//            line = BReader.readLine();
//            if(line == null) {
//                break;
//            }
//            temp.Songname = line;
//            line = BReader.readLine();
//            temp.Artist = line;
//            line = BReader.readLine();
//            temp.Album = line;
//            line = BReader.readLine();
//            temp.Year = line;
//            newSongList.add(temp);
//
//        }
//
//        BReader.close();
//        return newSongList;
//    }
//
//    public void Songsloader() throws IOException {
//
//        Filesloder("Songlist.txt");
//
//        obsList = FXCollections.observableArrayList(newSongList);
//        if(obsList.size() == 0) {
//            tfName.setText("");
//            tfArtist.setText("");
//            tfAlbum.setText("");
//            tfYear.setText("");
//        }
//
//        lvSongs.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Songs>() {
//
//            @Override
//            public void changed(ObservableValue<? extends Songs> arg0, Songs arg1, Songs arg2) {
//                try {
//                    tfName.setText(arg2.getSongname());
//                    tfArtist.setText(arg2.getArtist());
//                    tfAlbum.setText(arg2.getAlbum());
//                    tfYear.setText(arg2.getYear());
//                }
//                catch(NullPointerException e) {
//
//                }
//
//            }
//        });
//
//        lvSongs.setItems(obsList);
//        lvSongs.getSelectionModel().select(0);
//    }
//
//
//
//

//
//    //puts the values of the selected item into the textfields
//    private void showItem(Stage primaryStage) {
//
//        //get the song from the obsList
//        int index = lvSongs.getSelectionModel().getSelectedIndex();
//        Songs s = obsList.get(index);
//
//        //put necessary values in the text field
//        tfName.setText(s.getSongname());
//        tfArtist.setText(s.getArtist());
//        tfAlbum.setText(s.getAlbum());
//        tfYear.setText(s.getYear());
//
//    }
//

//
//
//
//    private void duplicateSongAlert() {
//        Alert alert = new Alert(AlertType.INFORMATION);
//        alert.setTitle("Error");
//        alert.setHeaderText(
//                "That song is already in the library.");
//
//        String content = "Please change the name of the song and/or the artist";
//
//        alert.setContentText(content);
//        alert.showAndWait();
//    }
//
//    private void noNameOrArtistAlert() {
//        Alert alert = new Alert(AlertType.INFORMATION);
//        alert.setTitle("Error");
//        alert.setHeaderText(
//                "No name and/or artist detected.");
//
//        String content = "Please input the name of the song and/or the artist";
//
//        alert.setContentText(content);
//        alert.showAndWait();
//    }
//
//    private void store() throws IOException {
//
//        File songs = new File("Songlist.txt");
//        BufferedWriter out = new BufferedWriter(new FileWriter("Songlist.txt"));
//
//        for (int i = 0; i < obsList.size(); i++) {
//
//            Songs s = obsList.get(i);
//            out.write(s.fileString());
//
//        }
//        out.close();
//    }
//
//    private void add(Songs s) {
//
//        String currName = s.getSongname(); String currArtist = s.getArtist();
//
//        // if this returns a negative int pop up an error message
//        int index = findSpot(s);
//        if (index < 0) {
//            duplicateSongAlert();
//            return;
//        } else if ( (currName.compareTo("") == 0) || (currArtist.compareTo("") == 0)){
//            noNameOrArtistAlert();
//            return;
//        }
//
//        obsList.add(index, s);
//        lvSongs.setItems(obsList);
//        //select the item just added
//        lvSongs.getSelectionModel().select(index);
//        //update();
//        try {
//            store();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//    }
//
//    private void edit() {
//
//        Songs edited = new Songs (tfName.getText(), tfArtist.getText(), tfAlbum.getText(), tfYear.getText());
//        Songs currSong = lvSongs.getSelectionModel().getSelectedItem();
//        String currAlbum = currSong.getAlbum(); String currYear = currSong.getYear();
//
//        int i = findSpot(edited);
//
//        if (i < 0) { // there exists a song with the same name and artist
//            // check if they are changing the album or year
//            if ((currAlbum.compareTo(edited.getAlbum()) == 0  )  && (currYear.compareTo(edited.getYear()) == 0)   ) {
//                //return an error message
//
//                duplicateSongAlert();
//
//            } else {
//                delete();
//                add(edited);
//            }
//
//        } else if ( (edited.getSongname().compareTo("") == 0) || (edited.getArtist().compareTo("") == 0)) {
//            noNameOrArtistAlert();
//            return;
//        } else {
//            delete();
//            add(edited);
//        }
//        //update();
//        try {
//            store();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//    }
//
//    private void delete() {
//
//        //get the index of the selected song
//        int index = lvSongs.getSelectionModel().getSelectedIndex();
//        obsList.remove(index);
//
//        int n = obsList.size();
//
//        //if we deleted the last item, make sure the new last item is selected
//        //if not, make sure the next item is selected, which is now at the removed index anyways
//        if (index == n) {
//            lvSongs.getSelectionModel().select(index-1);
//
//        } else if (obsList.isEmpty()) {
//            //don't select anything since the list is empty. wait for a new add to select
//        } else {
//            lvSongs.getSelectionModel().select(index);
//        }
//        //update();
//        try {
//            store();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//
//    private int findSpot(Songs s) {
//        if (obsList.isEmpty()) {
//            return 0;
//        }
//        String name = s.getSongname();
//        String artist = s.getArtist();
//
//        int n = obsList.size();
//        for (int i = 0; i < n; i++) {
//
//            Songs t = obsList.get(i);
//            String name2 = t.getSongname(); String artist2 = t.getArtist();
//
//            if (name.compareToIgnoreCase(name2) < 0) {
//                return i;
//            } else if (name.compareToIgnoreCase(name2) > 0) {
//                continue;
//            } else {
//                // case where song name is the same
//                if (artist.compareToIgnoreCase(artist2) < 0) {
//                    return i;
//                } else if (artist.compareToIgnoreCase(artist2) > 0) {
//                    continue;
//                } else {
//                    return -1;
//                }
//
//            }
//
//        }
//        return n;
//    }
}
