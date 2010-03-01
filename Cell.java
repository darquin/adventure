import java.util.ArrayList;

class Cell {
    
    private int row;
    private int col;
    private boolean penetrate;
    
    private ArrayList items;
    
    public void Cell(int row, int col)
    {
        this.row = row;
        this.col = col;
        this.setPenetrate(true);
    }
    
    public void setPenetrate(boolean p)
    {
        this.penetrate = p;
    }
    
    public boolean canPenetrate()
    {
        return penetrate;
    }
}