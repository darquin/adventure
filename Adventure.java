
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JFrame;

public class Adventure extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Adventure() {
        Container pane = this.getContentPane();
        pane.add(new Board(), BorderLayout.LINE_START);
        pane.add(new Log(), BorderLayout.PAGE_END);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(Map.WIDTH + 200, Map.HEIGHT + 100);
        this.setTitle("Adventure");
        this.setResizable(false);
        this.setVisible(true);
        this.setBackground(Color.GRAY);
        this.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        new Adventure();
    }
    
    //public void readMap()
    //{
    //    // read file
    //    
    //    for () {
    //        
    //    }
    //    
    //    image = Toolkit.getDefaultToolkit().getImage("images/map_grass_c.gif");
    //    g2d.drawImage(image, 50,50, this);
    //}
    Image image;
    Graphics2D g2d;
}
