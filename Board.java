
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

import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;


public class Board extends JPanel implements KeyListener {

    /**
	 * 
	 */
 	private int CELL = 24;
	private int WIDTH = CELL * 25;
    private int HEIGHT = CELL * 14;
	
	private static final long serialVersionUID = 1L;
    private Craft craft;

    private Map map;
    
    private List mapspec;
    
    public Board() 
    {
        this.addKeyListener(this);
        
        this.setFocusable(true);
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        
        try {
            CSVReader reader = new CSVReader(new FileReader("adventure.map"));
            mapspec = reader.readAll();
        } catch (IOException e) {
            System.out.println("File ei löydy");
        }
            
        this.map = new Map();
        this.craft = new Craft();
        
        this.craft.setMap(map);
        
        Dimension dimension = new Dimension(WIDTH, HEIGHT); 
        this.setPreferredSize(dimension);
    }
    
    public void paint(Graphics g)
    {
        super.paint(g);
        
        this.paintMap(g);
        
        g.drawImage(craft.getImage(), craft.getCol() * CELL, craft.getRow() * CELL, this);
        
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }
    
    private void paintMap(Graphics g)
    {
        int cols = WIDTH / CELL;
        int rows = HEIGHT / CELL;
        int x, y;
        
        String mapcell;
        String mapimage;
        Cell cell;
        
        for (int row = 0; row < rows; row++) {
            y = row * CELL;
            String[] rowspec = (String[])mapspec.get(row);
            for (int col = 0; col < cols; col++) {
                x = col * CELL;
                
                cell = new Cell();
                cell.setPenetrate(true);
                
                mapcell = rowspec[col];
                if (mapcell.equals("0")) {
                    mapimage = "images/map_grass.gif";
                } else if (mapcell.equals("1")) {
                    mapimage = "images/map_forest.gif";
                    cell.setPenetrate(false);
                } else if (mapcell.equals("2")) {
                    mapimage = "images/map_mountain.gif";
                    cell.setPenetrate(false);
                } else {
                    mapimage = "images/map_grass.gif";
                }
                
                ImageIcon ii = new ImageIcon(this.getClass().getResource(mapimage));
                Image image = ii.getImage();
                g.drawImage(image, x, y, this);
                
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
