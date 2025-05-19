import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Random;

public class Ball extends GameElement {
    private boolean isLaunched;
    private int dx, dy;
    
    public Ball(){
        GreenfootImage img = getImage();
        img.scale(28,28); // Set height to 50, keeping the width the same
        setImage(img);
        
    }
    
    public void act() {
        if (((MyWorld)getWorld()).isPaused()) return;

        if (!isLaunched) {
            stickToPaddle();
            checkForLaunch();
        } else {
            moveBall();
            checkForBrickCollision();  // Check for collisions with bricks
            if (getWorld() == null) return;
            checkForOutOfBounds();
            
        } 
    }
    
    private void stickToPaddle() {
        Paddle paddle = (Paddle) getWorld().getObjects(Paddle.class).get(0);
        setLocation(paddle.getX(), paddle.getY() - 20);
    }
    
    private void checkForLaunch() {
    if (Greenfoot.isKeyDown("UP")) {
        MyWorld world = (MyWorld)getWorld(); 
        world.stopBackgroundMusic();   
        isLaunched = true;
        if (!world.isMuted()) {
            Greenfoot.playSound("LaunchAudio.mp3");
        }

        int randAngle = Greenfoot.getRandomNumber(60) - 30; // -30 to +30 degrees
        double radians = Math.toRadians(randAngle);

        int launchSpeed = 7; // <-- Constant speed
        dx = (int)(launchSpeed * Math.sin(radians));
        dy = -(int)(launchSpeed * Math.cos(radians)); // negative to go upward
    }
}
    
    private void moveBall() {
        int speedFactor = 1;
        
        if (getX() <= 0 || getX() >= getWorld().getWidth() - 1)
            dx = -dx;  // Bounce horizontally
        
        if (getY() <= 0) 
            dy = -dy;  // Bounce vertically off the ceiling
        
        if (isTouching(Paddle.class)) {
            MyWorld world = (MyWorld)getWorld();
            if (!world.isMuted()) {
                Greenfoot.playSound("PaddleHit.mp3");
            }
        
            Paddle paddle = (Paddle) getOneIntersectingObject(Paddle.class);
            int paddleX = paddle.getX();
            int ballX = getX();
            int offset = ballX - paddleX;
        
            // Now calculate new dx based on how far from center you hit
            dx = offset / 5; // tweak this divisor for sensitivity
            dy = -Math.abs(dy); // always go upward after paddle hit
        }
        setLocation(getX() + dx * speedFactor, getY() + dy * speedFactor);
        
       if (getY() > getWorld().getHeight() - 1) {
        MyWorld world = (MyWorld)getWorld();
            if (!world.isMuted()) {                        // ← guard life-lost SFX
                Greenfoot.playSound("LevelLost.mp3");
            }
        getWorld().removeObject(this);
    }
}

    
    public void checkForBrickCollision() {
        if (getWorld() == null) return;  // Still important!
    
        Brick brick = (Brick) getOneIntersectingObject(Brick.class);
        if (brick != null) {
            MyWorld world = (MyWorld)getWorld();
            if (!world.isMuted()) { 
                Greenfoot.playSound("BrickHitAudio.mp3");
            }
    
            // Bounce **first** before doing anything that might remove the brick
            int ballX = getX();
            int ballY = getY();
            int brickX = brick.getX();
            int brickY = brick.getY();
            int brickWidth = brick.getImage().getWidth();
            int brickHeight = brick.getImage().getHeight();
    
            if (ballX < brickX - brickWidth / 2 || ballX > brickX + brickWidth / 2) {
                bounceHorizontal();
            } else if (ballY < brickY - brickHeight / 2 || ballY > brickY + brickHeight / 2) {
                bounceVertical();
            }
    
            // Add slight random curve after hitting a brick
            int randomNudge = Greenfoot.getRandomNumber(3) - 1; // -1, 0, or +1
            dx += randomNudge;
    
            // Optional: Limit max horizontal speed
            if (dx > 8) dx = 8;
            if (dx < -8) dx = -8;
            
            if (brick.getWorld() != null) {
                brick.handleCollision();
            }
        }
    }

    public void bounceHorizontal() {
        dx = -dx;  // Reverse the horizontal direction
    }
    public void bounceVertical() {
        dy = -dy;  // Reverse the vertical direction
    }
    
    private void checkForOutOfBounds() {
        if (getY() >= getWorld().getHeight() - 1) {
            MyWorld gameWorld = (MyWorld) getWorld();
            gameWorld.decrementLivesCount();
        }
    }
}







