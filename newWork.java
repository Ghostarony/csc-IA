import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

public class NewWork {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Add new work");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new workPaneProperties());
        frame.setSize(300, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static class workPaneProperties extends JPanel{
        private FieldPane fieldPane;
        private ButtonPane buttonPane;
    
        public workPaneProperties() {
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.insets = new Insets(4, 4, 4, 4);
    
            add((fieldPane = new FieldPane()), gbc);
            gbc.gridy++;
            add((buttonPane = new ButtonPane()), gbc);
        }
    }
    
    public static class FieldPane extends JPanel{
        private JTextField title, author, publication, finished;
        private JComboBox typelist;

        public FieldPane(){
            setLayout(new GridBagLayout());
            setBorder(new CompoundBorder(new TitledBorder("Add new work"), new EmptyBorder(8, 0, 0, 0)));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 0;
            gbc.anchor = GridBagConstraints.NORTHWEST;

            gbc.fill = GridBagConstraints.HORIZONTAL;
            //title
            add(new JLabel("Title: "), gbc); 
            gbc.gridy++;
            add((title = new JTextField(15)), gbc); 
            gbc.gridy++;

            //author
            add(new JLabel("Author: "), gbc); 
            gbc.gridy++;
            add((author = new JTextField(15)), gbc); 
            gbc.gridy++;

            gbc.fill = GridBagConstraints.NONE;
            //published
            add(new JLabel("Published: "), gbc); 
            gbc.gridy++;
            add((publication = new JTextField(5)), gbc); 
            gbc.gridy++;

            //finished
            add(new JLabel("Finished: "), gbc); 
            gbc.gridy++;
            add((finished = new JTextField(10)), gbc); 
            gbc.gridy++;

            //type
            add(new JLabel("Type: "), gbc);
            gbc.gridy++;
            String[] typeStrings = {"Physical", "E-book", "Web novel", "Fanfic", "Other", "Unspecified"};
            add((typelist = new JComboBox(typeStrings)), gbc);
            gbc.gridy++;

            add(new JLabel(" "), gbc);
            gbc.gridy++;
        }

        //get type box contents
        public String getTitle() {
            return title.getText();
        }
        public String getAuthor() {
            return author.getText();
        }
        public String getPublicationDate() {
            return publication.getText();
        }
        public String getFinishedDate(){
            return finished.getText();
        }
    }

    public static class ButtonPane extends JPanel{
        private JButton confirm, cancel;
    
        public ButtonPane(){
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.CENTER;
            
    
            add((confirm = new JButton("Confirm")), gbc);
            gbc.gridx++;
            add(new JLabel("       "), gbc);
            gbc.gridx++;
            add((cancel = new JButton("Cancel")), gbc);
        }
    }
}
