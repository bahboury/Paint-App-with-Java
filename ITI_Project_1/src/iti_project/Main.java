package iti_project;

import java.awt.Frame;
import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        DrawFrame drawframe = new DrawFrame();
        drawframe.setVisible(true);
        
        drawframe.setTitle("Paint Application");
        drawframe.pack();
        
        drawframe.setExtendedState(Frame.MAXIMIZED_BOTH);
        drawframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
