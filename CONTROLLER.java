import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;

public class CONTROLLER {
    public static List<Work> workList = new ArrayList<>(); //creates a list to store works in
    @SuppressWarnings("unchecked") //suppress unchecked conversions
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()-> new APP()); //new instance of APP / start program

        ArrayList<Work> list = null; //new list with data null
        try (FileInputStream fis = new FileInputStream("worksData");
            ObjectInputStream ois = new ObjectInputStream(fis);) {
            list = (ArrayList<Work>)ois.readObject(); //fill list with all objects from file //warning suppressed
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
        catch (ClassNotFoundException c) {
            System.out.println("Class not found: " + c); //error message to terminal
            c.printStackTrace();
        }

        for(Work w : list){
            workList.add(w);
        }
        workList.sort((o1,o2) -> o1.getFinished().compareTo(o2.getFinished()));
    }
}
