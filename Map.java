import java.util.ArrayList;
import java.awt.Image;
import javax.swing.ImageIcon;

class Map {
    
    private ArrayList map;
    
    public Map()
    {
        this.map = new ArrayList();
        assert this.map instanceof ArrayList;
    }
    
    public boolean isCellEmpty()
    {
        return true;
    }
    
    public void addCell(Cell cell, int rowindex, int colindex)
    {
        ArrayList row;
        
        try {
            row = (ArrayList) this.map.get(rowindex);
        } catch (IndexOutOfBoundsException e) {
            row = new ArrayList();
            this.map.add(rowindex, row);
        }
        
        row.add(colindex, cell);
    }
}
