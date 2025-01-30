import java.util.*;
import javax.swing.table.AbstractTableModel;

public class MyTableModel extends AbstractTableModel{
    private List<Work> data;
    private String[] headers = {"Finished", "Title", "Author", "Publication", "Type", "Length", "Comments"};

    public MyTableModel() {
        this.data = CONTROLLER.workList; // Initialize the list
    }

    public int getColumnCount() {
        return headers.length;
    }
    public int getRowCount() {
        return data.size();
    }
    public String getColumnName(int col) {
        return headers[col];
    }

    public void removeRow(int rowIndex) {
        if (data == null) {
            throw new IllegalStateException("Data list is not initialized.");
        }
        if (rowIndex < 0 || rowIndex >= data.size()) {
            throw new IndexOutOfBoundsException("Invalid row index: " + rowIndex);
        }
        else{
            //remove indexed data
            data.remove(rowIndex);
            //terminal prompt
            System.out.println("Row deleted...");
            //update table that data has changed
            fireTableDataChanged();
        }     
    }

    public Object getValueAt(int row, int col) {
        //retrieve the Work object from the data list at the specified row index
        Work w = data.get(row);
        //return the appropriate value based on the column index
        switch(col){
            case 0: return w.getFinished(); //returns finished date
            case 1: return w.getTitle(); //returns work title
            case 2: return w.getAuthor(); //returns work author
            case 3: return w.getPublished(); //returns publishing date
            case 4: return w.getType(); //returns work type
            case 5: return w.getLength(); //returns the length
            case 6: return w.getComments(); //returns comments
        }
        //default return value if an invalid column index is provided
        return null;
    }
}
