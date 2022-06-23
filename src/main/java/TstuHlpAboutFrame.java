import javax.swing.*;
import java.awt.*;

public class TstuHlpAboutFrame extends JFrame {
    TstuHlpAboutFrame() {
        this.setTitle("Tstu HighLevelProg About");
        this.setMinimumSize(new Dimension(320, 100));
        this.setMaximumSize(new Dimension(320, 100));
        this.setLocationRelativeTo(null);

        JComponent rootPanel = new JPanel();
        rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.Y_AXIS));

        rootPanel.add(new JLabel("Created by Dmitry Bulaev", JLabel.CENTER));
        rootPanel.add(new JLabel("2022", JLabel.CENTER));

        this.add(rootPanel);

        this.pack();

    }
}
