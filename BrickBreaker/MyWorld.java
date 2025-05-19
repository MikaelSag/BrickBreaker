import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

/**
 * MyWorld class serves as the base world for the game.
 * It handles level loading and transitions.
 */
public class MyWorld extends World {
    private int livesCount;
    public int currentLevel;
    private ArrayList<Brick> bricks = new ArrayList<>();
    private InfoDisplay display;
    private int savedVolume = 30;
    private boolean isMuted = false;
    
    public boolean isMuted() {
        return isMuted;
    }
    
    private boolean paused = false;
    public void setPaused(boolean p) { paused = p; }
    public boolean isPaused()    { return paused; }

    
    private GreenfootSound bgMusic = new GreenfootSound("BackgroundMusic.mp3");
    
    /**
     * Create a new world of 550x600 cells (cell size 1x1 pixels).
     */
    public MyWorld() {    
        super(550, 600, 1);
        currentLevel = 1;
        livesCount = 3; 
        
        GreenfootImage background = getBackground();
        background.setColor(Color.BLACK); // make background black
        background.fill();
    
        // add paddle and ball to game world
        addObject(new Paddle(), 275, 500);
        addObject(new Ball(), 275, 480);
        
        display = new InfoDisplay(currentLevel, livesCount);
        addObject(display, 100, 20); // Adjust position
        
        loadLevel(currentLevel);
        bgMusic.setVolume(savedVolume);
        bgMusic.playLoop();
        setPaintOrder(MenuButton.class, MenuOverlay.class);

    }
    public void stopBackgroundMusic() {
        bgMusic.stop();
    }   
     public void stopped() {
        bgMusic.pause();
    }
     public void started() {
        bgMusic.play(); 
    }
    public void act() {
    if ((Greenfoot.isKeyDown("p") || Greenfoot.isKeyDown("escape")) && !paused) {
        showPauseMenu();
    }
    if (paused) return;

    // Mute toggle on “M” key
    if (Greenfoot.isKeyDown("m")) {        
        toggleMute();                     
        while(Greenfoot.isKeyDown("m")) { Greenfoot.delay(1); }  
    }


}

    private void showPauseMenu() {
        setPaused(true);
        addObject(new MenuOverlay(this), getWidth()/2, getHeight()/2);
    }
    
    private void toggleMute() {
    isMuted = !isMuted;
    if (isMuted) {
        // stop the music entirely
        bgMusic.pause();
    } else {
        // resume looping music
        bgMusic.playLoop();
    }
}
    
     public void loadLevel(int level) {
        removeObjects(getObjects(Brick.class));  // clear the previous level
        
        if (level == 1) {
            createLevel1Layout();
        } 
        else if (level == 2) {
            createLevel2Layout();
        }
        else if (level == 3) {
            createLevel3Layout();
        }
        else if (level == 4) {
            createLevel4Layout();
        }
        else if (level == 5) {
            createLevel5Layout();
        }
        else {
            displayEndMessage("Victory!"); // game over, user won
        }
    }
    
    public void nextLevel() {
        repositionBall();
        currentLevel++; 
        display.setLevel(currentLevel);
        loadLevel(currentLevel);  
    }
    
    public void removeBrickAt(int x, int y) {
        for (Brick brick : bricks){
            if (brick.getX() == x && brick.getY() == y){
                removeObject(brick);
                bricks.remove(brick);
                break; // exit loop once removed
                }
        }
    }
    
    public void createLevel1Layout(){
        Brick brick1 = new Brick();
        int brickWidth = brick1.getImage().getWidth() + 3; // add 3 for spacing
        int brickHeight = brick1.getImage().getHeight() + 3;
        
        // fill the rectangular grid below with bricks
        for (int x = 115; x <= 450; x += brickWidth){
            for (int y = 150; y <= 300; y += brickHeight){
                Brick brick = new Brick();
                addObject(brick, x, y);
                bricks.add(brick);
            }
        }
        
        int x = 115 + 3 * brickWidth;   // x value where columns start + (column to delete - 1) * brick width
        int y = 150 + 3 * brickHeight;   // y value where rows start + (row to delete - 1) * brick height
        removeBrickAt(x,y);
        
        KeyBrick keybrick = new KeyBrick();
        addObject(keybrick, x, y);
    }
    
