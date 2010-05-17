
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.awt.Point;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import au.com.bytecode.opencsv.CSVReader;

public class Board extends JPanel implements KeyListener {

    /**
     * 
     */
    
    private static final long serialVersionUID = 1L;
    private Hero hero;

    private Hashtable<Integer, Villain> villains;
    private Enumeration<Villain> enu;
    
    private Map map;
    
    private Point area;
    
    private int villainCount;
    
    public Board() 
    {
        this.addKeyListener(this);
        
        this.setFocusable(true);
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.area = new Point(0, 0);
        
        this.map = new Map();
        this.hero = new Hero(new Point(4, 4), "creature", 15, 15, 15);
        this.hero.setName("The Hero");
        
        //KORVAA CREATURES-arraylistin
        this.villains = new Hashtable<Integer, Villain>();
        this.map.setVillains(this.villains);
        
        Villain villain;
        List<String[]> villainFile=null;
        
        	try {
				CSVReader reader = new CSVReader(new FileReader("villains.txt"));
				villainFile=reader.readAll();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			
			//Luetaan villainit tiedostosta
			for (int i=0; i<100; i++) {
				
				try {
					//int i=0;
					String[] villainRow = (String[]) villainFile.get(i);
					if(villainRow[0].startsWith("#")){
						continue;
					}
					//i++;
					
					int x = Integer.parseInt(villainRow[2]);
					int y = Integer.parseInt(villainRow[3]);
					
					String name = villainRow[0];
					String image = villainRow[1];
					String alignment = villainRow[4];
					
					int strength = Integer.parseInt(villainRow[5]);
					int endurance = Integer.parseInt(villainRow[5]);
					int agility = Integer.parseInt(villainRow[5]);
					
                    Point pos = new Point(x, y);
					villain = new Villain(pos, image, strength, endurance, agility);
					villain.setName(name);
					villain.setAlignment(alignment);
					villain.setMap(this.map);
					villain.setTarget(this.hero);
					
					villains.put(villain.hashCode(), villain);
					
				}
				
				catch (Exception e) { //Kun creaturet loppuu filusta hyp�t��n pois
					break;
				}
				villainCount++;
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
        
        // pit�� olla koko mapin sis�ll�, jotta edes testataan kartan siirtymist�
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
        
        enu = this.villains.elements();
        while (enu.hasMoreElements()){
        	enu.nextElement().paint(this.area, g, this);
        }
    }
    
    public void keyTyped(KeyEvent e) {}
    
    public void keyReleased(KeyEvent e) {}
    
    public void keyPressed(KeyEvent e) 
    {
        int key = e.getKeyCode();
        
        this.hero.act(key);
        
        if(this.villains.isEmpty()){
        	JOptionPane.showMessageDialog(null, "You have slaughtered everything in the valley. you have become death. Congratulations!");
        	System.exit(-1);
        }
        enu = this.villains.elements();
        while(enu.hasMoreElements())
        {
        	enu.nextElement().act();
        }
        repaint();
    }
}
