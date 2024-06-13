import javax.swing.*;
import java.awt.*;

public class ButtonPane extends JPanel{
    private JButton confirm, cancel;

    public ButtonPane(){
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        

        add((confirm = new JButton("Confirm")), gbc);
        gbc.gridx++;
        add((cancel = new JButton("Cancel")), gbc);
    }
}