import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.*;

public class Log extends JPanel {
    
    static private JTextArea logger;

    protected JTextArea log;

    public Log() 
    {
        this.setFocusable(true);
        this.setBackground(Color.black);
        
        Dimension dimension = new Dimension(800 - Map.WIDTH, 600 - Map.HEIGHT - 24); 
        this.setPreferredSize(dimension);

        this.log = new JTextArea(13, 113);

        JScrollPane scroll = new JScrollPane(
            this.log,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        // poistetaan borderi kokonaan
        scroll.setBorder(BorderFactory.createEmptyBorder());

        this.log.setEditable(false);
        this.log.setBackground(Color.black);
        this.log.setForeground(Color.white);
        this.log.setFont(new Font("Monaco", Font.PLAIN, 12));

        this.add(scroll);
        Log.logger = this.log;
    }

    static public void write(String msg) {
        Log.logger.append(msg + "\n");
    }
}