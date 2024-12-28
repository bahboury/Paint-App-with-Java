package Tools;

import iti_project.DrawFrame;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class DrawingTools extends JPanel {

    private final DrawFrame frame;

    public DrawingTools(DrawFrame frame) {
        this.frame = frame;
        JButton pencil = new JButton("Pencil");
        pencil.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getPaintPanel().active_tool = 0;
            }
        });

        JButton eraser = new JButton("Eraser");
        eraser.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getPaintPanel().active_tool = 4;
            }
        });

        JCheckBox dottedCheckBox = new JCheckBox("Dotted");
        dottedCheckBox.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dottedCheckBox.isSelected()) {
                    frame.getPaintPanel().setDotted(true);
                } else {
                    frame.getPaintPanel().setDotted(false);
                }
            }
        });

        // Create a dotted border for the checkbox
        dottedCheckBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                BorderFactory.createDashedBorder(Color.BLACK, 2, 2, 2, true)
        ));

        JCheckBox fillCheckBox = new JCheckBox("Fill");
        fillCheckBox.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fillCheckBox.isSelected()) {
                    frame.getPaintPanel().setFilled(true);
                } else {
                    frame.getPaintPanel().setFilled(false);
                }
            }
        });

        add(pencil);
        add(eraser);
        add(dottedCheckBox);
        add(fillCheckBox);

        setBorder(new TitledBorder("Tools"));
    }
}
