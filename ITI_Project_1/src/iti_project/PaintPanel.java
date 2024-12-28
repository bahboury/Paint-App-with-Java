package iti_project;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Stack;
import javax.swing.*;

@SuppressWarnings("serial")
public class PaintPanel extends JPanel implements MouseListener, MouseMotionListener {

    // drawing tools
    private final int PENCIL_TOOL = 0;
    private final int LINE_TOOL = 1;
    private final int RECTANGLE_TOOL = 2;
    private final int CIRCLE_TOOL = 3;
    private final int ERASER_TOOL = 4;

    // shapes
    private final int LINE = 1;
    private final int RECTANGLE = 2;
    private final int CIRCLE = 3;

    // graphics tool
    private Graphics2D graphics2D;

    // active tool
    public int active_tool = 0;
    private int grouped;

    Stack<Shape> shapes, removed, preview;

    private BufferedImage canvas;

    BasicStroke stroke = new BasicStroke((float) 1);

    int x1, y1, x2, y2, paintPanelWidth, paintPanelHeight;

    public Color currentColor = Color.black;
    Color eraserColor = Color.white;

    private boolean dragged = false;
    private boolean isDotted = false;
    private boolean isFilled = false;

    private DrawFrame frame;

    PaintPanel() {
        setBackground(Color.WHITE);
        shapes = new Stack<>();
    }

    PaintPanel(DrawFrame frame, int paintPanelWidth, int paintPanelHeight) {
        this.frame = frame;

        grouped = 1;

        shapes = new Stack<>();
        removed = new Stack<>();
        preview = new Stack<>();

        setLayout(null);
        setBackground(Color.WHITE);

        addMouseListener(this);
        addMouseMotionListener(this);

        this.paintPanelWidth = paintPanelWidth;
        this.paintPanelHeight = paintPanelHeight;
    }

    public void setDotted(boolean isDotted) {
        this.isDotted = isDotted;
    }

    public void setFilled(boolean isFilled) {
        this.isFilled = isFilled;
    }

