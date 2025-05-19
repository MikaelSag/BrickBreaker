import greenfoot.*;

public class Button extends Actor {
    private String text;
    private GreenfootImage image;
    private Runnable action;

    public Button(String text, Runnable action) {
        this.text = text;
        this.action = action;
        createImage();
    }

    public Button(String text) {
        this(text, null);
    }

    private void createImage() {
        //Font font = new Font("", true, 24); // Greenfoot's Font constructor
        GreenfootImage textImage = new GreenfootImage(text, 24, Color.WHITE, new Color(0, 0, 0, 0));
        //textImage.setFont(font);

        GreenfootImage background = new GreenfootImage(textImage.getWidth() + 20, textImage.getHeight() + 10);
        background.setColor(new Color(50, 50, 50)); // Greenfoot's Color
        background.fill();
        background.drawImage(textImage, 10, 5);
        setImage(background);
    }

    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            if (action != null) {
                action.run();
            }
        }
    }
}