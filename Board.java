
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
    private Hero hero;

    private ArrayList<Villain> creatures;
    
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
        this.hero = new Hero(new Point(4, 4), "creature");
        this.hero.setName("You");
        
        this.creatures = new ArrayList<Villain>();
        
        Villain villain;

        for (int i = 0; i < 5; i++) {
            villain = new Villain(8, 2+i, "golem");
            villain.setName("Golem");
            villain.setAlignment("hostile");
            villain.setMap(this.map);
            villain.setTarget(this.hero);
            this.creatures.add(villain);
        }
        
        Villain temp_cr;
        
        temp_cr = new Villain(7,3, "person");
        this.creatures.add(temp_cr); //Array 5 Dunken pig farmer
        
        temp_cr = new Villain(15,6, "person2");
        this.creatures.add(temp_cr); //Array 6 Pale skinned man
        
        temp_cr = new Villain(15,10, "golem2");
        this.creatures.add(temp_cr); //Array 7 Advanced Iron Golem
        
        temp_cr = new Villain(13,2, "person");
        this.creatures.add(temp_cr); //Array 8 Dirty pig farmer
        
        temp_cr = new Villain(10,3, "golem");
        this.creatures.add(temp_cr); //Array 9 Raging clay golem
        
        this.creatures.get(5).setMap(this.map);
        this.creatures.get(5).setName("Drunken pig farmer");
        this.creatures.get(5).setAlignment("aggressive");
        this.creatures.get(5).setTarget(hero);
        
        this.creatures.get(6).setMap(this.map);
        this.creatures.get(6).setName("Pale Skinned Man");
        this.creatures.get(6).setAlignment("hostile");
        this.creatures.get(6).setTarget(this.creatures.get(7));
        
        this.creatures.get(7).setMap(this.map);
        this.creatures.get(7).setName("Advanced Iron Golem");
        this.creatures.get(7).setAlignment("neutral");
        this.creatures.get(7).setTarget(hero);
        
        
        this.creatures.get(8).setMap(this.map);
        this.creatures.get(8).setName("Dirty Pig Farmer");
        this.creatures.get(8).setAlignment("neutral");
        this.creatures.get(8).setTarget(this.creatures.get(9));
        
        this.creatures.get(9).setMap(this.map);
        this.creatures.get(9).setName("Raging Clay Golem");
        this.creatures.get(9).setAlignment("hostile");
        this.creatures.get(9).setTarget(this.creatures.get(8));
        
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
        
        ListIterator<Villain> iter = this.creatures.listIterator();
        while (iter.hasNext()) {
            iter.next().paint(this.area, g, this);
        }
        //this.golem.paint(this.area, g, this);
    }
    
    public void keyTyped(KeyEvent e) {}
    
    public void keyReleased(KeyEvent e) {}
    
    public void keyPressed(KeyEvent e) 
    {
        int key = e.getKeyCode();
        
        this.hero.act(key);
        
        ListIterator<Villain> iter = this.creatures.listIterator();
        while (iter.hasNext()) {
            iter.next().act();
        }
        
        repaint();
    }
}
