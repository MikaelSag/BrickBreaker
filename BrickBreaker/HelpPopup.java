import greenfoot.*;

public class HelpPopup extends Actor {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;
    private static final Color BACKGROUND_COLOR = new Color(0, 0, 0, 200); // Semi-transparent black
    private static final Color TEXT_COLOR = Color.WHITE;

    private String instructions;

    public HelpPopup() {
        instructions = "How to Play:\n\n" +
                                  "Use the left and right arrow keys to move the paddle.\n" +
                                  "Use the up arrow key to unstick the ball from the paddle.\n" +
                                  "Use the 'p' or the escape key to pause the game.\n" +
                                  "Use the 'm' key to mute background music.\n" +
                                  "Hit the ball to break the bricks.\n" +
                                  "To advance to the next level, break the green Key Brick.\n" +
                                  "The ball must hit the Key Brick 3 times for it to break.\n" +
                                  "Avoid letting the ball fall off the bottom of the screen.\n" +
                                  "Avoid letting the ball collide with any sharp objects.\n" +
                                  "You have 3 lives to complete all 5 levels.\n\n" +
                                  "Click anywhere on this box to close.";
        createImage();
    }

    private void createImage() {
        GreenfootImage image = new GreenfootImage(WIDTH, HEIGHT);
        image.setColor(BACKGROUND_COLOR);
        image.fillRect(0, 0, WIDTH, HEIGHT);
        image.setColor(TEXT_COLOR);

        // Split instructions into lines and draw them
        String[] lines = instructions.split("\n");
        int y = 50;
        for (String line : lines) {
            image.drawString(line, 30, y);
            y += 18;
        }

        // Add a "Close" button within the popup
        GreenfootImage closeButton = new GreenfootImage("Close", 20, TEXT_COLOR, BACKGROUND_COLOR);
        image.drawImage(closeButton, WIDTH - closeButton.getWidth() - 20, HEIGHT - closeButton.getHeight() - 20);

        setImage(image);
    }

    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            World world = getWorld();
            if (world != null) {
                world.removeObject(this);
            }
        }
    }
}
