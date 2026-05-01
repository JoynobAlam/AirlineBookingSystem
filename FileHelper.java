import java.io.*;
import java.util.Scanner;

class FileHelper {
    void saveUser(String id, String fname, String lname, String bdate, String pass) {

        try {
            FileWriter fw = new FileWriter("users.txt", true); 

            fw.write(id + "," + fname + "," + lname + "," + bdate + "," + pass + "\n");

            fw.close();

            System.out.println("User saved to file.");

        } catch (Exception e) {
            System.out.println("Error saving data.");
        }
    }
}



