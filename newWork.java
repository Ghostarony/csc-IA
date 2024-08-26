import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.*;
import ibadts.*;

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
            setLayout(new GridBagLayout()); //creates a new grid bag layout manager
            GridBagConstraints gbc = new GridBagConstraints();
            //sets intial component positions:
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST; //sets a anchor location for components smaller than window they are located
            gbc.fill = GridBagConstraints.BOTH; //sets fill condition to making the component fill the entire area
            gbc.insets = new Insets(4, 4, 4, 4);
    
            add((new FieldPane()), gbc); //adds FieldPane JPanel into the workPaneProperties JPanel -> into frame JFrame
            gbc.gridy++;
            add((new ButtonPane()), gbc); //adds ButtonPane JPanel into -"-
        }
    }
    
    public static class FieldPane extends JPanel{
        public static JTextField title, author, publication, finished, length;
        public static JTextArea comments;
        public static JComboBox typelist;

        public FieldPane(){
            setLayout(new GridBagLayout());
            //sets a border around the FieldPane components:
            setBorder(new CompoundBorder(new TitledBorder("Add new work"), new EmptyBorder(8, 0, 0, 0)));
            GridBagConstraints gbc = new GridBagConstraints();
            //sets intial component positions:
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 0;
            gbc.anchor = GridBagConstraints.NORTHWEST;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            //creates title text component and textfield component
            add(new JLabel("Title: "), gbc); 
            gbc.gridy++;
            add((title = new JTextField(20)), gbc); 
            gbc.gridy++;

            //creates author text component and textfield component
            add(new JLabel("Author: "), gbc); 
            gbc.gridy++;
            add((author = new JTextField(20)), gbc); 
            gbc.gridy++;

            gbc.fill = GridBagConstraints.NONE; //set fill condition to not resize component to fit the window
            //creates publication text component and textfield component
            add(new JLabel("Published: "), gbc); 
            gbc.gridy++;
            add((publication = new JTextField(5)), gbc); 
            gbc.gridy++;

            //creates finishing date text component and textfield component
            add(new JLabel("Finished: "), gbc); 
            gbc.gridy++;
            add((finished = new JTextField(10)), gbc); 
            gbc.gridy++;

            //creates work length text component and textfield component
            add(new JLabel("Length: "), gbc);
            gbc.gridy++;
            add((length = new JTextField(10)), gbc);
            gbc.gridy++;

            //creates work type text component and a JComboBox component including all included work types
            add(new JLabel("Type: "), gbc);
            gbc.gridy++;
            String[] typeStrings = {"Physical book", "E-book", "Web novel", "Fanfic", "Comic", "Other", "Unspecified"};
            add((typelist = new JComboBox(typeStrings)), gbc);
            gbc.gridy++;

            //creates comments text component and text area component
            add(new JLabel("Comments: "), gbc);
            gbc.gridy++;
            add((comments = new JTextArea(5, 20)),gbc);
            comments.setLineWrap(true); //sets line wrapping for the text area to true
            gbc.gridy++;

            add(new JLabel(" "), gbc); //creates some enmpty space for more pleasing layout
            gbc.gridy++;
        }

        //static methods to retrieve contents from each textbox + combobox and text area
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
        public Queue<Work> works = new Queue<>(); //creates a queue to store works in
    
        public ButtonPane(){
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.CENTER;

            add((confirm = new JButton("Confirm")), gbc); //creates a new button loabeled confirm
            confirm.addActionListener(new ActionListener(){ //adds an action listener to the confirm button
                public void actionPerformed(ActionEvent ae){
                    //creates an instance of work that fetches all information from fields of the fieldpane GUI component 
                    Work tempName = new Work(FieldPane.getTitle(), FieldPane.getAuthor(), FieldPane.getPublicationDate(), 
                    FieldPane.getFinishedDate(), FieldPane.getLength(), FieldPane.getType(), FieldPane.getComments());
                    works.enqueue(tempName); //adds the new work into the works queue
                    System.out.println("saved...");
                    System.exit(0); //exits new work window
                }
             });

            gbc.gridx++;
            add(new JLabel("       "), gbc); //button spacer
            gbc.gridx++;
            add((cancel = new JButton("Cancel")), gbc); //creates a button labeled cancel
            cancel.addActionListener((ActionEvent e) -> System.exit(0)); //action listener on button to exit system
        }
    }
}
