import java.util.ArrayList;
import java.util.Hashtable;

import java.awt.Image;
import java.awt.Point;
import javax.swing.ImageIcon;

import java.awt.Graphics;

import au.com.bytecode.opencsv.CSVReader;
import java.util.List;

import java.io.FileReader;
import java.io.IOException;

import javax.swing.JPanel;

class Map {
    
    public static final int TOTALCOLS = 80;
    public static final int TOTALROWS = 60;
    
    public static final int CELL = 24;
    
    public static final int COLS = 20;
    public static final int ROWS = 15;
    
    public static final int WIDTH = CELL * COLS;
    public static final int HEIGHT = CELL * ROWS;
    
    private ArrayList<ArrayList> map;
    
    private List spec;
	private Hashtable<Integer, Villain> villains;
    
    private enum Bg {
        GRASS, FOREST, MOUNTAIN
    }
    
    public Map()
    {
        this.map = new ArrayList<ArrayList>();
        assert this.map instanceof ArrayList;
        
        try {
            CSVReader reader = new CSVReader(new FileReader("adventure.map"));
            spec = reader.readAll();
        } catch (IOException e) {
            System.out.println("File ei löydy");
        }
        
        // luodaan mappi tietorakenne
        this.init();
    }
    
    public void init()
    {
        for (int row = 0; row < TOTALROWS; row++) {
            for (int col = 0; col < TOTALCOLS; col++) {
                // uusi ruutu
                Cell cell = this.getNewCell(Bg.GRASS.ordinal(), row, col);
                this.addCell(cell, row, col);
            }
        }

        // käydään läpi jokainen ruutu ja lisätään se tietorakenteeseen
        for (int row = 0; row < TOTALROWS; row++) {
            String[] rowspec = (String[]) this.spec.get(row);
            
            int x = Integer.parseInt(rowspec[0]);
            int y = Integer.parseInt(rowspec[1]);
            int bg = Integer.parseInt(rowspec[2]);
            
            if (x > TOTALCOLS) {
                //throw new Exception("Coordinate not in map");
            }

            // uusi ruutu
            Cell cell = this.getNewCell(bg, x, y);
            this.addCell(cell, x, y);
        }
    }
    
    private Cell getNewCell(int bg, int row, int col)
    {
        Cell cell = new Cell(row, col);
        
        String path;
        
        if (Bg.GRASS.ordinal() == bg) {
            path = "images/map_grass.gif";
            cell.setPenetrate(true);
        } else if (Bg.FOREST.ordinal() == bg) {
            path = "images/map_forest.gif";
            cell.setPenetrate(false);
        } else if (Bg.MOUNTAIN.ordinal() == bg) {
            path = "images/map_mountain.gif";
            cell.setPenetrate(false);
        } else {
            path = "images/map_grass.gif";
            cell.setPenetrate(true);
        }
        
        ImageIcon ii = new ImageIcon(path);
        Image image = ii.getImage();
        cell.setBgImage(image);
        
        return cell;
    }
    
    public void paint(Graphics g, Point start, JPanel p)
    {
        int row = (int) start.getY();
        
        for (int r = 0; r < ROWS; r++) {
            boolean rowinmap = (row >= 0 && row < TOTALROWS);
            
            int col = (int) start.getX();
            
            for (int c = 0; c < COLS; c++) {
                if (rowinmap && (col >= 0 && col < TOTALCOLS)) {
                    this.getCell(row, col).paint(r, c, g, p);
                } else {
                    Cell cell = this.getNewCell(Map.Bg.FOREST.ordinal(), r, c);
                    cell.paint(r, c, g, p);
                }
                
                // seuraava kolumni
                col++;
            }
            
            row++;
        }
    }
    public boolean isCellEmpty()
    {
        return true;
    }
    
    public Cell getCell(int row, int col)
    {
        ArrayList list = (ArrayList) this.map.get(row);
        return (Cell) list.get(col);
    }
    
    public Cell getCell(Point point)
    {
        return this.getCell((int) point.getY(), (int) point.getX());
    }

    public void addCell(Cell cell, int rowindex, int colindex)
    {
        ArrayList<Cell> row;
        
        try {
            row = (ArrayList) this.map.get(rowindex);
        } catch (IndexOutOfBoundsException e) {
            row = new ArrayList<Cell>();
            this.map.add(rowindex, row);
        }
        
        row.add(colindex, cell);
    }
    public void setVillains(Hashtable<Integer, Villain> villains)
    {
    	this.villains = villains;
    }
    public Hashtable<Integer, Villain> getVillains(){
    	return this.villains;
    }
    public void terminateCreature(Creature corpse)
    {
    	villains.remove(corpse.hashCode());
		this.getCell(corpse.getPos()).setCreature(null);
    }
}
