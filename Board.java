
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import java.awt.Point;

import javax.swing.JPanel;

public class Board extends JPanel implements KeyListener {

    /**
     * 
     */
    
    private static final long serialVersionUID = 1L;
    private Craft craft;
    
    private Map map;
    
    private Point area;
    
    public Board() 
    {
        this.addKeyListener(this);
        
        this.setFocusable(true);
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.area = new Point(-5, -5);
        
        this.map = new Map();
        this.craft = new Craft();
        
        this.craft.setMap(map);
        Dimension dimension = new Dimension(Map.WIDTH, Map.HEIGHT); 
        this.setPreferredSize(dimension);
    }
    
    public void paint(Graphics g)
    {
        super.paint(g);
        
        int x = this.craft.getCol();
        int y = this.craft.getRow();
        int buffer = 3;
        
        int vx = (int) this.area.getX();
        int vy = (int) this.area.getY();
        
        // pitää olla koko mapin sisällä jotta edes testataan kartan siirtymistä
        boolean insidemap  = (x > (buffer - 1) && x < (Map.TOTALCOLS - buffer)) && (y > (buffer - 1) && y < (Map.TOTALROWS - buffer));
            
        boolean insideview = (x > (vx + buffer - 1) && x < (vx + Map.COLS - buffer)) && (y > (vy + buffer - 1) && y < (vy + Map.ROWS - buffer));
        
        if (! insideview && insidemap) {
            int new_view_x = x - 10;
            int new_view_y = y - 7;
            
            this.area.setLocation(new_view_x, new_view_y);
        }
        
        this.map.paint(g, this.area, this);
        
        craft.paint(this.area, g, this);
    }
    
    public void keyTyped(KeyEvent e)
    {
        
    }
    
    public void keyReleased(KeyEvent e)
    {
        
    }
    
    public void keyPressed(KeyEvent e) 
    {
        int key = e.getKeyCode();
        
        this.craft.move(key);
        repaint();
    }
}
