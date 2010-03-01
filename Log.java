import java.awt.Color;
import javax.swing.JPanel;
import java.awt.Dimension;

public class Log extends JPanel {
    
    public Log() 
    {
        this.setFocusable(true);
        this.setBackground(Color.green);
        
        Dimension dimension = new Dimension(600, 200); 
        this.setPreferredSize(dimension);
    }
}