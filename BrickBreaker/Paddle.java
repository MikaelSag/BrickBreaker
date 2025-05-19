import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Paddle here.
 * 
 * @author (Ritvik Gutta)
 * @version (3/24/2025 - 14:37)
 */
public class Paddle extends GameElement {

    public Paddle() {
        GreenfootImage img = getImage();
        img.scale(100,16); 
        setImage(img);
    }

    /**
     * Act - do whatever the Paddle wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
            if (((MyWorld)getWorld()).isPaused()) return;
            movement();
    }

    /**
     * Paddle left and right movement.
     */
    private void movement() {
        if (Greenfoot.isKeyDown("RIGHT") && getX() < getWorld().getWidth() - 1) {
            setRotation(0);
            move(10);
        } else if (Greenfoot.isKeyDown("LEFT") && getX() > 1) {
            setRotation(180);
            move(10);
        }
    }
}
