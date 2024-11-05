import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import java.util.List;

public class APP extends JFrame{
    public static JFrame frame;

    public APP(){
        frame = new JFrame("Bookshelf");
        frame.setSize(1024,620); //give the window a set size un-fullscreened
        frame.setLocationRelativeTo(null); //make centered when un-fullscreened
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); //make fullscreen by default
        frame.setVisible(true);

        frame.add(new bookshelfPanelProperties());
        frame.setVisible(true);

        frame.addWindowListener(new java.awt.event.WindowAdapter() { //implement a listener for closing the window
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                try (FileOutputStream fos = new FileOutputStream("worksData");
                    ObjectOutputStream oos = new ObjectOutputStream(fos);) {
                    oos.writeObject(CONTROLLER.workList); //write list into file
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
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            setBackground(new java.awt.Color(191, 211, 193));
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.insets = new Insets(4, 4, 4, 4);

            add(new BookshelfPane1(), gbc);
            gbc.gridy++;
            add(new BookshelfPane2(), gbc);  
            gbc.gridy++;
            add(new BookshelfPane3(), gbc);  
            gbc.gridy++;
            add(new BookshelfPane4(), gbc);
            gbc.gridy++;
            add(new BookshelfPane5(), gbc);
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

            add(newWork = new JButton("Add new work"), gbc);
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
            Image scaledMagIcon = magIcon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH); //make icon into image and rescale image
            ImageIcon fin = new ImageIcon(scaledMagIcon); //make an icon from rescaled image
            add(new JLabel("  "), gbc); //spacer
            gbc.gridx++;
            add(new JLabel(fin), gbc); //add magnifying glass icon

        }
    }

    public static class BookshelfPane3 extends JPanel{
        public static JTable table;
        public JScrollPane scroll;
        public MyTableModel model = new MyTableModel();
        public TableRowSorter<MyTableModel> sorter;
        private JTextField sF = BookshelfPane2.searchField;
        public static String selection;
        public int viewRow;
        public static int modelRow;

        public BookshelfPane3(){
            setLayout(new BorderLayout());

            sorter = new TableRowSorter<MyTableModel>(model);
            table = new JTable(model);
            table.setRowSorter(sorter);
            table.setFillsViewportHeight(true);
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
        private void newFilter() {
            RowFilter<MyTableModel, Object> rf = null; //if current expression doesn't parse, don't update.
            try {
                rf = RowFilter.regexFilter("(?i)^.*" + sF.getText() + ".*");
            } catch (java.util.regex.PatternSyntaxException e) {
                return;
            }
            sorter.setRowFilter(rf);
        }

        class MyTableModel extends AbstractTableModel{
            
            private String[] headers = {"Finished", "Title", "Author", "Publication", "Type", "Length", "Comments"};
            int rows = CONTROLLER.workList.size();
            List<Work> wList = CONTROLLER.workList;
            public MyTableModel(){
            }
            public int getColumnCount() {
                return headers.length;
            }
            public int getRowCount() {
                return wList.size();
            }
            public String getColumnName(int col) {
                return headers[col];
            }
            public Object getValueAt(int row, int col) {
                Work w = wList.get(row);
                switch(col){
                    case 0: return w.getFinished();
                    case 1: return w.getTitle();
                    case 2: return w.getAuthor();
                    case 3: return w.getPublished();
                    case 4: return w.getType();
                    case 5: return w.getLength();
                    case 6: return w.getComments();
                }
                return null;
            }
        }
    }
    class MyTableModel extends AbstractTableModel{
            
            private String[] headers = {"Finished", "Title", "Author", "Publication", "Type", "Length", "Comments"};
            int rows = CONTROLLER.workList.size();
            List<Work> wList = CONTROLLER.workList;
            public MyTableModel(){
            }
            public int getColumnCount() {
                return headers.length;
            }
            public int getRowCount() {
                return wList.size();
            }
            public String getColumnName(int col) {
                return headers[col];
            }
            public Object getValueAt(int row, int col) {
                Work w = wList.get(row);
                switch(col){
                    case 0: return w.getFinished();
                    case 1: return w.getTitle();
                    case 2: return w.getAuthor();
                    case 3: return w.getPublished();
                    case 4: return w.getType();
                    case 5: return w.getLength();
                    case 6: return w.getComments();
                }
                return null;
            }
        }

    public static class BookshelfPane4 extends JPanel{
        public JButton edit, delete;
        public BookshelfPane4(){
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
                    if(BookshelfPane3.selection != null){
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
                    if(BookshelfPane3.selection != null){
                        int choice = JOptionPane.showConfirmDialog(null, "Do you want to delete " + "'" + BookshelfPane3.selection + "'" + " from bookshelf permanently?", 
                                                   "Delete?", JOptionPane.YES_NO_OPTION);
                        if(choice == JOptionPane.YES_OPTION){
                            for(Work w : CONTROLLER.workList){
                                if(w.getTitle().equals(BookshelfPane3.selection)){
                                    CONTROLLER.workList.remove(w);
                                }
                            }
                            MyTableModel mod = new MyTableModel();
                            APP.BookshelfPane3.table.setModel(mod);
                        }
                    } 
                    MyTableModel mod = new MyTableModel();
                    APP.BookshelfPane3.table.setModel(mod);  
                }
            });
        }
        class MyTableModel extends AbstractTableModel{
            
            private String[] headers = {"Finished", "Title", "Author", "Publication", "Type", "Length", "Comments"};
            int rows = CONTROLLER.workList.size();
            List<Work> wList = CONTROLLER.workList;
            public MyTableModel(){
            }
            public int getColumnCount() {
                return headers.length;
            }
            public int getRowCount() {
                return wList.size();
            }
            public String getColumnName(int col) {
                return headers[col];
            }
            public Object getValueAt(int row, int col) {
                Work w = wList.get(row);
                switch(col){
                    case 0: return w.getFinished();
                    case 1: return w.getTitle();
                    case 2: return w.getAuthor();
                    case 3: return w.getPublished();
                    case 4: return w.getType();
                    case 5: return w.getLength();
                    case 6: return w.getComments();
                }
                return null;
            }
        }
    }
    public static class BookshelfPane5 extends JPanel{
        public int wordCount, workCount;
        public JButton edit, delete;

        public BookshelfPane5(){
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
            add(new JLabel("You have read " + workCount + " works altogether since " + CONTROLLER.workList.get(CONTROLLER.workList.size() -1).getFinished()), gbc);
            gbc.gridy++;
            add(new JLabel("In total you have read " + String.format("%,d", wordCount) + " words."), gbc);
            gbc.gridy++;
            add(new JLabel("You have read approximately an amount equaling " 
                            + wordCount/95000 + " adult fiction books."), gbc);

        }
    }
}