    public void createLevel2Layout(){
        Brick brick1 = new Brick();
        int brickWidth = brick1.getImage().getWidth() + 3;  // Add 3 for spacing
        int brickHeight = brick1.getImage().getHeight() + 3;
        int rows = 7; // Total rows: 5 for the pyramid, 2 for the top rows
        int bricksInBaseRow = (450 - 100) / brickWidth + 1; // Calculate number of bricks in base
        int startY = 275; // Start from the bottom and go upward

        for (int row = 0; row < rows; row++) {
        // Adjust number of bricks in each row
            int bricksInRow;
            if (row < 5) {
                bricksInRow = bricksInBaseRow - row; // Pyramid structure
            } else if (row == 5) {
                bricksInRow = 2; // The second-to-last row (2 bricks)
            } else {
                bricksInRow = 1; // The last row (just the key brick)
            }

            int totalRowWidth = bricksInRow * brickWidth;
            int startX = 275 - totalRowWidth / 2 + brickWidth / 2; // Center the row (275 is halfway between 100 and 450)

            for (int i = 0; i < bricksInRow; i++) {
                int x = startX + i * brickWidth;
                int y = startY - row * brickHeight;

                Brick brick;
                if (row == 6 && i == 0) {
                    brick = new KeyBrick(); // Special brick at the top
                } else {
                    brick = new Brick();
                }

                addObject(brick, x, y);
                bricks.add(brick);
            }
        }
    }

    public void createLevel3Layout(){
        Brick brick1 = new Brick();
        int brickWidth = brick1.getImage().getWidth() + 3; // add 3 for spacing
        int brickHeight = brick1.getImage().getHeight() + 3;
        
            // fill the rectangular grid below with bricks
            for (int x = 37; x <= 525; x += brickWidth){
                for (int y = 150; y <= 325; y += brickHeight){
                    Brick brick = new Brick();
                    addObject(brick, x, y);
                    bricks.add(brick);
                    }
            }
        
        int x = 275;
        int y = 15;
        KeyBrick keybrick = new KeyBrick();
        addObject(keybrick, x, y);
    }

    public void createLevel4Layout(){
        Planet p1 = new Planet(false);
        Planet p2 = new Planet(true);
        Planet p3 = new Planet(false);
        addObject(p1, 100, 150); // normal planet
        addObject(p2, 275, 250);  // special planet with KeyBrick
        addObject(p3, 450, 150); // another normal planet
        }
    
