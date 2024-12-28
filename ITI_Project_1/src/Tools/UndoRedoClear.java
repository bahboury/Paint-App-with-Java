package Tools;

import iti_project.DrawFrame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.*;

@SuppressWarnings("serial")
public class UndoRedoClear extends JPanel {
    private final DrawFrame frame;

    public UndoRedoClear(DrawFrame frame) {
        this.frame = frame;
        JPanel panel = new JPanel();

        JButton undoBtn = new JButton("Undo");
        undoBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getPaintPanel().undo();
            }
        });

        JButton redoBtn = new JButton("Redo");
        redoBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getPaintPanel().redo();
            }
        });
        panel.add(undoBtn);
        panel.add(redoBtn);

        JPanel panelClear = new JPanel();
        JButton clear = new JButton("Clear");
        clear.addActionListener(new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getPaintPanel().clear();
            }
        });
        panelClear.add(clear);

        add(panel);
        add(panelClear);
        setLayout(new GridLayout(2, 1));
    }
}
