import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import javax.swing.border.*;

public class NewWork {
    public static JFrame frame; //declare frame

    public NewWork() {
        frame = new JFrame("Add new work");
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new workPaneProperties()); //add JPanel workPaneProperties into the frame frame
        frame.setSize(260, 450); //sets a size for frame
        frame.setLocationRelativeTo(null); //makes frame's relative location to the center of the screen
        frame.setVisible(true); //sets frame visible
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
        @SuppressWarnings("rawtypes")
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
            add((comments = new JTextArea(5, 20)), gbc);
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
        public JButton confirm, cancel;
        public String title, name;
    
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
                    String form = determineDateFormat(FieldPane.getFinishedDate().toString()); //determine date format to use for date formatt .,ing
                    if(form == null){
                        //if date inputted is not in a valid format -> pop up info window
                        JOptionPane.showMessageDialog(frame, "Invalid date formatting!\nTry one of the following:\ndd/mm/yyyy\ndd-mm-yyyy", 
                                                "Invalid date!", JOptionPane.INFORMATION_MESSAGE);
                    }
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(form); //formatter for local date
                    LocalDate date = LocalDate.parse(FieldPane.getFinishedDate(), formatter); //parses a date from string input
                    Work tempName = new Work(FieldPane.getTitle(), FieldPane.getAuthor(), FieldPane.getPublicationDate(), 
                    date, FieldPane.getLength(), FieldPane.getType(), FieldPane.getComments());
                    CONTROLLER.workList.add(tempName); //adds the new work into the works queue

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
                    System.out.println("saved..."); //console verification of everything working (temp)
                    frame.dispose(); //exits new work window
                    MyTableModel mod = new MyTableModel();
                    APP.CenterPanel.table.setModel(mod);
                    APP.CenterPanel.setColumns();
                }
            });

            gbc.gridx++;
            add(new JLabel("       "), gbc); //button spacer
            gbc.gridx++;
            add((cancel = new JButton("Cancel")), gbc); //creates a button labeled cancel
            cancel.addActionListener((ActionEvent e) -> frame.dispose()); //action listener on button to close window
        }
        
    }
    //hashmap for all possible ways that date could be formatted
    private static final Map<String, String> DATE_FORMAT_REGEXPS = new HashMap<String, String>() {{
        put("^\\d{8}$", "yyyyMMdd");
        put("^\\d{1,2}-\\d{1,2}-\\d{4}$", "dd-MM-yyyy");
        put("^\\d{4}-\\d{1,2}-\\d{1,2}$", "yyyy-MM-dd");
        put("^\\d{1,2}/\\d{1,2}/\\d{4}$", "dd/MM/yyyy");
        put("^\\d{1,2}/\\d{1,2}/\\d{1,2}$", "dd/MM/yy");
        put("^\\d{4}/\\d{1,2}/\\d{1,2}$", "yyyy/MM/dd");
        put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}$", "dd MMM yyyy");
        put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}$", "dd MMMM yyyy");
        put("^\\d{4}\\d{1,2}\\d{1,2}$", "yyyy-MM-dd");
    }};
    public static String determineDateFormat(String dateString) {
        for (String regexp : DATE_FORMAT_REGEXPS.keySet()) {
            if (dateString.toLowerCase().matches(regexp)) {
                return DATE_FORMAT_REGEXPS.get(regexp); //returns value of the hashmap key that matched input
            }
        }
        return null; // Unknown format.
    }
}