    public boolean isFilled() {
        return isFilled;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (canvas == null) {
            canvas = new BufferedImage(paintPanelWidth, paintPanelHeight, BufferedImage.TYPE_INT_ARGB);
            graphics2D = canvas.createGraphics();
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            clear();
        }
        graphics2D.drawImage(canvas, 0, 0, null);

        Graphics2D g2d = (Graphics2D) g;

        for (Shape s : shapes) {
            g2d.setColor(s.getColor());
            g2d.setStroke(s.getStroke());

            if (isDotted) {
                float[] dashPattern = {5, 5};
                g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 1, dashPattern, 0));
            } else {
                g2d.setStroke(new BasicStroke(2));
            }
            switch (s.getShape()) {
                case LINE:
                    g2d.drawLine(s.getx1(), s.gety1(), s.getx2(), s.gety2());
                    break;
                case RECTANGLE:
                    if (isFilled) {
                        g2d.fillRect(s.getx1(), s.gety1(), s.getx2(), s.gety2());
                    } else {
                        g2d.drawRect(s.getx1(), s.gety1(), s.getx2(), s.gety2());
                    }
                    break;
                case CIRCLE:
                    if (isFilled) {
                        g2d.fillOval(s.getx1(), s.gety1(), s.getx2(), s.gety2());
                    } else {
                        g2d.drawOval(s.getx1(), s.gety1(), s.getx2(), s.gety2());
                    }
                    break;
                case ERASER_TOOL:
                    g2d.drawLine(s.getx1(), s.gety1(), s.getx2(), s.gety2());
                    break;
                default:
                    break;
            }

        }

        if (preview.size() > 0) {
            Shape s = preview.pop();
            g2d.setColor(s.getColor());
            g2d.setStroke(s.getStroke());

            switch (s.getShape()) {
                case RECTANGLE:
                    g2d.drawRect(s.getx1(), s.gety1(), s.getx2(), s.gety2());
                    break;
                case CIRCLE:
                    g2d.drawOval(s.getx1(), s.gety1(), s.getx2(), s.gety2());
                    break;
                case LINE:
                    g2d.drawLine(s.getx1(), s.gety1(), s.getx2(), s.gety2());
                    break;
                default:
                    break;
            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        x1 = e.getX();
        y1 = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        grouped++;
        if (active_tool == RECTANGLE_TOOL && dragged) {
            if (x1 < x2 && y1 < y2) {
                shapes.push(new Shape(x1, y1, x2 - x1, y2 - y1, currentColor, stroke, 2));
            } else if (x2 < x1 && y1 < y2) {
                shapes.push(new Shape(x2, y1, x1 - x2, y2 - y1, currentColor, stroke, 2));
            } else if (x1 < x2 && y2 < y1) {
                shapes.push(new Shape(x1, y2, x2 - x1, y1 - y2, currentColor, stroke, 2));
            } else if (x2 < x1 && y2 < y1) {
                shapes.push(new Shape(x2, y2, x1 - x2, y1 - y2, currentColor, stroke, 2));
            }
            repaint();
        } else if (active_tool == CIRCLE_TOOL && dragged) {
            if (x1 < x2 && y1 < y2) {
                shapes.push(new Shape(x1, y1, x2 - x1, y2 - y1, currentColor, stroke, 3));
            } else if (x2 < x1 && y1 < y2) {
                shapes.push(new Shape(x2, y1, x1 - x2, y2 - y1, currentColor, stroke, 3));
            } else if (x1 < x2 && y2 < y1) {
                shapes.push(new Shape(x1, y2, x2 - x1, y1 - y2, currentColor, stroke, 3));
            } else if (x2 < x1 && y2 < y1) {
                shapes.push(new Shape(x2, y2, x1 - x2, y1 - y2, currentColor, stroke, 3));
            }
            repaint();
        } else if (active_tool == LINE_TOOL && dragged) {
            shapes.push(new Shape(x1, y1, x2, y2, currentColor, stroke, 1));
        }
        dragged = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        dragged = true;
        x2 = e.getX();
        y2 = e.getY();
        switch (active_tool) {
            case PENCIL_TOOL:
                shapes.push(new Shape(x1, y1, x2, y2, currentColor, stroke, 1, grouped));
                repaint();
                x1 = x2;
                y1 = y2;
                break;
            case RECTANGLE_TOOL:
                if (x1 < x2 && y1 < y2) {
                    preview.push(new Shape(x1, y1, x2 - x1, y2 - y1, currentColor, stroke, 2));
                } else if (x2 < x1 && y1 < y2) {
                    preview.push(new Shape(x2, y1, x1 - x2, y2 - y1, currentColor, stroke, 2));
                } else if (x1 < x2 && y2 < y1) {
                    preview.push(new Shape(x1, y2, x2 - x1, y1 - y2, currentColor, stroke, 2));
                } else if (x2 < x1 && y2 < y1) {
                    preview.push(new Shape(x2, y2, x1 - x2, y1 - y2, currentColor, stroke, 2));
                }
                repaint();
                break;
            case CIRCLE_TOOL:
                if (x1 < x2 && y1 < y2) {
                    preview.push(new Shape(x1, y1, x2 - x1, y2 - y1, currentColor, stroke, 3));
                } else if (x2 < x1 && y1 < y2) {
                    preview.push(new Shape(x2, y1, x1 - x2, y2 - y1, currentColor, stroke, 3));
                } else if (x1 < x2 && y2 < y1) {
                    preview.push(new Shape(x1, y2, x2 - x1, y1 - y2, currentColor, stroke, 3));
                } else if (x2 < x1 && y2 < y1) {
                    preview.push(new Shape(x2, y2, x1 - x2, y1 - y2, currentColor, stroke, 3));
                }
                repaint();
                break;
            case LINE_TOOL:
                preview.push(new Shape(x1, y1, x2, y2, currentColor, stroke, 1));
                repaint();
                break;
            case ERASER_TOOL:
                shapes.push(new Shape(x1, y1, x2, y2, eraserColor, stroke, 4, grouped));
                repaint();
                x1 = x2;
                y1 = y2;
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    public void undo() {
        if (shapes.size() > 0 && shapes.peek().getGroup() == 0) {
            removed.push(shapes.pop());
            repaint();
        } else if (shapes.size() > 0 && shapes.peek().getGroup() != 0) {
            Shape lastRemoved = shapes.pop();
            removed.push(lastRemoved);

            while (!shapes.isEmpty() && shapes.peek().getGroup() == lastRemoved.getGroup()) {
                removed.push(shapes.pop());
                repaint();
            }
        }
    }

    public void redo() {
        if (removed.size() > 0 && removed.peek().getGroup() == 0) {
            shapes.push(removed.pop());
            repaint();
        } else if (removed.size() > 0 && removed.peek().getGroup() != 0) {
            Shape lastRemoved = removed.pop();
            shapes.push(lastRemoved);
            while (!removed.isEmpty() && removed.peek().getGroup() == lastRemoved.getGroup()) {
                shapes.push(removed.pop());
                repaint();
            }
        }
    }

    public void setImage(BufferedImage image) {
        graphics2D.dispose();
        setPaintPanel(image.getWidth(), image.getHeight());
        canvas = new BufferedImage(paintPanelWidth, paintPanelHeight, BufferedImage.TYPE_INT_ARGB);
        graphics2D = canvas.createGraphics();
        graphics2D.drawImage(image, 0, 0, this);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    private void setPaintPanel(int width, int height) {
        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        graphics2D = canvas.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        setSize(width, height);
        setPreferredSize(new Dimension(width, height));
        clear();
    }

    public void clear() {
        frame.getPaintPanel().shapes.removeAllElements();
        frame.getPaintPanel().removed.removeAllElements();
        frame.getPaintPanel().preview.removeAllElements();

        frame.getPaintPanel().repaint();
    }
}
