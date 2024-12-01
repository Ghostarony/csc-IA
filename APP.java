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
        frame.setVisible(true);

        //add gui components to frame
        frame.add(new bookshelfPanelProperties());
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
                catch (FileNotFoundException e) { //file not found exception
                    System.out.println("File not found : " + e);
                    throw new RuntimeException(e);
                }
                catch (IOException ioe) { //io exception
                    System.out.println("Error while writing data : " + ioe);
                    ioe.printStackTrace();
                }
                System.exit(0);
            }
        });
    }

    public static class bookshelfPanelProperties extends JPanel{
    
        public bookshelfPanelProperties() {
            setLayout(new BorderLayout());
            setBackground(new java.awt.Color(191, 211, 193));
            
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
            GridBagConstraints gbc = new GridBagConstraints();
            setBackground(new java.awt.Color(191, 211, 193));
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 0;
            gbc.anchor = GridBagConstraints.NORTH;

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
                    //invoke a new instance of newWork window creation
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
            add(new JLabel("Search:  "), gbc); //label search
            gbc.gridx++;
            add(searchField = new JTextField(30), gbc); //textfield for searching
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
        public static String selection;
        public static int viewRow;
        public static int modelRow;

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
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            setColumns();
            add(scroll = new JScrollPane(table), BorderLayout.CENTER);

            sF.getDocument().addDocumentListener(
                new DocumentListener()
                {
                    public void changedUpdate(DocumentEvent e)
                    {
                        newFilter();
                    }
                    public void insertUpdate(DocumentEvent e)
                    {
                        newFilter();
                    }
                    public void removeUpdate(DocumentEvent e)
                    {
                        newFilter();
                    }
                }
            );

            table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent event){
                    viewRow = table.getSelectedRow();
                    modelRow = table.convertRowIndexToModel(viewRow);
                    selection = table.getValueAt(modelRow, 1).toString();
                }
            });
        }
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
            RowFilter<MyTableModel, Object> rf = null; //if current expression doesn't parse, don't update.
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

            add(edit = new JButton("Edit selected work"), gbc);
            edit.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent edit){
                    if(CenterPanel.selection != null){
                        SwingUtilities.invokeLater(() -> new EditWork());
                    } 
                }
            });
            gbc.gridx++;
            add(new JLabel("  "), gbc);
            gbc.gridx++;
            add(delete = new JButton("Delete selected work"), gbc);
            delete.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent del){
                    if(CenterPanel.selection != null){
                        int choice = JOptionPane.showConfirmDialog(null, 
                                    "Do you want to delete " + "'" + CenterPanel.selection + "'" + " from bookshelf permanently?", 
                                    "Delete?", JOptionPane.YES_NO_OPTION);

                        if(choice == JOptionPane.YES_OPTION){
                            int index = CenterPanel.table.getSelectedRow();
                            System.out.println(index);
                            try {
                                ((MyTableModel) CenterPanel.table.getModel()).removeRow(index);
                                CenterPanel.table.clearSelection();
                                System.out.println("Work " + CenterPanel.selection + " was deleted");
                            }
                            catch (Exception ex) {
                                //ex.printStackTrace(); // Print the full stack trace to the console
                                //System.out.println("Deletion error: " + ex.getMessage()); // Log the specific error message
                            }
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

            for(Work w : CONTROLLER.workList){  
                try{
                    wordCount += Integer.parseInt(w.getLength().replace(" ", ""));
                }
                catch (NumberFormatException ne){
                    System.out.println("Uncountable int...");
                }
                workCount++;
            }
            add(new JLabel("You have read " + workCount + " works altogether since " + CONTROLLER.workList.get(CONTROLLER.workList.size() -1).getFinished() + "."), gbc);
            gbc.gridy++;
            add(new JLabel("In total you have read " + String.format("%,d", wordCount) + " words."), gbc);
            gbc.gridy++;
            add(new JLabel("You have read approximately an amount equaling " 
                            + wordCount/95000 + " adult fiction books."), gbc);

        }
    }
}
