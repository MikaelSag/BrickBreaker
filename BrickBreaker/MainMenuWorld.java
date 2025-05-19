import greenfoot.*;

public class MainMenuWorld extends World {
    private HelpPopup helpPopup = null; // Keep track of the help popup

    public MainMenuWorld() {
        super(550, 600, 1);
        GreenfootImage background = new GreenfootImage(getWidth(), getHeight());
        background.setColor(Color.BLACK);
        background.fill();
        setBackground(background);

        // Logo
        Logo gameLogo = new Logo(); 
        addObject(gameLogo, getWidth() / 2, 150);

        // Start Game button
        Button startGameButton = new Button("Start Game", () -> Greenfoot.setWorld(new MyWorld()));
        addObject(startGameButton, getWidth() / 2, 365);

        // Help button
        Button helpButton = new Button("Help", this::showHelpPopup); 
        addObject(helpButton, getWidth() / 2, 430);

        // Loing / Register Button
        Button loginRegisterButton = new Button("Login / Register", () -> Greenfoot.setWorld(new LoginWorld()));
        addObject(loginRegisterButton, getWidth() / 2, 490);
    }

    private void showHelpPopup() {
        if (helpPopup == null) {
            helpPopup = new HelpPopup();
            addObject(helpPopup, getWidth() / 2, getHeight() / 2);
        }
    }

    public void act() {
        // Keep the help popup null if it's not showing
        if (getObjects(HelpPopup.class).isEmpty()) {
            helpPopup = null;
        }
    }
}