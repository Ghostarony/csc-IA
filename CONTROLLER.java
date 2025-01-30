import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;

public class CONTROLLER {
    //creates a list to store works in
    public static List<Work> workList = new ArrayList<>(); 
    @SuppressWarnings("unchecked") //suppress unchecked conversions
    public static void main(String[] args) {
        //new instance of APP / start program
        SwingUtilities.invokeLater(()-> new APP()); 

        //deserialization try-catch
        try (FileInputStream fis = new FileInputStream("worksData");
            ObjectInputStream ois = new ObjectInputStream(fis);) {
            //fill list with all objects from file //warning suppressed
            workList = (ArrayList<Work>)ois.readObject(); 
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
        catch (ClassNotFoundException c) {
            //error message to terminal
            System.out.println("Class not found: " + c); 
            c.printStackTrace();
        }

        if(!workList.isEmpty()){
            //sorts worklist with newest date first
            workList.sort((o1,o2) -> o2.getFinished().compareTo(o1.getFinished())); 
        }
        
    }
}
