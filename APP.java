import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;

public class APP extends JFrame{
    public static JFrame frame;

    public APP(){
        //create frame
        frame = new JFrame("Bookshelf");
        //give the window a set size un-fullscreened
        frame.setSize(1024,620);
        //make centered when un-fullscreened
        frame.setLocationRelativeTo(null); 
        //make fullscreen by default
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        //add gui components to frame
        frame.add(new bookshelfPanelProperties());
        //set frame visible
        frame.setVisible(true);

        //implement a listener for closing the window
        frame.addWindowListener(new java.awt.event.WindowAdapter() { 
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                //try-catch for serializing the worklist
                try (FileOutputStream fos = new FileOutputStream("worksData");
                    ObjectOutputStream oos = new ObjectOutputStream(fos);) {
                    //write list into file
                    oos.writeObject(CONTROLLER.workList);
                    //terminal confirmation
                    System.out.println("serialized");
                }
                catch (FileNotFoundException e) { //file not found exception and log
                    System.out.println("File not found : " + e);
                    throw new RuntimeException(e);
                }
                catch (IOException ioe) { //io exception and log to terminal
                    System.out.println("Error while writing data : " + ioe);
                    ioe.printStackTrace();
                }
                System.exit(0); //stop running program
            }
        });
    }

    public static class bookshelfPanelProperties extends JPanel{
    
        public bookshelfPanelProperties() {
            //set swing layout
            setLayout(new BorderLayout());
            //background color
            setBackground(new java.awt.Color(191, 211, 193));
            
            //add all panels into one
            add(new NorthPanel(), BorderLayout.NORTH);
            add(new JLabel("              "), BorderLayout.EAST); //spacer
            add(new CenterPanel(), BorderLayout.CENTER);
            add(new SouthPanel(), BorderLayout.SOUTH);
            add(new JLabel("              "), BorderLayout.WEST); //spacer
        }
    }
    public static class NorthPanel extends JPanel{
        public NorthPanel(){
            setLayout(new GridBagLayout());
            setBackground(new java.awt.Color(191, 211, 193));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 0;
            //anchor components to top
            gbc.anchor = GridBagConstraints.NORTH; 

            //set workpanel and searchpanel to northpanel
            add(new JLabel("   "), gbc); //spacer
            gbc.gridy++;
            add(new JLabel("   "), gbc); //spacer
            gbc.gridy++;
            add(new NewWorkPanel(), gbc);
            gbc.gridy++;
            add(new SearchPanel(), gbc);
            gbc.gridy++;
            add(new JLabel("   "), gbc); //spacer

        }
    }
    public static class NewWorkPanel extends JPanel{
        private JButton newWork;

        public NewWorkPanel(){
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            setBackground(new java.awt.Color(191, 211, 193));
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 0;
            gbc.anchor = GridBagConstraints.NORTH;

            //creating a new work button
            add(newWork = new JButton("Add new work"), gbc);
            //action listener for the button
            newWork.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ae){
                    //invoke a new instance of NewWork window creation
                    SwingUtilities.invokeLater(() -> new NewWork());
                }
            });
        }
    }
    public static class SearchPanel extends JPanel{
        private static JTextField searchField;

        public SearchPanel(){
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            setBackground(new java.awt.Color(191, 211, 193));
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 0;
            gbc.anchor = GridBagConstraints.NORTH;

            add(new JLabel("  ")); //spacer
            gbc.gridy++;
            //add and lable searchfield
            add(new JLabel("Search:  "), gbc);
            gbc.gridx++;
            add(searchField = new JTextField(30), gbc);
            gbc.gridx++;
            //create icon from image in folder
            ImageIcon magIcon = new ImageIcon("mag_icon.png"); 
            //make icon into image and rescale image
            Image scaledMagIcon = magIcon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH); 
            //make an icon from rescaled image
            ImageIcon fin = new ImageIcon(scaledMagIcon); 
            add(new JLabel("  "), gbc); //spacer
            gbc.gridx++;
            //add magnifying glass icon
            add(new JLabel(fin), gbc); 

        }
    }

    public static class CenterPanel extends JPanel{
        public static JTable table;
        public JScrollPane scroll;
        public MyTableModel model = new MyTableModel();
        public TableRowSorter<MyTableModel> sorter;
        private JTextField sF = SearchPanel.searchField;
        public static String selection, selectionComments;
        public static int viewRow, modelRow;

        public CenterPanel(){
            setLayout(new BorderLayout());

            //crete table for data display
            table = new JTable(model);
            //create sorter for table
            sorter = new TableRowSorter<MyTableModel>(model);
            //add sorter to table
            table.setRowSorter(sorter);
            //set that table fills height view
            table.setFillsViewportHeight(true);
            //only allow one row to be selected at a time
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            setColumns(); //call setcolumns method
            //add scrollpane to the panel to the center
            add(scroll = new JScrollPane(table), BorderLayout.CENTER);

            //documentlistener for changes in searchfield
            sF.getDocument().addDocumentListener(
                new DocumentListener()
                { //update table filter for all events
                    public void changedUpdate(DocumentEvent e)
                    { //if content is changed
                        newFilter();
                    }
                    public void insertUpdate(DocumentEvent e)
                    { //if text is added
                        newFilter();
                    }
                    public void removeUpdate(DocumentEvent e)
                    { //if text is deleted
                        newFilter();
                    }
                }
            );

            //adding a listener for the selected row and column of the table
            table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent event){
                    viewRow = table.getSelectedRow();
                    modelRow = table.convertRowIndexToModel(viewRow);
                    selection = table.getValueAt(modelRow, 1).toString();
                    selectionComments = table.getValueAt(modelRow, 6).toString();
                }
            });
        }

        //method for setting width boundaries for table columns
        public static void setColumns(){
            table.getColumnModel().getColumn(0).setMinWidth(90);
            table.getColumnModel().getColumn(0).setMaxWidth(90);
            table.getColumnModel().getColumn(2).setMinWidth(150);
            table.getColumnModel().getColumn(2).setMaxWidth(200);
            table.getColumnModel().getColumn(3).setMinWidth(70);
            table.getColumnModel().getColumn(3).setMaxWidth(70);
            table.getColumnModel().getColumn(4).setMinWidth(100);
            table.getColumnModel().getColumn(4).setMaxWidth(300);
            table.getColumnModel().getColumn(5).setMinWidth(70);
            table.getColumnModel().getColumn(5).setMaxWidth(70);
        }
        //search filter
        private void newFilter() {
            //if current expression doesn't parse, don't update
            RowFilter<MyTableModel, Object> rf = null; 
            try {
                //regex filter: not case sensitive and "includes"-type search
                rf = RowFilter.regexFilter("(?i)^.*" + sF.getText() + ".*"); 
            } catch (java.util.regex.PatternSyntaxException e) { //error catch
                return;
            }
            sorter.setRowFilter(rf); //display only works mathcing search
        }

        
    }
        
    public static class SouthPanel extends JPanel{
        public SouthPanel(){
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            setBackground(new java.awt.Color(191, 211, 193));
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 0;
            gbc.anchor = GridBagConstraints.NORTH;

            //add editanddelete and statisticpanel to southpanel
            add(new JLabel("   "), gbc); //spacer
            gbc.gridy++;
            add(new EditAndDeletePanel(), gbc);
            gbc.gridy++;
            add(new StatisticPanel(), gbc);
            gbc.gridy++;
            add(new JLabel("   "), gbc); //spacer
            gbc.gridy++;
            add(new JLabel("   "), gbc); //spacer
        }
    }

    public static class EditAndDeletePanel extends JPanel{
        public JButton edit, delete;
        public EditAndDeletePanel(){
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            setBackground(new java.awt.Color(191, 211, 193));
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 0;
            gbc.anchor = GridBagConstraints.NORTH;

            //create an edit work button
            add(edit = new JButton("Edit selected work"), gbc);
            //add an actionlistener to the button
            edit.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent edit){
                    //as long as a work is selected...
                    if(CenterPanel.selection != null){
                        //invoke an editwork window
                        SwingUtilities.invokeLater(() -> new EditWork());
                    } 
                }
            });
            gbc.gridx++;
            add(new JLabel("  "), gbc); //spacer
            gbc.gridx++;
            //add a delete work button
            add(delete = new JButton("Delete selected work"), gbc);
            //add an action listener to the button
            delete.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent del){
                    //as long as selection isn't null
                    if(CenterPanel.selection != null){
                        //show a confimation prompt window with yes/no options 
                        int choice = JOptionPane.showConfirmDialog(null, 
                                    "Do you want to delete " + "'" + CenterPanel.selection + "'" + " from bookshelf permanently?", 
                                    "Delete?", JOptionPane.YES_NO_OPTION);
                        //if chosen option is yes
                        if(choice == JOptionPane.YES_OPTION){
                            //get selected row index
                            int index = CenterPanel.table.getSelectedRow();
                            try {
                                //call delete row as a table model method
                                ((MyTableModel) CenterPanel.table.getModel()).removeRow(index);
                                //clear selection
                                CenterPanel.table.clearSelection();
                                //log to terminal that deletion was a success
                                System.out.println("Work " + CenterPanel.selection + " was deleted");
                            }
                            catch (Exception ex) { //terminal error notice
                                System.out.println("Deletion error...");
                            }
                            //update columns
                            CenterPanel.setColumns();
                        }
                    }   
                }
            });
        }

        
    }
    public static class StatisticPanel extends JPanel{
        private int wordCount, workCount;

        public StatisticPanel(){
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            setBackground(new java.awt.Color(191, 211, 193));
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 0;
            gbc.anchor = GridBagConstraints.NORTH;

            //go through worklist
            for(Work w : CONTROLLER.workList){  
                try{
                    //count the total wordcount from all works
                    wordCount += Integer.parseInt(w.getLength().replace(" ", ""));
                }
                catch (NumberFormatException ne){ //exception log
                    System.out.println("Uncountable int...");
                }
                //count the number of works as well
                workCount++;
            }
            //label of works read
            add(new JLabel("You have read " + workCount + " works altogether since " + CONTROLLER.workList.get(CONTROLLER.workList.size() -1).getFinished() + "."), gbc);
            gbc.gridy++;
            //label of total words left
            add(new JLabel("In total you have read " + String.format("%,d", wordCount) + " words."), gbc);
            gbc.gridy++;
            //total words divided by average novel length
            add(new JLabel("You have read approximately an amount equaling " 
                            + wordCount/95000 + " adult fiction books."), gbc);

        }
    }
}
