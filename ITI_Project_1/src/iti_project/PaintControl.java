package iti_project;

import Tools.*;
import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class PaintControl extends JToolBar {
    private final DrawFrame frame;

    PaintControl(DrawFrame frame) {
        this.frame = frame;

        // create a color chooser
        ColorChooser colorChooser = new ColorChooser(frame);
        DrawingTools drawingTools = new DrawingTools(frame);
        DrawingShapes drawingShapes = new DrawingShapes(frame);
        UndoRedoClear undoRedoClear = new UndoRedoClear(frame);

        add(undoRedoClear);
        add(drawingTools);
        add(drawingShapes);
        add(colorChooser);
        add(new Separator());

        setBackground(Color.WHITE);
        setLayout(new GridLayout(6, 1));
        setFloatable(false);
    }
}
