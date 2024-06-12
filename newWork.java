import javax.swing.JFrame;

public class newWork {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Add new work");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new FieldPane());
        frame.setSize(300, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
