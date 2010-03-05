
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
        
        this.area = new Point(0, 0);
        
        this.map = new Map();
        this.craft = new Craft();
        
        this.craft.setMap(map);
        Dimension dimension = new Dimension(Map.WIDTH, Map.HEIGHT); 
        this.setPreferredSize(dimension);
    }
    
    public void paint(Graphics g)
    {
        super.paint(g);
        
        this.map.paint(g, this.area, this);
        
        g.drawImage(craft.getImage(), craft.getCol() * Map.CELL, craft.getRow() * Map.CELL, this);
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
