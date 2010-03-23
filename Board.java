
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
    private ArrayList<Creature> creatures;
    
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
        
        Creature temp_cr;
        
        temp_cr = new Creature (7,3, "person");
        this.creatures.add(temp_cr); //Array 5 Dunken pig farmer
        
        temp_cr = new Creature (15,6, "person2");
        this.creatures.add(temp_cr); //Array 6 Pale skinned man
        
        temp_cr = new Creature (15,10, "golem2");
        this.creatures.add(temp_cr); //Array 7 Advanced Iron Golem
        
        temp_cr = new Creature (13,2, "person");
        this.creatures.add(temp_cr); //Array 8 Dirty pig farmer
        
        temp_cr = new Creature (10,3, "golem");
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
        
        System.out.println("* turn end *");
        repaint();
    }
}
