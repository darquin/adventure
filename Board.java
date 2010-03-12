
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import java.awt.Point;

import javax.swing.JPanel;

public class Board extends JPanel implements KeyListener {

    /**
     * 
     */
    
    private static final long serialVersionUID = 1L;
    private Creature hero;
    //private Creature golem;
    private ArrayList creatures;
    
    private Map map;
    
    private Point area;
    private AI ai;
    
    public Board() 
    {
        this.addKeyListener(this);
        
        this.setFocusable(true);
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.area = new Point(0, 0);
        
        this.map = new Map();
        this.hero = new Creature(4, 4, "creature");
        this.hero.setName("You");
        this.hero.setHero(this.hero);
        this.ai = new AI();
        
        // this.golem = new Creature(3, 3, "golem");
        // this.golem.setMap(this.map);
        // this.golem.setTarget(this.hero);
        
        this.creatures = new ArrayList();
        
        for (int i = 0; i < 5; i++) {
            Creature golem = new Creature(8, 2+i, "golem");
            golem.setName("Golem");
            golem.setAlignment("hostile");
            golem.setMap(this.map);
            golem.setTarget(this.hero);
            this.creatures.add(golem);
        }
        
        this.hero.setMap(map);
        Dimension dimension = new Dimension(Map.WIDTH, Map.HEIGHT); 
        this.setPreferredSize(dimension);
    }
    
    public void paint(Graphics g)
    {
        super.paint(g);
        
        int x = this.hero.getX();
        int y = this.hero.getY();
        int buffer = 3;
        
        int vx = (int) this.area.getX();
        int vy = (int) this.area.getY();
        
        // pitää olla koko mapin sisällä jotta edes testataan kartan siirtymistä
        boolean insidemap = (x > (buffer - 1) && x < (Map.TOTALCOLS - buffer))
                          || (y > (buffer - 1) && y < (Map.TOTALROWS - buffer));

        
        boolean insideview = (x > (vx + buffer - 1) && x < (vx + Map.COLS - buffer))
                           && (y > (vy + buffer - 1) && y < (vy + Map.ROWS - buffer));
        
        if (! insideview && insidemap) {
            int new_view_x = x - 10;
            int new_view_y = y - 7;

            this.area.setLocation(new_view_x, new_view_y);
        }
        
        this.map.paint(g, this.area, this);
        
        this.hero.paint(this.area, g, this);
        
        ListIterator<Creature> iter = this.creatures.listIterator();
        while (iter.hasNext()) {
            iter.next().paint(this.area, g, this);
        }
        //this.golem.paint(this.area, g, this);
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
        
        this.hero.move(key);
        
        //this.ai.act(this.golem);
        
        ListIterator<Creature> iter = this.creatures.listIterator();
        while (iter.hasNext()) {
            this.ai.act(iter.next());
        }
        
        repaint();
    }
}
