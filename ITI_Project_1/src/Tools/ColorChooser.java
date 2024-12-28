package Tools;

import iti_project.DrawFrame;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class ColorChooser extends JPanel implements ActionListener {
    JButton red, green, blue;
    DrawFrame frame;

    public ColorChooser(DrawFrame frame) {
        this.frame = frame;

        red = new JButton("");
        red.setSize(25, 25);
        red.setBackground(Color.red);
        red.addActionListener(this);

        blue = new JButton("");
        blue.setSize(25, 25);
        blue.setBackground(Color.BLUE);
        blue.addActionListener(this);

        green = new JButton("");
        green.setSize(25, 25);
        green.setBackground(Color.GREEN);
        green.addActionListener(this);

        add(red);
        add(green);
        add(blue);
        
        setLayout(new GridLayout(3, 3, 5, 5));
        setBorder(new TitledBorder("Colors"));

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        frame.getPaintPanel().currentColor = btn.getBackground();
    }
}