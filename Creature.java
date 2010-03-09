

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

import javax.swing.ImageIcon;

public class Creature {

    private String originalImage;
    protected String creatureImage;
    
    private Map map;
    
    private int y;
    private int x;
    private Image image;
    private String Alignment;
    private Creature Target;

    public Creature(int x, int y, String img) 
    {
    	
    	creatureImage = "images/"+img+".png";
    	originalImage=img;
    	
    	System.out.println(this.creatureImage);
        ImageIcon ii = new ImageIcon(this.creatureImage);
        image = ii.getImage();
        this.y = y;
        this.x = x;
        Alignment = "neutral";
        
    }
    
    public void setMap(Map map)
    {
        this.map = map;
    }
    
    public int getY() 
    {
        return this.y;
    }

    public int getX() 
    {
        return this.x;
    }
    
    public Image getImage() 
    {
        return image;
    }
    
    public void move(int key)
    {
        int new_y = this.y;
        int new_x = this.x;
        
    	switch (key)  {
    	case KeyEvent.VK_LEFT:
    	    creatureImage = "images/"+originalImage+".png";
    	    new_x = this.getX() - 1;
    	    break;
    	case KeyEvent.VK_RIGHT:
    		creatureImage = "images/"+originalImage+"Right.png";
    		System.out.println(creatureImage);
    	    new_x = this.getX() + 1;
    	    break;
    	case KeyEvent.VK_UP:
	        new_y = this.getY() - 1;
    	    break;
    	case KeyEvent.VK_DOWN:
	        new_y = this.getY() + 1;
    	    break;
    	default:
    	    break;
    	}
    	
    	if (this.canMoveTo(new_x, new_y)) {
    	    this.x = new_x;
    	    this.y = new_y;
    	    
    	    ImageIcon ii = new ImageIcon(creatureImage);
            this.image = ii.getImage();
    	}
    }
	
    public boolean canMoveTo(int x, int y)
    {
    	if (x < 0 || y < 0) {
    	    return false;
    	}
        
        Cell cell = this.map.getCell(y, x);
        return cell.canPenetrate();
	}
	
	public void paint(Point area, Graphics g, JPanel p)
    {
        int x = (int) (this.getX() - area.getX()) * Map.CELL;
        int y = (int) (this.getY() - area.getY()) * Map.CELL;
        
        g.drawImage(this.getImage(), x, y, p);
    }
    
    public String getAlignment() {
    	return Alignment;
    }
    
    public void setAlignment(String align) {
    	Alignment = align;
    }
    
    public Creature getTarget() {
    	return Target;
    }
    
    public void setTarget(Creature newtarget) {
    	Target=newtarget;
    }
	

}
