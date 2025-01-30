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
        Work w = data.get(row);
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
