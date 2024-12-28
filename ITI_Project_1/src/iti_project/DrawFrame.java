package iti_project;

import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class DrawFrame extends JFrame {
    private JPanel contentPanel;
    private JToolBar toolBar, PaintControl;
    private PaintPanel paintPanel;
    private JScrollPane scrollPane;

    int paintPanelWidth = 1300, paintPanelHeight = 800;

    public DrawFrame() {
        // content panel
        contentPanel = new JPanel();
        contentPanel.setLayout(null);

        // toolbar
        toolBar = new ToolBar(this).getToolBar();

        // create a panel for painting
        paintPanel = new PaintPanel(this, paintPanelWidth, paintPanelHeight);

        PaintControl = new PaintControl(this);

        // set a scroll bar
        scrollPane = new JScrollPane();
        scrollPane.setLocation(5, 5);
        scrollPane.setViewportView(paintPanel);
        scrollPane.setSize(paintPanelWidth, paintPanelHeight);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // scroll panel in content panel
        contentPanel.add(scrollPane);
        contentPanel.setBackground(new Color(145, 179, 203));

        add(toolBar, BorderLayout.NORTH);
        add(PaintControl, BorderLayout.EAST);
        add(contentPanel);
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public JToolBar getPaintControl() {
        return this.PaintControl;
    }

    public PaintPanel getPaintPanel() {
        return paintPanel;
    }

}
