package model;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class DataSaving {
    public static void saveData(ArrayList<User> users) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("Photos62/data/data.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            for (User curr: users) {
                objectOutputStream.writeObject(curr);
            }
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