    public void createLevel5Layout() {
        Brick brick1 = new Brick();
        int brickWidth = brick1.getImage().getWidth() + 3;  // add 3 for spacing
        int brickHeight = brick1.getImage().getHeight() + 3;
    
        // Maze layout using bricks (0 = empty, 1 = brick 2 = spike, 3 = keybrick)
        int[][] mazeLayout = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 1, 1, 0, 0, 0, 1},
            {1, 0, 3, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 1, 1, 0, 2, 0, 1},
            {1, 0, 1, 1, 1, 1, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 1, 0, 1, 0, 1},
            {1, 0, 0, 2, 0, 1, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 1, 0, 2, 0, 1},
            {1, 0, 1, 1, 1, 1, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 0, 1, 1, 1, 1, 0, 1, 1}
        };
    
        // Iterate through the maze layout to place bricks, spikes, and the keybrick
        for (int row = 0; row < mazeLayout.length; row++) {
            for (int col = 0; col < mazeLayout[row].length; col++) {
                int x = col * brickWidth + 37;  // 100px offset for x-position
                int y = row * brickHeight + 90;  // 80px offset for y-position
    
                if (mazeLayout[row][col] == 1) {
                    // Add regular brick (wall) at this location
                    Brick brick = new Brick();
                    addObject(brick, x, y);
                } else if (mazeLayout[row][col] == 2) {
                    // Add spike at this location (wrong path)
                    Spike spike = new Spike();
                    addObject(spike, x, y);
                } else if (mazeLayout[row][col] == 3) {
                    // Add the keybrick at a specific location
                    KeyBrick keyBrick = new KeyBrick();
                    addObject(keyBrick, x, y);
                }
            }
        }
    }

    private void addSpike(int x, int y) {
        Spike spike = new Spike();
        addObject(spike, x, y);  // Add the spike at the given (x, y) coordinates
    }
    
    public void displayMessage(String message) {
        GreenfootImage bg = getBackground();
        GreenfootImage originalbg = new GreenfootImage(bg);
        
        bg.setColor(Color.WHITE);
        bg.setFont(bg.getFont().deriveFont(36f)); 

        GreenfootImage tempImage = new GreenfootImage(message, 36, Color.GREEN, new Color(0,0,0,0));
        int textWidth = tempImage.getWidth();
        int textHeight = tempImage.getHeight();

        int x = (getWidth() - textWidth) / 2;
        int y = 400;

        bg.drawString(message, x, y);
        
        Greenfoot.delay(100);
        setBackground(originalbg);
    }
    
    public void displayEndMessage(String message) {
        GreenfootImage bg = getBackground();
        GreenfootImage originalbg = new GreenfootImage(bg);
        
        bg.setColor(Color.WHITE);
        bg.setFont(bg.getFont().deriveFont(36f)); 

        GreenfootImage tempImage = new GreenfootImage(message, 36, Color.GREEN, new Color(0,0,0,0));
        int textWidth = tempImage.getWidth();
        int textHeight = tempImage.getHeight();

        int x = (getWidth() - textWidth) / 2;
        int y = 400;

        bg.drawString(message, x, y);

        // return to main menu
        Greenfoot.delay(100);
        Greenfoot.setWorld(new MainMenuWorld());
    }
    
    /**
     * Decrements lives count (to be called when the ball falls out of bounds) and checks for game loss.
     */
    public void decrementLivesCount() {
        livesCount--;
        display.setLives(livesCount);
        if (livesCount <= 0) {
            displayEndMessage("Game Over!"); // display a "Game Over" message
        } else { // If the player still has lives remaining, reposition the ball
            repositionBall();
        }
    }
    
    private void repositionBall() {
        Ball oldBall = (Ball) getObjects(Ball.class).get(0); // get old ball
        removeObject(oldBall); // remove old ball
        addObject(new Ball(), 275, 480); // place new ball on paddle
    } 


    /**
     * Update the currently user's win count (number of times they beat level 5) in the user data file.
     */
    private void updateWinCount() {
    try {
        File file = new File("players.txt");
        if (!file.exists()) {
            return; // if file doesn't exist, do nothing (nothing to update)
        }
        
        ArrayList<String> lines = new ArrayList<>(); // arr list to read all users' data, update, and write them back as needed
        Scanner scanner = new Scanner(file);
        boolean updated = false; // flag to check if current user's win count has been updated

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");

            // Check if the line belongs to the current logged-in user, and update their win count accordingly
            if (parts.length >= 3 && parts[0].equals(LoginWorld.currentUsername)) {
                int wins = Integer.parseInt(parts[2]); // read current win count
                wins++; // update win count
                parts[2] = String.valueOf(wins); // set updated win count
                line = parts[0] + "," + parts[1] + "," + parts[2]; // rebuild the line with updated win count
                updated = true;
            }

            lines.add(line); // add the current line to the list (updated if current user won level 5)
        }
        scanner.close();

        // If the user's win count was updated, write the updated data back to the file
        if (updated) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
            for (String updatedLine : lines) {
                writer.write(updatedLine); // write the updated lines back to the file
                writer.newLine();
            }
            writer.close();
        }

    } catch (IOException e) {
        System.err.println("An error occurred while trying to update win count: " + e.getMessage());
    }
}
}





