
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.Timer;


public class Board extends JPanel implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Timer timer;
    private Craft craft;

    public Board() 
    {
        this.addKeyListener(new TAdapter());
        this.setFocusable(true);
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        
        Dimension dimension = new Dimension(500, 300); 
        this.setPreferredSize(dimension);
        
        craft = new Craft();

        timer = new Timer(5, this);
        timer.start();
    }

    public void paint(Graphics g)
    {
        super.paint(g);

        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(craft.getImage(), craft.getX(), craft.getY(), this);

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }


    public void actionPerformed(ActionEvent e) 
    {
        repaint();  
    }


    private class TAdapter extends KeyAdapter {

 /*       public void keyReleased(KeyEvent e) 
        {
        	craft.keyReleased(e);
        }
*/
        public void keyPressed(KeyEvent e) 
        {
            craft.move(craft.keyPressed(e));
        }
    }

}
