
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.JFrame;

public class Adventure extends JFrame {

	private static final long serialVersionUID = 1L;

	public Adventure() {
        Container pane = this.getContentPane();
        pane.add(new Board(), BorderLayout.LINE_START);
        pane.add(new Log(), BorderLayout.PAGE_END);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setTitle("Adventure");
        this.setResizable(false);
        this.setVisible(true);
        this.setBackground(Color.GRAY);
        this.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        new Adventure();
    }
}
