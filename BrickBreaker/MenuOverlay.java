import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * MenuOverlay displays a dark panel with buttons to
 * resume, restart, or quit the game.
 */
public class MenuOverlay extends Actor {
    private MyWorld world;

    // ─── Constructor ────────────────────────────────────
    public MenuOverlay(MyWorld w) {
        world = w;
        GreenfootImage bg = new GreenfootImage(220, 240);
        bg.setColor(Color.DARK_GRAY);
        bg.fillRect(0,0,220,240);
        bg.setColor(Color.WHITE);
        bg.drawRect(0,0,219,239);
        setImage(bg);

        int cx = world.getWidth() / 2;
        int cy = world.getHeight() / 2;
        addButton("Resume", this::resume,  cx, cy - 60);
        addButton("Restart", this::restart, cx, cy    );
        addButton("Quit",    this::quit,    cx, cy + 60);
    }  

    private void addButton(String label, Runnable action, int x, int y) {
        MenuButton b = new MenuButton(label, action);
        GreenfootImage img = b.getImage();
        // add padding around the text
        img.scale(img.getWidth() + 20, img.getHeight() + 10);
        b.setImage(img);
        world.addObject(b, x, y);
    }

    private void resume() {
        world.setPaused(false);
        world.removeObjects(world.getObjects(MenuButton.class));
        world.removeObject(this);
    }

    private void restart() {
        Greenfoot.setWorld(new MyWorld());
    }
    private void quit() {
        world.removeObjects(world.getObjects(MenuButton.class));
        world.removeObject(this);
        Greenfoot.stop();
    }
}
