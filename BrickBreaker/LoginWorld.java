import greenfoot.*;
import java.io.*;
import java.util.Scanner;

public class LoginWorld extends World
{
    private TextBox usernameBox;
    private TextBox passwordBox;
    private int messageTimer = 0;
    private String messageText = "";
    private GreenfootImage logo;
    public static String currentUsername;

    public LoginWorld()
    {    
        super(550, 600, 1);
        int centerX = getWidth() / 2;
        
        // Initialize and store the logo image
        logo = new GreenfootImage("Brick_Breaker_Logo.png");
        
        // Draw initial background with logo
        redrawBackground();

        usernameBox = new TextBox("Username");
        passwordBox = new TextBox("Password");

        // Move everything downward to leave space for logo at top
        addObject(usernameBox, centerX, 300);
        addObject(passwordBox, centerX, 350);
        
        addObject(new MenuButton("Login", () -> login()), centerX, 400);
        addObject(new MenuButton("Register", () -> register()), centerX, 450);
        
        // Create players.txt if it doesn't exist
        createPlayersFileIfNeeded();
    }

    private void createPlayersFileIfNeeded() {
        try {
            File file = new File("players.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Error creating players.txt: " + e.getMessage());
        }
    }

    public void act() {
        if (messageTimer > 0) {
            messageTimer--;
            if (messageTimer == 0) {
                messageText = "";
                redrawBackground();
            }
        }
        if (!messageText.isEmpty()) {
            displayMessage(messageText);
        }
    }

    private void login() {
        String username = usernameBox.getText();
        String password = passwordBox.getText();

        boolean found = false;

        try {
            File file = new File("players.txt");
            if (!file.exists()) {
                file.createNewFile();
            }

            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    if (parts[0].equals(username) && parts[1].equals(password)) {
                        found = true;
                        currentUsername = username; // keep track of currently logged in user to keep win count updated
                        messageText = "Login successful!";
                        messageTimer = 100;
                        Greenfoot.delay(50); 
                        Greenfoot.setWorld(new MyWorld()); // transition to game world
                        break;
                    }
                }
            }
            scanner.close();
            
            if (!found) {
                messageText = "Invalid username or password!";
                messageTimer = 100;
            }
        } catch (IOException e) {
            messageText = "Error reading players.txt.";
            messageTimer = 100;
        }
    }

    private void register() {
        String username = usernameBox.getText();
        String password = passwordBox.getText();

        boolean exists = false;

        try {
            File file = new File("players.txt");
            if (!file.exists()) {
                file.createNewFile();
            }

            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length >= 1 && parts[0].equals(username)) {
                    exists = true;
                    break;
                }
            }
            scanner.close();

            if (exists) {
                messageText = "Username already exists!";
                messageTimer = 100;
            } else {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
                writer.write(username + "," + password + ",0");
                writer.newLine();
                writer.close();
                messageText = "Registration successful!";
                messageTimer = 100;
            }
        } catch (IOException e) {
            messageText = "Error writing to players.txt.";
            messageTimer = 100;
        }
    }

    public void displayMessage(String message) {
        GreenfootImage bg = getBackground();
        redrawBackground(); // Redraw background with logo first

        bg.setColor(Color.BLACK);
        bg.setFont(bg.getFont().deriveFont(24f));

        int x = getWidth() / 2 - (message.length() * 6); // approx. centering
        int y = 500; // Below buttons

        bg.drawString(message, x, y);
    }

    private void redrawBackground() {
        GreenfootImage bg = getBackground();
        bg.setColor(Color.WHITE);
        bg.fill();
        // Always redraw the logo when redrawing background
        bg.drawImage(logo, 150, 50);
    }
}