import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

public class Planet extends Actor
{
    private boolean hasKeyBrick;
    private int speed = 1;
    private int direction = 1;
    private ArrayList<Brick> bricks = new ArrayList<>();

    public Planet(boolean hasKeyBrick)
    {
        this.hasKeyBrick = hasKeyBrick;
        setImage(new GreenfootImage(1, 1)); // Tiny invisible 1x1 image
    }

    protected void addedToWorld(World world)
    {
        createDiamond();
    }

    public void act()
    {
        int oldY = getY();
        setLocation(getX(), getY() + speed * direction);
        int deltaY = getY() - oldY;

        // Move all bricks by the same delta
        for (Brick brick : bricks) {
            if (brick.getWorld() != null) { // Make sure brick is still in the world
                brick.setLocation(brick.getX(), brick.getY() + deltaY);
            }
        }

        // Reverse direction if hitting bounds
        if (getY() <= 50 || getY() >= 400) {
            direction *= -1;
        }
    }

    private void createDiamond()
    {
        Brick brick1 = new Brick();
        int brickWidth = brick1.getImage().getWidth() + 3;
        int brickHeight = brick1.getImage().getHeight() + 3;

        int[] bricksInRow = {1, 2, 3, 2, 1};
        int startY = -brickHeight * bricksInRow.length / 2;

        for (int row = 0; row < bricksInRow.length; row++) {
            int numBricks = bricksInRow[row];
            int startX = -brickWidth * numBricks / 2 + brickWidth / 2;

            for (int i = 0; i < numBricks; i++) {
                Brick brick;
                if (hasKeyBrick && row == 2 && i == 1) {
                    brick = new KeyBrick();
                } else {
                    brick = new Brick();
                }
                getWorld().addObject(brick, getX() + startX + i * brickWidth, getY() + startY + row * brickHeight);
                bricks.add(brick); // Save brick into list
            }
        }
    }
}