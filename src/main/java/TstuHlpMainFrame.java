import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JOptionPane.showMessageDialog;

public class TstuHlpMainFrame extends JFrame {

    private GearReductionDriveVisualiser reductionDriveVisualiser;
    private DefaultTableModel tableModel;
    private JTable table;


    TstuHlpMainFrame() {
        this.setTitle("Tstu HighLevelProg");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(800, 180));
        this.setPreferredSize(new Dimension(800, 480));
        this.setLocationRelativeTo(null);


        String[] columnNames = {
                "Drive Gear Radius",
                "Driven Gear Radius",
        };

        String[][] data = {
                {"20", "20",},
                {"5", "40",},
                {"40", "20",},
        };


        tableModel = new DefaultTableModel(data, columnNames);
        table = new JTable(tableModel);


        reductionDriveVisualiser = new GearReductionDriveVisualiser();
        reductionDriveVisualiser.setAnimStep(0.01);
        reductionDriveVisualiser.setAnimEnabled(true);

        reductionDriveVisualiser.setDriveGear(new GearVisualiser(30, Color.GREEN, 30));
        reductionDriveVisualiser.setDrivenGear(new GearVisualiser(80, Color.ORANGE, 80));


        this.constructMenu();

        JScrollPane tableScrollPanel = new JScrollPane(table);
        tableScrollPanel.setMaximumSize(new Dimension(200, Short.MAX_VALUE));
        tableScrollPanel.setMinimumSize(new Dimension(200, 0));

        JComponent rootPanel = new JPanel();
        rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.LINE_AXIS));

        rootPanel.add(tableScrollPanel);
        rootPanel.add(reductionDriveVisualiser);

        this.add(rootPanel);

        this.pack();
    }

    private void constructMenu() {


        JMenuItem applyOption = new JMenuItem("Apply");
        applyOption.addActionListener(ev -> applyCurrentRaw());

        JMenuItem aboutOption = new JMenuItem("About");
        aboutOption.addActionListener(ev -> openAboutFrame());

        JMenuItem addOption = new JMenuItem("Add");
        addOption.addActionListener(ev -> addRow());

        JMenuItem removeOption = new JMenuItem("Remove");
        removeOption.addActionListener(ev -> removeCurrent());


        JMenu helpMenu = new JMenu("Help");
        helpMenu.add(aboutOption);


        JMenu tableMenu = new JMenu("Table");
        tableMenu.add(addOption);
        tableMenu.add(removeOption);


        JMenuBar menuBar = new JMenuBar();

        menuBar.add(helpMenu);
        menuBar.add(tableMenu);
        menuBar.add(applyOption);


        this.setJMenuBar(menuBar);
    }

    private void openAboutFrame() {
        new TstuHlpAboutFrame().setVisible(true);
    }

    private void addRow() {
        tableModel.addRow(new Object[]{"10", "10"});
    }

    private void removeCurrent() {
        int selectedRaw = table.getSelectedRow();

        if (selectedRaw >= 0) {
            tableModel.removeRow(selectedRaw);
        } else {
            showMessageDialog(null, "Nothing selected\nSelect table row!");
        }
    }

    private void applyCurrentRaw() {

        int selectedRaw = table.getSelectedRow();

        if (selectedRaw >= 0) {
            double driveRadius = Double.parseDouble((String) table.getValueAt(selectedRaw, 0));
            double drivenRadius = Double.parseDouble((String) table.getValueAt(selectedRaw, 1));

            reductionDriveVisualiser.setDriveGear(new GearVisualiser(driveRadius, Color.GREEN, (int) driveRadius));
            reductionDriveVisualiser.setDrivenGear(new GearVisualiser(drivenRadius, Color.ORANGE, (int) drivenRadius));
        } else {
            showMessageDialog(null, "Nothing selected\nSelect table row!");
        }
    }
}
