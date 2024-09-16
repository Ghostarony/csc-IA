import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

public class CONTROLLER {
    public static List<Work> workList = new ArrayList<>(); //creates a queue to store works in
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()-> new APP());
    }
}
