package iti_project;

import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
public class ToolBar extends JToolBar {
    // File chooser for selecting files
    private JFileChooser fileChoose;
    // Currently selected file
    private File file;
    private DrawFrame frame;

    public ToolBar(DrawFrame frame) {
        this.frame = frame;

        fileChoose = new JFileChooser(new File("."));
        fileChoose.setFileFilter(new FileNameExtensionFilter("Image", "jpg", "png", "jpeg"));

//        Create a new JToolBar
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenuItem openItem = new JMenuItem("Open");
        fileMenu.add(openItem);
        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show the file chooser dialog
                if (fileChoose.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                    // Get the selected file
                    file = fileChoose.getSelectedFile();
                    System.out.println(file.toString());
                    // Display the selected image
                    displayImage(file);
                }
            }
        });

        JMenuItem saveItem = new JMenuItem("Save");
        fileMenu.add(saveItem);
        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a new file with the same name as the selected file but with a.png extension
                file = new File(fileChoose.getSelectedFile() + ".png");
                // Show the file chooser dialog
                if (fileChoose.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                    saveImage(file);
                }
            }
        });

        setFloatable(false);
    }

    private void saveImage(File f) {
        try {
            JPanel panel = frame.getPaintPanel();
            if (panel != null) {
                BufferedImage bi = makePanel(panel);
                if (bi != null) {
                    if (ImageIO.write(bi, "", f)) {
                        System.out.println("Image saved to " + f.toString());
                    } else {
                        System.out.println("Error saving image: unknown error");
                    }
                } else {
                    System.out.println("Error saving image: panel is null");
                }
            } else {
                System.out.println("Error saving image: paint panel is null");
            }
        } catch (IOException e) {
            System.out.println("Error saving image: " + e.getMessage());
        }
    }

    // Method to create a buffered image of the panel
    private BufferedImage makePanel(JPanel panel) {
        int w = panel.getWidth();
        int h = panel.getHeight();
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        panel.print(g);
        return bi;
    }

    // Method to display the image
    private void displayImage(File file) {
        try {
            // Read the image from the file
            BufferedImage image = ImageIO.read(file);

            // Create an icon from the image
            ImageIcon icon = new ImageIcon(image);

            // Set the icon to the image label
            JLabel imageLabel = new JLabel(icon);
            frame.getScrollPane().setViewportView(imageLabel);

            // Set the horizontal alignment of the image label to center
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
        } catch (IOException e) {
            System.out.println("Error reading image: " + e.getMessage());
        }
    }

    // Method to get the tool bar
    public JToolBar getToolBar() {
        return this;
    }
}
