import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class TextBox extends Actor
{
    private String text = "";
    private String prompt;
    private boolean isActive = false;

    public TextBox(String prompt) {
        this.prompt = prompt;
        updateImage();
    }

    public void act()
    {
        if (Greenfoot.mouseClicked(this)) {
            isActive = true;
        } else if (Greenfoot.mouseClicked(null) && !Greenfoot.mouseClicked(this)) {
            isActive = false;
        }

        if (isActive) {
            String key = Greenfoot.getKey();
            if (key != null) {
                if (key.equals("backspace") && text.length() > 0) {
                    text = text.substring(0, text.length() - 1);
                }
                else if (key.length() == 1) {
                    text += key;
                }
                updateImage();
            }
        }
    }

    private void updateImage() {
        String display = (text.isEmpty()) ? prompt : text;
        GreenfootImage img = new GreenfootImage(200, 30);
        img.setColor(Color.WHITE);
        img.fill();
        img.setColor(Color.BLACK);
        img.drawRect(0, 0, 199, 29);
        img.drawString(display, 5, 20);
        setImage(img);
    }

    public String getText() {
        return text;
    }
}
