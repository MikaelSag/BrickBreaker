import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A clickable button that runs the given action when clicked.
 */
public class MenuButton extends Actor {
    private Runnable action;

    public MenuButton(String label, Runnable action) {
        this.action = action;
        // white text on blue background
        GreenfootImage img = new GreenfootImage(label, 28, 
            Color.WHITE,        // text color
            Color.BLUE          // fill color
        );
        setImage(img);          
    }

    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            action.run();
        }
    }
}
