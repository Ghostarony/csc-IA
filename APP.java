import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.awt.event.ActionEvent;

public class APP extends JFrame{
    public static JFrame frame;

    public APP(){
        frame = new JFrame("The lovely book tracker <3");
        frame.setSize(1024,600); //give the window a set size un-fullscreened
        frame.setLocationRelativeTo(null); //make centered when un-fullscreened
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); //make fullscreen by default
        frame.setVisible(true);

        //tabs
        JTabbedPane tabbedPane = new JTabbedPane(); //created the tab layout
        tabbedPane.addTab("Bookshelf", new bookshelfPanelProperties()); //add bookshelf tab
        tabbedPane.addTab("Statistics", new statsPanelProperties());
        tabbedPane.addTab("Goals", new goalsPanelProperties());
        frame.add(tabbedPane); //add tabbedpane to the jframe
        tabbedPane.setVisible(true); //set visible

        frame.addWindowListener(new java.awt.event.WindowAdapter() { //implement a listener for closing the window
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                try (FileOutputStream fos = new FileOutputStream("worksData");
                    ObjectOutputStream oos = new ObjectOutputStream(fos);) {
                    oos.writeObject(CONTROLLER.workList); //write list into file
                    }
                    catch (FileNotFoundException e) { //file not found exception
                        System.out.println("File not found : " + e);
                        throw new RuntimeException(e);
                    }
                    catch (IOException ioe) { //io exception
                        System.out.println("Error while writing data : " + ioe);
                        ioe.printStackTrace();
                }

                if (JOptionPane.showConfirmDialog(frame, 
                    "Do you want to exit the program?", "Exit Program", //ask for confirmation before closing the program
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    System.exit(0); //close system if answer is yes
                }
            }
        });
    }

    public static class bookshelfPanelProperties extends JPanel{
    
        public bookshelfPanelProperties() {
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            setBackground(new java.awt.Color(191, 211, 193));
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
            setBackground(new java.awt.Color(191, 211, 193));
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
        private static JTextField searchField;

        public BookshelfPane2(){
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            setBackground(new java.awt.Color(191, 211, 193));
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

        public static String getSearch() {
            return searchField.getText();
        }
    }
    public static class BookshelfPane3 extends JPanel{

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

    public static class statsPanelProperties extends JPanel{
    
        public statsPanelProperties() {
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            setBackground(new java.awt.Color(177, 224, 223));
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.insets = new Insets(4, 4, 4, 4);    
        }
    }

    public static class goalsPanelProperties extends JPanel{
    
        public goalsPanelProperties() {
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            setBackground(new java.awt.Color(239, 199, 194));
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.insets = new Insets(4, 4, 4, 4);    
        }
    }
}
