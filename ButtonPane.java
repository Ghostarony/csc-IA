import javax.swing.*;
import java.awt.*;

public class ButtonPane extends JPanel{
    private JButton confirm, cancel;

    public ButtonPane(){
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 0, 4);

        gbc.gridwidth = 1;
        gbc.weightx = 0.25;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add((confirm = new JButton("Confirm")), gbc);
        gbc.gridx++;
        add((cancel = new JButton("Cancel")), gbc);
    }
}