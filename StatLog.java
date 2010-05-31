import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.*;

public class StatLog extends JPanel {
	
	private static final long serialVersionUID = 1L;

	static private JTextArea statlogger;

    protected JTextArea statlog;

    public StatLog() 
    {
        this.setFocusable(true);
        this.setBackground(Color.red);
        
        Dimension dimension = new Dimension(800 - Map.WIDTH, Map.HEIGHT); 
        this.setPreferredSize(dimension);

        this.statlog = new JTextArea(22,27);

        JScrollPane scroll = new JScrollPane(
            this.statlog,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        // poistetaan borderi kokonaan
        scroll.setBorder(BorderFactory.createEmptyBorder());

        this.statlog.setEditable(false);
        this.statlog.setBackground(Color.black);
        this.statlog.setForeground(Color.white);
        this.statlog.setFont(new Font("Monaco", Font.PLAIN, 12));

        this.add(scroll);
        StatLog.statlogger = this.statlog;
    }

    static public void write(String msg) {
        StatLog.statlogger.append(msg + "\n");
    }
    static public void clear(){
    	StatLog.statlogger.setText("");
    }
}