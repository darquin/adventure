
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
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
    
    private Hashtable<Integer, Villain> villains;
    private Enumeration<Villain> enu;
    
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
        this.hero = new Hero(new Point(4, 4), "creature", 20, 20, 20);
        this.hero.setName("You");
        
        //this.creatures = new ArrayList<Villain>();
        this.villains = new Hashtable<Integer, Villain>();
        Villain villain;

        for (int i = 0; i < 5; i++) {
            villain = new Villain(8, 2+i, "golem", 10, 10, 10);
            villain.setName("Golem");
            villain.setAlignment("hostile");
            villain.setMap(this.map);
            villain.setTarget(this.hero);
            villain.setAttack();
            villain.setDefense();
            villain.setHealth();
           // this.creatures.add(villain);
            villains.put(villain.hashCode(), villain);
        }
        
        Villain temp_cr;
        
        temp_cr = new Villain(7,3, "person", 10, 10, 10);
        temp_cr.setAttack();
        temp_cr.setDefense();
        temp_cr.setHealth();
        int personCode = temp_cr.hashCode();
        //this.creatures.add(temp_cr); //Array 5 Drunken pig farmer
        this.villains.put(temp_cr.hashCode(), temp_cr);
        
        
        temp_cr = new Villain(15,6, "person2", 10, 10, 10);
        temp_cr.setAttack();
        temp_cr.setDefense();
        temp_cr.setHealth();
        int person2Code = temp_cr.hashCode();
        //this.creatures.add(temp_cr); //Array 6 Pale skinned man
        this.villains.put(temp_cr.hashCode(), temp_cr);
        
        
        temp_cr = new Villain(15,10, "golem2", 10, 10, 10);
        temp_cr.setAttack();
        temp_cr.setDefense();
        temp_cr.setHealth();
        int golem2Code = temp_cr.hashCode();
        //this.creatures.add(temp_cr); //Array 7 Advanced Iron Golem
        this.villains.put(temp_cr.hashCode(), temp_cr);
        
        temp_cr = new Villain(13,2, "person3", 10, 10, 10);
        temp_cr.setAttack();
        temp_cr.setDefense();
        temp_cr.setHealth();
        int person3Code = temp_cr.hashCode();
        //this.creatures.add(temp_cr); //Array 8 Dirty pig farmer
        this.villains.put(temp_cr.hashCode(), temp_cr);
        
        temp_cr = new Villain(10,3, "golem", 10, 10, 10);
        temp_cr.setAttack();
        temp_cr.setDefense();
        temp_cr.setHealth();
        int golemCode = temp_cr.hashCode();
        //this.creatures.add(temp_cr); //Array 9 Raging clay golem
        this.villains.put(temp_cr.hashCode(), temp_cr);
        
        this.villains.get(personCode).setMap(this.map);
        this.villains.get(personCode).setName("Drunken pig farmer");
        this.villains.get(personCode).setAlignment("aggressive");
        this.villains.get(personCode).setTarget(hero);
        
        this.villains.get(person2Code).setMap(this.map);
        this.villains.get(person2Code).setName("Pale Skinned Man");
        this.villains.get(person2Code).setAlignment("hostile");
        this.villains.get(person2Code).setTarget(this.villains.get(golem2Code));
        
        this.villains.get(golem2Code).setMap(this.map);
        this.villains.get(golem2Code).setName("Advanced Iron Golem");
        this.villains.get(golem2Code).setAlignment("neutral");
        this.villains.get(golem2Code).setTarget(hero);
        
        
        this.villains.get(person3Code).setMap(this.map);
        this.villains.get(person3Code).setName("Dirty Pig Farmer");
        this.villains.get(person3Code).setAlignment("neutral");
        this.villains.get(person3Code).setTarget(this.villains.get(golemCode));
        
        this.villains.get(golemCode).setMap(this.map);
        this.villains.get(golemCode).setName("Raging Clay Golem");
        this.villains.get(golemCode).setAlignment("hostile");
        this.villains.get(golemCode).setTarget(this.villains.get(person3Code));
        
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
        
        // pitää olla koko mapin sisällä, jotta edes testataan kartan siirtymistä
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
        
        /*ListIterator<Villain> iter = this.creatures.listIterator();
        while (iter.hasNext()) {
            iter.next().paint(this.area, g, this);
        }*/
        enu = this.villains.elements();
        while(enu.hasMoreElements()){
        	enu.nextElement().paint(this.area, g, this);
        }
        //this.golem.paint(this.area, g, this);
    }
    
    public void keyTyped(KeyEvent e) {}
    
    public void keyReleased(KeyEvent e) {}
    
    public void keyPressed(KeyEvent e) 
    {
        int key = e.getKeyCode();
        
        this.hero.act(key);
        
        /*ListIterator<Villain> iter = this.creatures.listIterator();
        while (iter.hasNext()) {
            iter.next().act();
        }*/
        enu = this.villains.elements();
        while(enu.hasMoreElements()){
        	
        	enu.nextElement().act();
        }
        
        repaint();
    }

}
