

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Point;
import java.awt.Graphics;

import javax.swing.ImageIcon;

import javax.swing.JPanel;

public class Craft {

    private String craft = "images/wizard.png";
    
    private Map map;
    
    // private int dx;
    // private int dy;
    private int row;
    private int col;
    private Image image;
    private char moveDirection;

    public Craft() 
    {
        ImageIcon ii = new ImageIcon(this.getClass().getResource(craft));
        image = ii.getImage();
        this.row = 0;
        this.col = 0;
    }
    
    public void setMap(Map map)
    {
        this.map = map;
    }
    
    public int getRow() 
    {
        return this.row;
    }

    public int getCol() 
    {
        return this.col;
    }
    
    public Image getImage() 
    {
        return image;
    }
    
    public void paint(Point area, Graphics g, JPanel p)
    {
        int x = (int) (this.getCol() - area.getX()) * Map.CELL;
        int y = (int) (this.getRow() - area.getY()) * Map.CELL;
        
        g.drawImage(this.getImage(), x, y, p);
    }
    
    public void move(int key)
    {
        int newrow = this.row;
        int newcol = this.col;
        
    	switch (key)  {
    	case KeyEvent.VK_LEFT:
    	    craft = "images/wizard.png";
    	    newcol = this.getCol() - 1;
    	    break;
    	case KeyEvent.VK_RIGHT:
    	    craft = "images/wizardRight.png";
    	    newcol = this.getCol() + 1;
    	    break;
    	case KeyEvent.VK_UP:
	        newrow = this.getRow() - 1;
    	    break;
    	case KeyEvent.VK_DOWN:
	        newrow = this.getRow() + 1;
    	    break;
    	default:
    	    break;
    	}
    	
    	if (this.canMoveTo(newcol, newrow)) {
    	    this.col = newcol;
    	    this.row = newrow;
    	    
    	    ImageIcon ii = new ImageIcon(this.getClass().getResource(craft));
            this.image = ii.getImage();
    	}
    }
	
    private boolean canMoveTo(int col, int row)
    {
        if (! ((col >= 0 && col < Map.TOTALCOLS) && (row >= 0 && row < Map.TOTALROWS))) {
            return false;
        }
        
        Cell cell = this.map.getCell(row, col);
        return cell.canPenetrate();
    }
}
