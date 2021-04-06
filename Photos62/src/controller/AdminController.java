package controller;

import java.io.*;
import java.util.Calendar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import helper.User;
import helper.Album;


public class AdminController {
    @FXML
    Button btnAdd, btnEdit, btnDelete, btnCancel, btnSave;
    @FXML
    Text tName, tArtist, tAlbum, tYear;
    @FXML
    Button mEdit, mAdd, mDelete, mSave, mcancel;
    @FXML
    TextField tfName, tfArtist, tfAlbum, tfYear;

    @FXML ListView<Songs> lvSongs; //list of strings ("song name" by artist)
    private ObservableList<Songs> obsList; //observable list that stores strings for everthing in listview
    public ArrayList<Songs> newSongList;

    public ArrayList<Songs> Filesloder(String file) throws IOException {
        File songfile = new File(file);
        newSongList = new ArrayList<Songs>(1);
        if(!songfile.exists()) {
            songfile.createNewFile();
        }

        FileReader reader = new FileReader(songfile);
        BufferedReader BReader = new BufferedReader(reader);

        String line = "";
        while(line != null) {
            Songs temp = new Songs(line, line, line, line);
            line = BReader.readLine();
            if(line == null) {
                break;
            }
            temp.Songname = line;
            line = BReader.readLine();
            temp.Artist = line;
            line = BReader.readLine();
            temp.Album = line;
            line = BReader.readLine();
            temp.Year = line;
            newSongList.add(temp);

        }

        BReader.close();
        return newSongList;
    }

    public void Songsloader() throws IOException {

        Filesloder("Songlist.txt");

        obsList = FXCollections.observableArrayList(newSongList);
        if(obsList.size() == 0) {
            tfName.setText("");
            tfArtist.setText("");
            tfAlbum.setText("");
            tfYear.setText("");
        }

        lvSongs.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Songs>() {

            @Override
            public void changed(ObservableValue<? extends Songs> arg0, Songs arg1, Songs arg2) {
                try {
                    tfName.setText(arg2.getSongname());
                    tfArtist.setText(arg2.getArtist());
                    tfAlbum.setText(arg2.getAlbum());
                    tfYear.setText(arg2.getYear());
                }
                catch(NullPointerException e) {

                }

            }
        });

        lvSongs.setItems(obsList);
        lvSongs.getSelectionModel().select(0);
    }




    public void start (Stage primaryStage) throws IOException {

        Songsloader();

        obsList = FXCollections.observableArrayList(obsList);
        lvSongs.setItems(obsList);

        lvSongs.getSelectionModel().selectedIndexProperty().addListener((obsList, oldVal, newVal) -> showItem(primaryStage));



    }

    //puts the values of the selected item into the textfields
    private void showItem(Stage primaryStage) {

        //get the song from the obsList
        int index = lvSongs.getSelectionModel().getSelectedIndex();
        Songs s = obsList.get(index);

        //put necessary values in the text field
        tfName.setText(s.getSongname());
        tfArtist.setText(s.getArtist());
        tfAlbum.setText(s.getAlbum());
        tfYear.setText(s.getYear());

    }

    public void convert(ActionEvent e) {

        Button b = (Button)e.getSource();
        if (b == btnAdd) {
            String songname = tfName.getText();
            String artist = tfArtist.getText();
            String album = tfAlbum.getText();
            String year = tfYear.getText();


            Songs addThisSong = new Songs(songname, artist, album, year);

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Need Confirmation");
            alert.setContentText("Are you ok with this?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                add(addThisSong);
            } else {
                // ... user chose CANCEL or closed the dialog
            }


        } else if (b == btnEdit){
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Need Confirmation");
            alert.setContentText("Are you ok with this?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                edit();
            } else {
                // ... user chose CANCEL or closed the dialog
            }

        } else if (b == btnDelete){
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Need Confirmation");
            alert.setContentText("Are you ok with this?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                delete();
            } else {
                // ... user chose CANCEL or closed the dialog
            }

        }
    }



    private void duplicateSongAlert() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(
                "That song is already in the library.");

        String content = "Please change the name of the song and/or the artist";

        alert.setContentText(content);
        alert.showAndWait();
    }

    private void noNameOrArtistAlert() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(
                "No name and/or artist detected.");

        String content = "Please input the name of the song and/or the artist";

        alert.setContentText(content);
        alert.showAndWait();
    }

    private void store() throws IOException {

        File songs = new File("Songlist.txt");
        BufferedWriter out = new BufferedWriter(new FileWriter("Songlist.txt"));

        for (int i = 0; i < obsList.size(); i++) {

            Songs s = obsList.get(i);
            out.write(s.fileString());

        }
        out.close();
    }

    private void add(Songs s) {

        String currName = s.getSongname(); String currArtist = s.getArtist();

        // if this returns a negative int pop up an error message
        int index = findSpot(s);
        if (index < 0) {
            duplicateSongAlert();
            return;
        } else if ( (currName.compareTo("") == 0) || (currArtist.compareTo("") == 0)){
            noNameOrArtistAlert();
            return;
        }

        obsList.add(index, s);
        lvSongs.setItems(obsList);
        //select the item just added
        lvSongs.getSelectionModel().select(index);
        //update();
        try {
            store();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void edit() {

        Songs edited = new Songs (tfName.getText(), tfArtist.getText(), tfAlbum.getText(), tfYear.getText());
        Songs currSong = lvSongs.getSelectionModel().getSelectedItem();
        String currAlbum = currSong.getAlbum(); String currYear = currSong.getYear();

        int i = findSpot(edited);

        if (i < 0) { // there exists a song with the same name and artist
            // check if they are changing the album or year
            if ((currAlbum.compareTo(edited.getAlbum()) == 0  )  && (currYear.compareTo(edited.getYear()) == 0)   ) {
                //return an error message

                duplicateSongAlert();

            } else {
                delete();
                add(edited);
            }

        } else if ( (edited.getSongname().compareTo("") == 0) || (edited.getArtist().compareTo("") == 0)) {
            noNameOrArtistAlert();
            return;
        } else {
            delete();
            add(edited);
        }
        //update();
        try {
            store();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void delete() {

        //get the index of the selected song
        int index = lvSongs.getSelectionModel().getSelectedIndex();
        obsList.remove(index);

        int n = obsList.size();

        //if we deleted the last item, make sure the new last item is selected
        //if not, make sure the next item is selected, which is now at the removed index anyways
        if (index == n) {
            lvSongs.getSelectionModel().select(index-1);

        } else if (obsList.isEmpty()) {
            //don't select anything since the list is empty. wait for a new add to select
        } else {
            lvSongs.getSelectionModel().select(index);
        }
        //update();
        try {
            store();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private int findSpot(Songs s) {
        if (obsList.isEmpty()) {
            return 0;
        }
        String name = s.getSongname();
        String artist = s.getArtist();

        int n = obsList.size();
        for (int i = 0; i < n; i++) {

            Songs t = obsList.get(i);
            String name2 = t.getSongname(); String artist2 = t.getArtist();

            if (name.compareToIgnoreCase(name2) < 0) {
                return i;
            } else if (name.compareToIgnoreCase(name2) > 0) {
                continue;
            } else {
                // case where song name is the same
                if (artist.compareToIgnoreCase(artist2) < 0) {
                    return i;
                } else if (artist.compareToIgnoreCase(artist2) > 0) {
                    continue;
                } else {
                    return -1;
                }

            }

        }
        return n;
    }
}
