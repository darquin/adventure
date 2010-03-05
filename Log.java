import java.awt.Color;
import javax.swing.JPanel;
import java.awt.Dimension;

public class Log extends JPanel {
    
    public Log() 
    {
        this.setFocusable(true);
        this.setBackground(Color.green);
        
        Dimension dimension = new Dimension(Map.WIDTH + 200, 100); 
        this.setPreferredSize(dimension);
    }
}