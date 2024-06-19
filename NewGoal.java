import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.*;

public class NewGoal {
    public static void main(String[] args) {
        JFrame goalFrame = new JFrame("Add new goal");
        goalFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        goalFrame.add(new goalPaneProperties());
        goalFrame.setSize(260, 450);
        goalFrame.setLocationRelativeTo(null);
        goalFrame.setVisible(true);
    }

    public static class goalPaneProperties extends JPanel {

        public goalPaneProperties(){
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.insets = new Insets(4, 4, 4, 4);

            add((new GoalPane()), gbc);
            gbc.gridy++;
            add((new ButtonPane()), gbc);
        }
    }

    public static class GoalPane extends JPanel {
        private JRadioButton monthlyButton, yearlyButton;
        static String monthlyString = "Monthly goal";
        static String yearlyString = "Yearly goal";

        public GoalPane(){
            setLayout(new GridBagLayout());
            setBorder(new CompoundBorder(new TitledBorder("Add new goal"), new EmptyBorder(8, 0, 0, 0)));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 0;
            gbc.anchor = GridBagConstraints.NORTHWEST;

            add(monthlyButton = new JRadioButton(monthlyString), gbc);
            monthlyButton.setSelected(true);
            gbc.gridy++;
            add(yearlyButton = new JRadioButton(yearlyString), gbc);
            gbc.gridy++;

            ButtonGroup group = new ButtonGroup();
            group.add(monthlyButton);
            group.add(yearlyButton);
        }
    }
    public static class ButtonPane extends JPanel{
        private JButton confirm, cancel;
    
        public ButtonPane(){
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.CENTER;
            
    
            add((confirm = new JButton("Confirm")), gbc);
            confirm.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ae){
                
                }
             });
            gbc.gridx++;
            add(new JLabel("       "), gbc);
            gbc.gridx++;
            add((cancel = new JButton("Cancel")), gbc);
            cancel.addActionListener((ActionEvent e) -> System.exit(0));
        }
    }
}
