
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Adventure extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Adventure() {
        
        this.add(new Board());
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 400);
        this.setLocationRelativeTo(null);
        this.setTitle("Adventure");
        this.setResizable(false);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Adventure();
    }
    
    public void readMap()
    {
        image = Toolkit.getDefaultToolkit().getImage("images/map_grass_c.gif");
        g2d.drawImage(image, 50,50, this);
    }
    Image image;
    Graphics2D g2d;
}
