import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class APP extends JFrame{
    public static void main(String[] args){
        SwingUtilities.invokeLater(()-> new APP());
    }
    public APP(){
        setSize(1024,600); //give the window a set size un-fullscreened
        setTitle("The lovely book tracker <3"); //name the window
        setLocationRelativeTo(null); //make centered un-fullscreened
        setExtendedState(JFrame.MAXIMIZED_BOTH); //make fullscreen by default
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //set to exit system when closed
        setVisible(true);

        //tabs
        JTabbedPane tabbedPane = new JTabbedPane(); //created the tab layout
        tabbedPane.addTab("Bookshelf", new bookshelfPanelProperties()); //add bookshelf tab
        JComponent panel2 = makeTextPanel("Panel #2");
        tabbedPane.addTab("Statistics", panel2);
        JComponent panel3 = makeTextPanel("Panel #3");
        tabbedPane.addTab("Goals", panel3);
        add(tabbedPane); //add tabbedpane to the jframe
        tabbedPane.setVisible(true); //set visible
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
            setBackground(new java.awt.Color(103, 181, 124));
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.insets = new Insets(4, 4, 4, 4);

            add((new BookshelfPane1()), gbc);
            gbc.gridy++;
            add ((new BookshelfPane2()), gbc);    
        }
    }
    public static class BookshelfPane1 extends JPanel{
        private JButton newWork;

        public BookshelfPane1(){
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            setBackground(new java.awt.Color(103, 181, 124));
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 0;
            gbc.anchor = GridBagConstraints.NORTH;

            add((newWork = new JButton("Add new work")), gbc);
            newWork.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ae){
                    SwingUtilities.invokeLater(() -> new NewWork());
                }
            });
        }
    }
    public static class BookshelfPane2 extends JPanel{
        private JTextField searchField;

        public BookshelfPane2(){
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            setBackground(new java.awt.Color(103, 181, 124));
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 0;
            gbc.anchor = GridBagConstraints.NORTH;

            add(new JLabel("  ")); //spacer
            gbc.gridy++;
            add(new JLabel("Search:  "), gbc); //label search
            gbc.gridx++;
            add(searchField = new JTextField(30), gbc); //textfield for searching
            gbc.gridx++;
            ImageIcon magIcon = new ImageIcon("mag_icon.png"); //create icon from image in folder
            Image scaledMagIcon = magIcon.getImage().getScaledInstance(11, 11, Image.SCALE_SMOOTH); //make icon into image and rescale image
            ImageIcon fin = new ImageIcon(scaledMagIcon); //make an icon from rescaled image
            add(new JButton(fin), gbc); //button with magnifying glass icon
        }
    }
    public static class BookshelfPane3 extends JPanel{
        private JButton newWork;

        public BookshelfPane3(){
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            setBackground(new java.awt.Color(103, 181, 124));
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 0;
            gbc.anchor = GridBagConstraints.NORTH;

            //list work in a table
        }
    }
}
