
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.Image;
import javax.swing.ImageIcon;

import javax.swing.JPanel;
import javax.swing.Timer;


public class Board extends JPanel implements KeyListener {

    /**
	 * 
	 */
	private int WIDTH = 500;
    private int HEIGHT = 300;
	private int CELL = 20;
	
	private static final long serialVersionUID = 1L;
    private Craft craft;

    private Map map;
    
    public Board() 
    {
        this.addKeyListener(this);
        
        this.setFocusable(true);
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        
        this.map = new Map();
        this.craft = new Craft();
        
        Dimension dimension = new Dimension(WIDTH, HEIGHT); 
        this.setPreferredSize(dimension);
    }
    
    public void paint(Graphics g)
    {
        super.paint(g);
        
        this.paintMap(g);
        
        g.drawImage(craft.getImage(), craft.getX(), craft.getY(), this);
        
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }
    
    private void paintMap(Graphics g)
    {
        int cols = WIDTH / CELL;
        int rows = HEIGHT / CELL;
        int x, y;
        
        for (int row = 0; row < rows; row++) {
            y = row * 20;
            for (int col = 0; col < cols; col++) {
                x = col * 20;
                ImageIcon ii = new ImageIcon(this.getClass().getResource("images/map_grass_c.gif"));
                Image image = ii.getImage();
                g.drawImage(image, x, y, this);
                
                Cell cell = new Cell();
                cell.setPenetrate(true);
                this.map.addCell(cell, row, col);
            }
        }
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
        
        this.craft.move(e);
        repaint();
    }
}
