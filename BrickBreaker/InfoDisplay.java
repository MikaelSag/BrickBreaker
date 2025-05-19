import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

// Display level number and live count
public class InfoDisplay extends Actor {
    private int level;
    private int lives;

    public InfoDisplay(int initialLevel, int initialLives) {
        level = initialLevel;
        lives = initialLives;
        updateImage(); // makes sure the level and live count are updates throughout gameplay
    }

    public void setLevel(int newLevel) {
        level = newLevel;
        updateImage();
    }

    public void setLives(int newLives) {
        lives = newLives;
        updateImage();
    }

    private void updateImage() {
        GreenfootImage image = new GreenfootImage("Level: " + level 
        + "\nLives: " + lives, 20, Color.WHITE, new Color(0, 0, 0, 0));
        setImage(image);
    }
}
