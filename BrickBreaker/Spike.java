import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Spike extends Actor {
    
    public Spike(){
        GreenfootImage img = getImage();
        img.scale(35, 25);
        setImage(img);
    }
    // This class represents the spike. It will check for collisions with the player
    public void act() {
        // Detect collision with the player
        Actor ball = getOneIntersectingObject(Ball.class);
        
        if (ball != null) {
            // Deduct life from the player when it intersects with the spike
            if (ball instanceof Ball) {
                MyWorld gameWorld = (MyWorld) getWorld();
                gameWorld.decrementLivesCount();
            }

            // Optional: Remove the spike after it hits the player (if spikes disappear after hitting player)
            getWorld().removeObject(this);
        }
    }
}
