import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Logo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Logo extends Actor
{
    /**
     * Act - do whatever the Logo wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public Logo()
    {
        // Add your action code here.
        setImage("brickBreakerLogo.png");
        GreenfootImage image = getImage();
        
        double scaleFactor = 0.7; // Scale offset

        int newWidth = (int) (image.getWidth() * scaleFactor);
        int newHeight = (int) (image.getHeight() * scaleFactor);

        image.scale(newWidth, newHeight);
    }
}
