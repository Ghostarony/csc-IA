import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
//public class newWorkPane extends JPanel{

    public class FieldPane extends JPanel{
        private JTextField title, author, publication, finished;
        private JComboBox typelist;

        public FieldPane(){
            setLayout(new GridBagLayout());
            setBorder(new CompoundBorder(new TitledBorder("Add new work"), new EmptyBorder(8, 0, 0, 0)));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
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
    
    
//}
