import javax.swing.*;
import java.awt.*;

public class Cloud extends JButton {
    private int i = 0;
    private Thread thread = new Thread();
    private Cloud cloud;

    public Cloud() {
        super();
        cloud = this;

        Icon icon = new ImageIcon("Cloud2.png");
        setIcon(icon);
        setFocusable(false);
        setBorder(null);

        thread = new Thread(() -> {
            int y = (int) (Math.random() * 200)+20;
            while (!Thread.interrupted() && Duck.getCnt() < 5) {
                setBounds(i, y, 75, 55);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ea) {
                    return;
                }
                i = i + 2;
            }
        });
        thread.start();

        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
    }
}
