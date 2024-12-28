package Tools;

import iti_project.DrawFrame;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class DrawingShapes extends JPanel {
    private final DrawFrame frame;

        public DrawingShapes(DrawFrame frame) {
            this.frame = frame;
            JButton circleBtn = new JButton("Circle");
            circleBtn.setPreferredSize(new Dimension(100, 100));
            circleBtn.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.getPaintPanel().active_tool = 3;
                }
            });
            add(circleBtn);

            JButton rectangleBtn = new JButton("Rectangle");
            rectangleBtn.setPreferredSize(new Dimension(100, 100));
            rectangleBtn.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.getPaintPanel().active_tool = 2;
                }
            });
            add(rectangleBtn);

            JButton lineBtn = new JButton("Line");
            lineBtn.setPreferredSize(new Dimension(100, 100));
            lineBtn.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.getPaintPanel().active_tool = 1;
                }
            });
            add(lineBtn);

            setBorder(new TitledBorder("Shapes"));
        }
}