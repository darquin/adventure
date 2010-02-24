

import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class Craft {

    private String craft = "xd.png";

    private int dx;
    private int dy;
    private int x;
    private int y;
    private Image image;

    public Craft() 
    {
        ImageIcon ii = new ImageIcon(this.getClass().getResource(craft));
        image = ii.getImage();
        x = 40;
        y = 60;
    }
    
    public int getX() 
    {
        return x;
    }

    public int getY() 
    {
        return y;
    }
    
 	public Image getImage() 
 	{
        return image;
    }
    public void move()
    {
	    dx = 0;
	    dy = 0;
		x += dx;
		y += dy;
	}
	 public void keyPressed(KeyEvent e) 
	 {

        int key = e.getKeyCode();

      /*  if (key == KeyEvent.VK_SPACE) {
            fire();
        }*/

        if (key == KeyEvent.VK_LEFT) {
            x += -10;
        }

        if (key == KeyEvent.VK_RIGHT) {
            x = 10;
        }

        if (key == KeyEvent.VK_UP) {
            y = -10;
        }

        if (key == KeyEvent.VK_DOWN) {
            y = 10;
        }
   	 }
    
     public void keyReleased(KeyEvent e)
     {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_UP) {
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 0;
        }
   	 }



}
