import javax.swing.*;
import java.awt.*;

public class APP extends JFrame{
    public static void main(String[] args){
        APP window = new APP();
    }
    public APP(){
        setSize(1024,600);
        setTitle("Bookshelf");
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        //tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        JComponent panel1 = makeTextPanel("Panel #1");
        tabbedPane.addTab("Bookshelf", panel1);
        JComponent panel2 = makeTextPanel("Panel #2");
        tabbedPane.addTab("Statistics", panel2);
        JComponent panel3 = makeTextPanel("Panel #3");
        tabbedPane.addTab("Goals", panel3);
        add(tabbedPane);
    }
    protected JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }
}
