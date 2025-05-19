import greenfoot.*;
import java.io.*;
import java.util.Scanner;

public class LoginSystem extends Actor {
    private static final String USER_DATA_FILE = "UserData.txt";

    /**
     * Register a new user.
     */
    public static boolean register(String username, String password) {
        try (FileWriter fw = new FileWriter(USER_DATA_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            
            // Check if username already exists
            if (userExists(username)) {
                return false;
            }
            
            bw.write(username + ":" + password);
            bw.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Login an existing user.
     */
    public static boolean login(String username, String password) {
        try (Scanner scanner = new Scanner(new File(USER_DATA_FILE))) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(":");
                if (parts[0].equals(username) && parts[1].equals(password)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No users registered yet.");
        }
        return false;
    }

    /**
     * Check for existing username in file.
     */
    private static boolean userExists(String username) {
        try (Scanner scanner = new Scanner(new File(USER_DATA_FILE))) {
            while (scanner.hasNextLine()) {
                if (scanner.nextLine().split(":")[0].equals(username)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            return false;  // file doesn't exist yet
        }
        return false;
    }
}
