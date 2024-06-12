import javax.swing.*;
import java.awt.*;

public class workPaneProperties extends JPanel{
    private FieldPane fieldPane;
    private ButtonPane buttonPane;

    public workPaneProperties() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(4, 4, 4, 4);

        add((fieldPane = new FieldPane()), gbc);
        gbc.gridy++;
        add((buttonPane = new ButtonPane()), gbc);
    }
}
