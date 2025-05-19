import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Brick here.
 * 
 * @author (Ritvik Gutta) 
 * @version (3/20/2025 - 17:26)
 */
public class Brick extends GameElement
{
    private int health = 1;
    private boolean isKey = false;
    
    public Brick() {
        GreenfootImage img = getImage();
        img.scale(50,20); 
        setImage(img);
    }
    
    public void act()
    {
        checkForCollision();
    }
    
    public void checkForCollision(){
        if (isTouching(Ball.class)){
            handleCollision();
        }
    }

    public void handleCollision(){
        if (getWorld() == null) return;  

        health--;
        MyWorld world = (MyWorld) getWorld();
        
        if (health <= 0){
            getWorld().removeObject(this);
            if(isKey){
                Greenfoot.playSound("LevelComplete.mp3");
                // Greenfoot.stop(); // stop the game when key brick is hit 3+ times
                // display a "Level Complete" message
                String message = "Level " + world.currentLevel + " Complete!";
                world.displayMessage(message);
                world.nextLevel();
                
                }
        }
        
    }
 
    public void setHealth(int i){
         health = i;
    }
    
    public int getHealth(){
        return health;
    }
    
    public void setKey(){
        isKey = true;
    }
}
