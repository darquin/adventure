

import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class Craft {

    private String craft = "images/wizard.png";

 //   private int dx;
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
    public void move(char tempDirection)
    {
    	switch (tempDirection) 
    	{
    	case 'l': craft = "images/wizard.png"; x -= 20; break;
    	case 'r': craft = "images/wizardRight.png"; x += 20; break;
    	case 'u': y -= 20; break;
    	case 'd': y += 20; break;
    	case 'w': break;
    	}
	}
    
	 public char keyPressed(KeyEvent e) 
	 {

        int key = e.getKeyCode();

      /*  if (key == KeyEvent.VK_SPACE) {
            fire();
        }*/

        if (key == KeyEvent.VK_LEFT) 
        {
        	moveDirection = 'l';
        	return moveDirection;
        }

        if (key == KeyEvent.VK_RIGHT) 
        {
        	moveDirection = 'r';
        	return moveDirection;
        }
        if (key == KeyEvent.VK_UP) 
        {
        	moveDirection = 'u';
        	return moveDirection;
        }

        if (key == KeyEvent.VK_DOWN) 
        {
        	moveDirection = 'd';
        	return moveDirection;
        }
        else return 'w';
   	 }
    
 /*    public void keyReleased(KeyEvent e)
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
   	 }*/



}
