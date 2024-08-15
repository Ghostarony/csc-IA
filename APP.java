import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

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
        tabbedPane.addTab("Bookshelf", new bookshelfPanelProperties());
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

    public static class bookshelfPanelProperties extends JPanel{
    
        public bookshelfPanelProperties() {
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.insets = new Insets(4, 4, 4, 4);

            add((new BookshelfPane1()), gbc);
            gbc.gridy++;
    
        }
    }
    public static class BookshelfPane1 extends JPanel{
        public static JTextField hold1;
        public static JTextArea hold2;;
        public static JComboBox hold3;

        public BookshelfPane1(){
            setLayout(new GridBagLayout());
            setBorder(new CompoundBorder(new TitledBorder("Add new work"), new EmptyBorder(8, 0, 0, 0)));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 0;
            gbc.anchor = GridBagConstraints.NORTHWEST;
        }
    }
}
