import java.util.ArrayList;

import java.awt.Image;
import java.awt.Graphics;

import javax.swing.JPanel;

class Cell {
    
    private int row;
    private int col;
    private boolean penetrate;
    
    private ArrayList items;
    
    private Image bgimage;
    
    public Cell(int row, int col)
    {
        this.row = row;
        this.col = col;
        this.setPenetrate(true);
    }
    
    public void paint(Graphics g, JPanel p)
    {
        g.drawImage(this.bgimage, this.col * 24, this.row * 24, p);
    }
    
    public void setBgImage(Image i)
    {
        this.bgimage = i;
    }
    
    public void setPenetrate(boolean p)
    {
        this.penetrate = p;
    }
    
    public boolean canPenetrate()
    {
        return penetrate;
    }
    
    //public void addCreature(Creature c)
}