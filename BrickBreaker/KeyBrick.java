import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class KeyBrick here.
 * 
 */
public class KeyBrick extends Brick
{
    private GreenfootImage normalImage;
    private GreenfootImage damagedImage1;
    private GreenfootImage damagedImage2;
    private int hits = 0; // track number of hits

    public KeyBrick() {
        normalImage = getImage();
        damagedImage1 = new GreenfootImage("05-Breakout-Tiles.png");
        damagedImage2 = new GreenfootImage("04-Breakout-Tiles.png");
        setHealth(6);
        setKey();
    }

    public void handleCollision() {
        hits++;
        if (hits == 2) { // change to damagedImage after 2 hits
            damagedImage1.scale(50, 20);
            setImage(damagedImage1);
        }
        if (hits == 4) {
            damagedImage2.scale(50, 20);
            setImage(damagedImage2);
        }

        super.handleCollision(); // call Brick handleCollision method to reduce health
    }
}
