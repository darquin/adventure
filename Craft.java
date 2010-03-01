

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.awt.Graphics;

import javax.swing.ImageIcon;

public class Craft {

    private String craft = "images/wizard.png";

    // private int dx;
    // private int dy;
    private int x;
    private int y;
    private Image image;
    private char moveDirection;

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
    
    public void move(KeyEvent e)
    {
        int key = e.getKeyCode();
        
    	switch (key)  {
    	case KeyEvent.VK_LEFT:
    	    craft = "images/wizard.png";
    	    x -= 20;
    	    break;
    	case KeyEvent.VK_RIGHT:
    	    craft = "images/wizardRight.png";
    	    x += 20;
    	    break;
    	case KeyEvent.VK_UP:
    	    y -= 20;
    	    break;
    	case KeyEvent.VK_DOWN:
    	    y += 20;
    	    break;
    	default:
    	    break;
    	}
	}
}
