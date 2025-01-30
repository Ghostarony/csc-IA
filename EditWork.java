import javax.swing.*;
import javax.swing.border.*;
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


public class EditWork {
    public static JFrame frame; //declare frame

    public EditWork() {
        //define frame
        frame = new JFrame("Edit work in bookshelf");
        //add JPanel workPaneProperties into the frame frame
        frame.add(new editPaneProperties()); 
        //sets a size for frame
        frame.setSize(260, 450); 
        //makes frame's relative location to the center of the screen
        frame.setLocationRelativeTo(null); 
        //sets frame visible
        frame.setVisible(true); 
    }

    public static class editPaneProperties extends JPanel{
    
        public editPaneProperties() {
            //layout manager
            setLayout(new GridBagLayout());
            //background color
            setBackground(new java.awt.Color(233, 242, 234));
            GridBagConstraints gbc = new GridBagConstraints();
            //sets intial component positions
            gbc.gridx = 0;
            gbc.gridy = 0;
            //sets a anchor location for components smaller than window they are located
            gbc.anchor = GridBagConstraints.WEST; 
            //sets fill condition to making the component fill the entire area
            gbc.fill = GridBagConstraints.BOTH; 
            gbc.insets = new Insets(4, 4, 4, 4);
    
            //adds FieldPane and ButtonPane JPanels into the workPaneProperties JPanel -> into frame JFrame
            add((new FieldPane()), gbc);
            gbc.gridy++;
            add((new ButtonPane()), gbc);
        }
    }
    
    public static class FieldPane extends JPanel{
        public static JTextField title, author, publication, finished, length;
        public static JTextArea comments;
        @SuppressWarnings("rawtypes")
        public static JComboBox typelist;
        public static Work setter;

        public FieldPane(){
            setLayout(new GridBagLayout());
            //sets a border around the FieldPane components:
            setBorder(new CompoundBorder(new TitledBorder("Edit work"), new EmptyBorder(8, 0, 0, 0)));
            setBackground(new java.awt.Color(233, 242, 234));
            GridBagConstraints gbc = new GridBagConstraints();
            //sets intial component positions and constraints
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 0;
            gbc.anchor = GridBagConstraints.NORTHWEST;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            //go through worklist
            for(Work w : CONTROLLER.workList){
                //compares selection to all works to find edited work
                if(w.getTitle().equals(APP.CenterPanel.selection)
                && w.getComments().equals(APP.CenterPanel.selectionComments)){
                    setter = w;
                }
            }
            //creates title text component and textfield component
            add(new JLabel("Title: "), gbc); 
            gbc.gridy++;
            add((title = new JTextField(setter.getTitle(), 20)), gbc); 
            gbc.gridy++;

            //creates author text component and textfield component
            add(new JLabel("Author: "), gbc); 
            gbc.gridy++;
            add((author = new JTextField(setter.getAuthor(), 20)), gbc); 
            gbc.gridy++;

            //set fill condition to not resize component to fit the window
            gbc.fill = GridBagConstraints.NONE; 
            //creates publication text component and textfield component
            add(new JLabel("Published: "), gbc); 
            gbc.gridy++;
            add((publication = new JTextField(setter.getPublished(), 5)), gbc); 
            gbc.gridy++;

            //creates finishing date text component and textfield component
            add(new JLabel("Finished: "), gbc); 
            gbc.gridy++;
            add((finished = new JTextField(setter.getFinished().toString(), 10)), gbc); 
            gbc.gridy++;

            //creates work length text component and textfield component
            add(new JLabel("Length: "), gbc);
            gbc.gridy++;
            add((length = new JTextField(setter.getLength(), 10)), gbc);
            gbc.gridy++;

            //creates work type text component and a JComboBox component including all included work types
            add(new JLabel("Type: "), gbc);
            gbc.gridy++;
            //string array of possible work types
            String[] typeStrings = {"Physical book", "E-book", "Web novel", "Fanfic", "Comic", "Other", "Unspecified"};
            add((typelist = new JComboBox(typeStrings)), gbc);
            typelist.setSelectedItem(setter.getType());
            gbc.gridy++;

            //creates comments text component and text area component
            add(new JLabel("Comments: "), gbc);
            gbc.gridy++;
            add((comments = new JTextArea(setter.getComments(), 5, 20)), gbc);
            //sets line wrapping for the text area to true
            comments.setLineWrap(true); 
            gbc.gridy++;
            add(new JLabel(" "), gbc); //spacer
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
    
        public ButtonPane(){
            setLayout(new GridBagLayout());
            setBackground(new java.awt.Color(233, 242, 234));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.CENTER;

            //creates a new button loabeled confirm
            add((confirm = new JButton("Confirm")), gbc); 
            //adds an action listener to the confirm button
            confirm.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ae){
                    //determine date format to use for date formatting
                    String form = determineDateFormat(FieldPane.getFinishedDate().toString()); 
                    if(form == null){
                        //if date inputted is not in a valid format -> pop up info window
                        JOptionPane.showMessageDialog(frame, "Invalid date formatting!\nTry one of the following:\ndd/mm/yyyy\ndd-mm-yyyy", 
                                                "Invalid date!", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else{
                        //formatter for local date
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(form); 
                        //parses a date from string input
                        LocalDate date = LocalDate.parse(FieldPane.getFinishedDate(), formatter); 
                        //update data of the work
                        FieldPane.setter.setTitle(FieldPane.getTitle());
                        FieldPane.setter.setAuthor(FieldPane.getAuthor());
                        FieldPane.setter.setPublished(FieldPane.getPublicationDate());
                        FieldPane.setter.setFinished(date);
                        FieldPane.setter.setLength(FieldPane.getLength());
                        FieldPane.setter.setType(FieldPane.getType());
                        FieldPane.setter.setComments(FieldPane.getComments());
                    }
                    
                    //serialization
                    try (FileOutputStream fos = new FileOutputStream("worksData");
                    ObjectOutputStream oos = new ObjectOutputStream(fos);) {
                    oos.writeObject(CONTROLLER.workList); //write list into file
                    }
                    catch (FileNotFoundException e) { //file not found exception and log to terminal
                        System.out.println("File not found : " + e);
                        throw new RuntimeException(e);
                    }
                    catch (IOException ioe) { //catch exception and log to terminal
                        System.out.println("Error while writing data : " + ioe);
                        ioe.printStackTrace();
                    }
                    System.out.println("saved..."); //console verification of everything working (temp)
                    frame.dispose(); //exits new work window
                    //creates new table model
                    MyTableModel mod = new MyTableModel();
                    //applies tablemodel to the table
                    APP.CenterPanel.table.setModel(mod);
                    //make sure that the column widths stay
                    APP.CenterPanel.setColumns();
                }
            });

            gbc.gridx++;
            add(new JLabel("       "), gbc); //spacer
            gbc.gridx++;
            //creates a button labeled cancel
            add((cancel = new JButton("Cancel")), gbc); 
            //action listener on button to close window
            cancel.addActionListener((ActionEvent e) -> frame.dispose()); 
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
            //compares input date to the hashmap of date formats
            if (dateString.toLowerCase().matches(regexp)) {
                //returns value of the hashmap key that matched input
                return DATE_FORMAT_REGEXPS.get(regexp); 
            }
        }
        return null; // Unknown format
    }
}
