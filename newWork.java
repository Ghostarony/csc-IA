import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.*;

public class NewWork {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Add new work");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new workPaneProperties());
        frame.setSize(260, 450);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static class workPaneProperties extends JPanel{
    
        public workPaneProperties() {
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.insets = new Insets(4, 4, 4, 4);
    
            add((new FieldPane()), gbc);
            gbc.gridy++;
            add((new ButtonPane()), gbc);
        }
    }
    
    public static class FieldPane extends JPanel{
        public static JTextField title, author, publication, finished, length;
        public static JTextArea comments;
        public static JComboBox typelist;

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
            add((title = new JTextField(20)), gbc); 
            gbc.gridy++;

            //author
            add(new JLabel("Author: "), gbc); 
            gbc.gridy++;
            add((author = new JTextField(20)), gbc); 
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

            //length
            add(new JLabel("Length: "), gbc);
            gbc.gridy++;
            add((length = new JTextField(10)), gbc);
            gbc.gridy++;

            //type
            add(new JLabel("Type: "), gbc);
            gbc.gridy++;
            String[] typeStrings = {"Physical book", "E-book", "Web novel", "Fanfic", "Comic", "Other", "Unspecified"};
            add((typelist = new JComboBox(typeStrings)), gbc);
            gbc.gridy++;

            //comments
            add(new JLabel("Comments: "), gbc);
            gbc.gridy++;
            add((comments = new JTextArea(5, 20)),gbc);
            comments.setLineWrap(true);
            gbc.gridy++;

            add(new JLabel(" "), gbc);
            gbc.gridy++;
        }

        //get box contents
        public static String getTitle() {
            return title.getText();
        }
        public static String getAuthor() {
            return author.getText();
        }
        public static String getPublicationDate() {
            return publication.getText();
        }
        public static String getFinishedDate(){
            return finished.getText();
        }
        public static String getLength(){
            return length.getText();
        }
        public static String getType(){
            return typelist.getSelectedItem().toString();
        }
        public static String getComments(){
            return comments.getText();
        }
        
    }

    public static class ButtonPane extends JPanel{
        private JButton confirm, cancel;
        public String title, name;
    
        public ButtonPane(){
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.CENTER;
            
    
            add((confirm = new JButton("Confirm")), gbc);
            //String nameVariable = (title.replace(" ", "")).toLowerCase();
            confirm.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ae){
                    Work tempName = new Work(FieldPane.getTitle(), FieldPane.getAuthor(), FieldPane.getPublicationDate(), 
                    FieldPane.getFinishedDate(), FieldPane.getLength(), FieldPane.getType(), FieldPane.getComments());
                }
             });
            gbc.gridx++;
            add(new JLabel("       "), gbc);
            gbc.gridx++;
            add((cancel = new JButton("Cancel")), gbc);
            cancel.addActionListener((ActionEvent e) -> System.exit(0));
        }
    }
}
