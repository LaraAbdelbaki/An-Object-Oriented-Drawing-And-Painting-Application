/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Shapes.Circle;
import Shapes.Ellipse;
import Shapes.Line;
import Shapes.Rectangle;
import Shapes.Shape;
import Shapes.ShapeFactory;
import Shapes.Square;
import Shapes.Triangle;
import gui.Canvas.ShapeType;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Canvas extends JPanel implements MouseListener, MouseMotionListener {

    public static Canvas drawingcanvas = new Canvas();

    //make the constructor private so that this class cannot be
    //instantiated
    public Canvas() {
        shapes = new ArrayList<>();
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    //Get the only object available
    public static Canvas getInstance() {
        return drawingcanvas;
    }

    public enum ShapeType {
        RECTANGLE, SQUARE, ELLIPSE, LINE, CIRCLE, TRIANGLE
    };

    public enum Operation {
        RESIZE, MOVE, DELETE, CLEAR, UNDO, REDO, COLOR, COPY, CLONE, PASTE
    };

    private ShapeFactory factory;
    public ShapeType type;
    public Operation operation;
    public int x1, y1;
    public int x2, y2;
    public int x_resize, y_resize;
    public int x_move, y_move;
    public Color newColor;
    public Color newFillColor;
    public Graphics2D g2d;
    public ArrayList<Shape> shapes;
    private int corner = -1;
    private Shape toBeResized = null;
    private Shape toBeMoved = null;
    private Shape toBeResized_old = null;
    private Shape toBeMoved_old = null;
    private Shape toBeResized_new = null;
    private Shape toBeMoved_new = null;
    public Shape toBeColored = null;
    public Shape toBeColored_old = null;
    public Shape toBeCopiedPasted = null;
    public Shape toBeColored_new = null;
    int xPressed, yPressed;

    @Override
    protected void paintComponent(Graphics drawer) {
        super.paintComponent(drawer);
        g2d = (Graphics2D) drawer;
        g2d.setStroke(new BasicStroke(3));
        Looper shapeLooper = new Looper();
        shapeLooper.shapes = shapes;
        for (Iterator iter = shapeLooper.getIterator(); iter.hasNext();) {
            Shape current = (Shape) iter.next();
            g2d.setStroke(new BasicStroke(3));
            newColor = Color.black;
            if (current.getFillColor() == Color.white) {
                if (current instanceof Line) {
                    if (current.getColor() != null) {
                        g2d.setColor(current.getColor());
                    } else {
                        g2d.setColor(Color.black);
                    }
                    Line l = (Line) current;
                    g2d.drawLine(l.getX1(), l.getY1(), l.getX2(), l.getY2());
                } else if (current instanceof Circle) {
                    if (current.getColor() != null) {
                        g2d.setColor(current.getColor());
                    } else {
                        g2d.setColor(Color.black);
                    }
                    Circle c = (Circle) current;
                    g2d.drawOval(c.getX(), c.getY(), c.getSide(), c.getSide());
                } else if (current instanceof Triangle) {
                    if (current.getColor() != null) {
                        g2d.setColor(current.getColor());
                    } else {
                        g2d.setColor(Color.black);
                    }

                    Triangle t = (Triangle) current;
                    int xPoints[] = new int[3];
                    int yPoints[] = new int[3];
                    xPoints[0] = t.getX1();
                    xPoints[1] = t.getX2();
                    xPoints[2] = t.getX3();
                    yPoints[0] = t.getY1();
                    yPoints[1] = t.getY2();
                    yPoints[2] = t.getY3();
                    g2d.drawPolygon(xPoints, yPoints, 3);
                } else if (current instanceof Rectangle) {
                    if (current.getColor() != null) {
                        g2d.setColor(current.getColor());
                    } else {
                        g2d.setColor(Color.black);
                    }
                    Rectangle r = (Rectangle) current;
                    g2d.drawRect(r.getX(), r.getY(), r.getWidth(), r.getHeight());
                } else if (current instanceof Square) {
                    if (current.getColor() != null) {
                        g2d.setColor(current.getColor());
                    } else {
                        g2d.setColor(Color.black);
                    }
                    Square s = (Square) current;
                    g2d.drawRect(s.getX(), s.getY(), s.getSide(), s.getSide());
                } else if (current instanceof Ellipse) {
                    if (current.getColor() != null) {
                        g2d.setColor(current.getColor());
                    } else {
                        g2d.setColor(Color.black);
                    }

                    Ellipse e = (Ellipse) current;
                    g2d.drawOval(e.getX(), e.getY(), e.getWidth(), e.getHeight());
                }
            } else if (current.getFillColor() != Color.white) {
                if (current instanceof Line) {
                    if (current.getColor() != null) {
                        g2d.setColor(current.getColor());
                    } else {
                        g2d.setColor(Color.black);
                    }
                    Line l = (Line) current;
                    g2d.drawLine(l.getX1(), l.getY1(), l.getX2(), l.getY2());
                } else if (current instanceof Circle) {
                    Circle c = (Circle) current;
                    if (current.getColor() != null) {
                        g2d.setPaint(c.getColor());
                    } else {
                        g2d.setColor(Color.black);
                    }
                    g2d.drawOval(c.getX(), c.getY(), c.getSide(), c.getSide());
                    g2d.setPaint(current.getFillColor());
                    g2d.setStroke(new BasicStroke(3));
                    g2d.fillOval(c.getX(), c.getY(), c.getSide(), c.getSide());

                } else if (current instanceof Triangle) {
                    Triangle t = (Triangle) current;
                    if (current.getColor() != null) {
                        g2d.setPaint(t.getColor());
                    } else {
                        g2d.setColor(Color.black);
                    }
                    int xPoints[] = new int[3];
                    int yPoints[] = new int[3];
                    xPoints[0] = t.getX1();
                    xPoints[1] = t.getX2();
                    xPoints[2] = t.getX1() - (t.getX2() - t.getX1());
                    yPoints[0] = t.getY1();
                    yPoints[1] = t.getY2();
                    yPoints[2] = t.getY2();
                    g2d.drawPolygon(xPoints, yPoints, 3);
                    g2d.setPaint(t.getFillColor());
                    g2d.setStroke(new BasicStroke(3));
                    g2d.fillPolygon(xPoints, yPoints, 3);

                } else if (current instanceof Rectangle) {
                    Rectangle r = (Rectangle) current;
                    if (current.getColor() != null) {
                        g2d.setPaint(r.getColor());
                    } else {
                        g2d.setColor(Color.black);
                    }
                    g2d.drawRect(r.getX(), r.getY(), r.getWidth(), r.getHeight());
                    g2d.setPaint(r.getFillColor());
                    g2d.setStroke(new BasicStroke(3));
                    g2d.fillRect(r.getX(), r.getY(), r.getWidth(), r.getHeight());
                } else if (current instanceof Square) {
                    Square s = (Square) current;
                    if (current.getColor() != null) {
                        g2d.setPaint(s.getColor());
                    } else {
                        g2d.setColor(Color.black);
                    }
                    g2d.drawRect(s.getX(), s.getY(), s.getSide(), s.getSide());
                    g2d.setPaint(s.getFillColor());
                    g2d.setStroke(new BasicStroke(3));
                    g2d.fillRect(s.getX(), s.getY(), s.getSide(), s.getSide());
                } else if (current instanceof Ellipse) {
                    Ellipse e = (Ellipse) current;
                    if (current.getColor() != null) {
                        g2d.setPaint(e.getColor());
                    } else {
                        g2d.setColor(Color.black);
                    }
                    g2d.drawOval(e.getX(), e.getY(), e.getWidth(), e.getHeight());
                    g2d.setPaint(e.getFillColor());
                    g2d.setStroke(new BasicStroke(3));
                    g2d.fillOval(e.getX(), e.getY(), e.getWidth(), e.getHeight());
                }

            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (operation != null) {
            switch (operation) {
                case COPY:
                    toBeCopiedPasted = getSelectedShape(e.getX(), e.getY());
                    if (toBeCopiedPasted != null) {
                        operation = Operation.PASTE;
                    }
                    break;
                case PASTE:
                    Shape temp = null;
                    if (toBeCopiedPasted instanceof Circle) {
                        Circle copy = new Circle(e.getX(), e.getY(), ((Circle) toBeCopiedPasted).getSide(), ((Circle) toBeCopiedPasted).getColor(), ((Circle) toBeCopiedPasted).getFillColor());
                        shapes.add(copy);
                        temp = copy;
                    } else if (toBeCopiedPasted instanceof Ellipse) {
                        Ellipse copy = new Ellipse(e.getX(), e.getY(), ((Ellipse) toBeCopiedPasted).getWidth(), ((Ellipse) toBeCopiedPasted).getHeight(), ((Ellipse) toBeCopiedPasted).getColor(), ((Ellipse) toBeCopiedPasted).getFillColor());
                        shapes.add(copy);
                        temp = copy;
                    } else if (toBeCopiedPasted instanceof Square) {
                        Square copy = new Square(e.getX(), e.getY(), ((Square) toBeCopiedPasted).getSide(), ((Square) toBeCopiedPasted).getColor(), ((Square) toBeCopiedPasted).getFillColor());
                        shapes.add(copy);
                        temp = copy;
                    } else if (toBeCopiedPasted instanceof Rectangle) {
                        Rectangle copy = new Rectangle(e.getX(), e.getY(), ((Rectangle) toBeCopiedPasted).getWidth(), ((Rectangle) toBeCopiedPasted).getHeight(), ((Rectangle) toBeCopiedPasted).getColor(), ((Rectangle) toBeCopiedPasted).getFillColor());
                        shapes.add(copy);
                        temp = copy;
                    } else if (toBeCopiedPasted instanceof Line) {
                        int newX2 = e.getX()-((Line) toBeCopiedPasted).getX1()+((Line) toBeCopiedPasted).getX2();
                        int newY2 = e.getY()-((Line) toBeCopiedPasted).getY1()+((Line) toBeCopiedPasted).getY2();
                        Line copy = new Line(e.getX(),e.getY(),newX2,newY2,((Line) toBeCopiedPasted).getColor(), ((Line) toBeCopiedPasted).getFillColor()); 
                        shapes.add(copy);
                        temp = copy;
                    } else if (toBeCopiedPasted instanceof Triangle){
                        int newX2 = e.getX()-((Triangle) toBeCopiedPasted).getX1()+((Triangle) toBeCopiedPasted).getX2();
                        int newY2 = e.getY()-((Triangle) toBeCopiedPasted).getY1()+((Triangle) toBeCopiedPasted).getY2();
                          int newX3 = e.getX()-((Triangle) toBeCopiedPasted).getX1()+((Triangle) toBeCopiedPasted).getX3();
                        int newY3 = e.getY()-((Triangle) toBeCopiedPasted).getY1()+((Triangle) toBeCopiedPasted).getY3();
                        Triangle copy = new Triangle(e.getX(),e.getY(),newX2,newY2,newX3,newY3,((Triangle) toBeCopiedPasted).getColor(), ((Triangle) toBeCopiedPasted).getFillColor()); 
                        shapes.add(copy);
                        temp = copy;
                    }
                    operation = Operation.COPY;
                    if (temp != null) {
                        Action a = new Action(null, temp, Action.Operation.COPY);
                        Action.actions_undo.push(a);
                    }
                    repaint();
                    break;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.xPressed = e.getX();
        this.yPressed = e.getY();
        if (null != type) {
            switch (type) {
                case LINE:
                    x1 = e.getX();
                    y1 = e.getY();
                    factory = new ShapeFactory(x1, y1, x1, y1, ShapeFactory.ShapeType.LINE, newColor, Color.white);
                    Line l = (Line) factory.addShape();
                    l.template();
                    shapes.add(l);
                    repaint();
                    break;
                case CIRCLE:
                    x1 = e.getX();
                    y1 = e.getY();
                    factory = new ShapeFactory(x1, y1, 0, 0, ShapeFactory.ShapeType.CIRCLE, newColor, Color.white);
                    Circle c = (Circle) factory.addShape();
                    c.template();
                    shapes.add(c);
                    repaint();
                    break;
                case RECTANGLE:
                    x1 = e.getX();
                    y1 = e.getY();
                    factory = new ShapeFactory(x1, y1, 0, 0, ShapeFactory.ShapeType.RECTANGLE, newColor, Color.white);
                    Rectangle r = (Rectangle) factory.addShape();
                    shapes.add(r);
                    r.template();
                    repaint();
                    break;
                case SQUARE:
                    x1 = e.getX();
                    y1 = e.getY();
                    factory = new ShapeFactory(x1, y1, 0, 0, ShapeFactory.ShapeType.SQUARE, newColor, Color.white);
                    Square s = (Square) factory.addShape();
                    shapes.add(s);
                    s.template();
                    repaint();
                    break;
                case ELLIPSE:
                    x1 = e.getX();
                    y1 = e.getY();
                    factory = new ShapeFactory(x1, y1, 0, 0, ShapeFactory.ShapeType.ELLIPSE, newColor, Color.white);
                    Ellipse el = (Ellipse) factory.addShape();
                    el.template();
                    shapes.add(el);
                    repaint();
                    break;
                case TRIANGLE:
                    x1 = e.getX();
                    y1 = e.getY();
                    factory = new ShapeFactory(x1, y1, 0, 0, ShapeFactory.ShapeType.TRIANGLE, newColor, Color.white);
                    Triangle t = (Triangle) factory.addShape();
                    shapes.add(t);
                    t.template();
                    repaint();
                    break;
                default:
                    break;
            }
        }
        if (null != operation) {
            switch (operation) {
                case RESIZE:
                    int x = e.getX();
                    int y = e.getY();
                    toBeResized = getSelectedShape(x, y);
                    if (toBeResized != null) {
                        corner = toBeResized.getNearestCorner(x, y);
                        System.out.println("Nearest corner of " + toBeResized + " is " + corner);
                        if (toBeResized instanceof Square) {
                            Square s = (Square) toBeResized;
                            factory = new ShapeFactory(s.getX(), s.getY(), s.getSide(), 0, ShapeFactory.ShapeType.SQUARE, s.getColor(), s.getFillColor());
                            toBeResized_old = factory.copyShape();
                            x_resize = s.getX() + s.getSide();
                            y_resize = s.getY() + s.getSide();
                        } else if (toBeResized instanceof Circle) {
                            Circle c = (Circle) toBeResized;
                            x_resize = c.getX() + c.getSide();
                            y_resize = c.getY() + c.getSide();
                            factory = new ShapeFactory(c.getX(), c.getY(), c.getSide(), 0, ShapeFactory.ShapeType.CIRCLE, c.getColor(), c.getFillColor());
                            toBeResized_old = factory.copyShape();
                        } else if (toBeResized instanceof Rectangle) {
                            Rectangle r = (Rectangle) toBeResized;
                            factory = new ShapeFactory(r.getX(), r.getY(), r.getWidth(), r.getHeight(), ShapeFactory.ShapeType.RECTANGLE, r.getColor(), r.getFillColor());
                            toBeResized_old = factory.copyShape();
                        } else if (toBeResized instanceof Line) {
                            Line l = (Line) toBeResized;
                            factory = new ShapeFactory(l.getX1(), l.getY1(), l.getX2(), l.getY2(), ShapeFactory.ShapeType.LINE, l.getColor(), l.getFillColor());
                            toBeResized_old = factory.copyShape();
                        } else if (toBeResized instanceof Ellipse) {
                            Ellipse el = (Ellipse) toBeResized;
                            factory = new ShapeFactory(el.getX(), el.getY(), el.getWidth(), el.getHeight(), ShapeFactory.ShapeType.ELLIPSE, el.getColor(), el.getFillColor());
                            toBeResized_old = factory.copyShape();
                        } else if (toBeResized instanceof Triangle) {
                            Triangle t = (Triangle) toBeResized;
                            factory = new ShapeFactory(t.getX1(), t.getY1(), t.getX2(), t.getY2(), t.getX3(), t.getY3(), t.getColor(), t.getFillColor());
                            toBeResized_old = factory.copyShape();
                        }

                    }
                    break;

                case DELETE:
                    Shape toBeDeleted = getSelectedShape(e.getX(), e.getY());
                    Shape temp = null;
                    Shape temp1 = null;
                    Action a;
                    for (int i = shapes.size() - 1; i >= 0; i--) {
                        if (shapes.get(i) == toBeDeleted) {
                            temp = toBeDeleted;
                            shapes.remove(i);
                        }
                        if (temp instanceof Square) {
                            factory = new ShapeFactory(((Square) temp).getX(), ((Square) temp).getY(), ((Square) temp).getSide(), ((Square) temp).getSide(), ShapeFactory.ShapeType.SQUARE, temp.getColor(), temp.getFillColor());
                            temp1 = factory.copyShape();
                        } else if (temp instanceof Rectangle) {
                            factory = new ShapeFactory(((Rectangle) temp).getX(), ((Rectangle) temp).getY(), ((Rectangle) temp).getWidth(), ((Rectangle) temp).getHeight(), ShapeFactory.ShapeType.RECTANGLE, temp.getColor(), temp.getFillColor());
                            temp1 = factory.copyShape();
                        } else if (temp instanceof Line) {
                            factory = new ShapeFactory(((Line) temp).getX1(), ((Line) temp).getY1(), ((Line) temp).getX2(), ((Line) temp).getY2(), ShapeFactory.ShapeType.LINE, temp.getColor(), temp.getFillColor());
                            temp1 = factory.copyShape();
                        } else if (temp instanceof Circle) {
                            factory = new ShapeFactory(((Circle) temp).getX(), ((Circle) temp).getY(), ((Circle) temp).getSide(), ((Circle) temp).getSide(), ShapeFactory.ShapeType.CIRCLE, temp.getColor(), temp.getFillColor());
                            temp1 = factory.copyShape();
                        } else if (temp instanceof Ellipse) {
                            factory = new ShapeFactory(((Ellipse) temp).getX(), ((Ellipse) temp).getY(), ((Ellipse) temp).getWidth(), ((Ellipse) temp).getHeight(), ShapeFactory.ShapeType.ELLIPSE, temp.getColor(), temp.getFillColor());
                            temp1 = factory.copyShape();
                        } else if (temp instanceof Triangle) {
                            factory = new ShapeFactory(((Triangle) temp).getX1(), ((Triangle) temp).getY1(), ((Triangle) temp).getX2(), ((Triangle) temp).getY2(), ((Triangle) temp).getX3(), ((Triangle) temp).getY3(), temp.getColor(), temp.getFillColor());
                            temp1 = factory.copyShape();
                        }
                        if (temp1 != null) {
                            a = new Action(temp1, null, Action.Operation.DELETE);
                            Action.actions_undo.push(a);
                        }
                        repaint();
                    }
                    break;
                case COLOR:
                    toBeColored = getSelectedShape(e.getX(), e.getY());
                    if (toBeColored != null) {
                        Color old_c = new Color(toBeColored.getFillColor().getRGB());
                        if (toBeColored instanceof Square) {
                            Square s = (Square) toBeColored;
                            factory = new ShapeFactory(s.getX(), s.getY(), s.getSide(), 0, ShapeFactory.ShapeType.SQUARE, s.getColor(), s.getFillColor());
                            toBeColored_old = factory.copyShape();
                        } else if (toBeColored instanceof Circle) {
                            Circle c = (Circle) toBeColored;
                            factory = new ShapeFactory(c.getX(), c.getY(), c.getSide(), 0, ShapeFactory.ShapeType.CIRCLE, c.getColor(), c.getFillColor());
                            toBeColored_old = factory.copyShape();
                        } else if (toBeColored instanceof Rectangle) {
                            Rectangle r = (Rectangle) toBeColored;
                            factory = new ShapeFactory(r.getX(), r.getY(), r.getWidth(), r.getHeight(), ShapeFactory.ShapeType.RECTANGLE, r.getColor(), r.getFillColor());
                            toBeColored_old = factory.copyShape();
                        } else if (toBeColored instanceof Line) {
                            Line l = (Line) toBeColored;
                            factory = new ShapeFactory(l.getX1(), l.getY1(), l.getX2(), l.getY2(), ShapeFactory.ShapeType.LINE, l.getColor(), l.getFillColor());
                            toBeColored_old = factory.copyShape();
                        } else if (toBeColored instanceof Ellipse) {
                            Ellipse el = (Ellipse) toBeColored;
                            factory = new ShapeFactory(el.getX(), el.getY(), el.getWidth(), el.getHeight(), ShapeFactory.ShapeType.ELLIPSE, el.getColor(), el.getFillColor());
                            toBeColored_old = factory.copyShape();
                        } else if (toBeColored instanceof Triangle) {
                            Triangle t = (Triangle) toBeColored;
                            factory = new ShapeFactory(t.getX1(), t.getY1(), t.getX2(), t.getY2(), t.getX3(), t.getY3(), t.getColor(), t.getFillColor());
                            toBeColored_old = factory.copyShape();
                        }
                        for (int i = 0; i < shapes.size(); i++) {
                            if (toBeColored.getClass().equals(shapes.get(i).getClass())) {
                                if (toBeColored.equals(shapes.get(i))) {
                                    shapes.get(i).setFillColor(newFillColor);
                                    break;
                                }
                            }
                        }
                        a = new Action(toBeColored_old, old_c, newFillColor);
                        Action.actions_undo.push(a);
                    } else if (toBeColored == null) {
                        Color old_bg = new Color(this.getBackground().getRGB());
                        Color new_bg = new Color(this.newFillColor.getRGB());
                        this.setBackground(this.newFillColor);
                        a = new Action(old_bg, new_bg);
                        Action.actions_undo.push(a);
                    }
                    repaint();
                    break;
                case MOVE:
                    toBeMoved = getSelectedShape(e.getX(), e.getY());
                    x_move = e.getX();
                    y_move = e.getY();
                    if (toBeMoved != null) {
                        if (toBeMoved instanceof Square) {
                            Square s = (Square) toBeMoved;
                            factory = new ShapeFactory(s.getX(), s.getY(), s.getSide(), 0, ShapeFactory.ShapeType.SQUARE, s.getColor(), s.getFillColor());
                            toBeMoved_old = factory.copyShape();
                        } else if (toBeMoved instanceof Circle) {
                            Circle c = (Circle) toBeMoved;
                            factory = new ShapeFactory(c.getX(), c.getY(), c.getSide(), 0, ShapeFactory.ShapeType.CIRCLE, c.getColor(), c.getFillColor());
                            toBeMoved_old = factory.copyShape();
                        } else if (toBeMoved instanceof Rectangle) {
                            Rectangle r = (Rectangle) toBeMoved;
                            factory = new ShapeFactory(r.getX(), r.getY(), r.getWidth(), r.getHeight(), ShapeFactory.ShapeType.RECTANGLE, r.getColor(), r.getFillColor());
                            toBeMoved_old = factory.copyShape();
                        } else if (toBeMoved instanceof Line) {
                            Line l = (Line) toBeMoved;
                            factory = new ShapeFactory(l.getX1(), l.getY1(), l.getX2(), l.getY2(), ShapeFactory.ShapeType.LINE, l.getColor(), l.getFillColor());
                            toBeMoved_old = factory.copyShape();
                        } else if (toBeMoved instanceof Ellipse) {
                            Ellipse el = (Ellipse) toBeMoved;
                            factory = new ShapeFactory(el.getX(), el.getY(), el.getWidth(), el.getHeight(), ShapeFactory.ShapeType.ELLIPSE, el.getColor(), el.getFillColor());
                            toBeMoved_old = factory.copyShape();
                        } else if (toBeMoved instanceof Triangle) {
                            Triangle t = (Triangle) toBeMoved;
                            factory = new ShapeFactory(t.getX1(), t.getY1(), t.getX2(), t.getY2(), t.getX3(), t.getY3(), t.getColor(), t.getFillColor());
                            toBeMoved_old = factory.copyShape();
                        }

                    }
                    break;
                case CLONE:
                    Shape toBeCopied = getSelectedShape(e.getX(), e.getY());
                    if (toBeCopied instanceof Circle) {
                        Circle copy = new Circle((Circle) toBeCopied);
                        shapes.add(copy.clone());
                    } else if (toBeCopied instanceof Ellipse) {
                        Ellipse copy = new Ellipse((Ellipse) toBeCopied);
                        shapes.add(copy.clone());
                    } else if (toBeCopied instanceof Square) {
                        Square copy = new Square((Square) toBeCopied);
                        shapes.add(copy.clone());
                    } else if (toBeCopied instanceof Rectangle) {
                        Rectangle copy = new Rectangle((Rectangle) toBeCopied);
                        shapes.add(copy.clone());
                    } else if (toBeCopied instanceof Line) {
                        Line copy = new Line((Line) toBeCopied);
                        shapes.add(copy.clone());
                    } else if (toBeCopied instanceof Triangle) {
                        Triangle copy = new Triangle((Triangle) toBeCopied);
                        shapes.add(copy.clone());
                    }
                    repaint();
                    break;
            }
        }
    }

    private Shape getSelectedShape(int x, int y) {
        Shape selectedShape = null;
        boolean found = false;
        if (shapes.size() != 0) {
            for (int i = shapes.size() - 1; i >= 0; i--) {
                if (shapes.get(i) != null && shapes.get(i).contains(x, y) && found == false) {
                    selectedShape = (Shape) shapes.get(i);
                    System.out.println("You are now clicking on  " + selectedShape);
                    found = true;
                }
            }
        }
        return selectedShape;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Action a;
        Shape temp;
        Shape temp1 = null;
        if (type != null) {
            temp = shapes.get(shapes.size() - 1);
            if (temp instanceof Square) {
                factory = new ShapeFactory(((Square) temp).getX(), ((Square) temp).getY(), ((Square) temp).getSide(), ((Square) temp).getSide(), ShapeFactory.ShapeType.SQUARE, temp.getColor(), temp.getFillColor());
                temp1 = factory.copyShape();
            } else if (temp instanceof Rectangle) {
                factory = new ShapeFactory(((Rectangle) temp).getX(), ((Rectangle) temp).getY(), ((Rectangle) temp).getWidth(), ((Rectangle) temp).getHeight(), ShapeFactory.ShapeType.RECTANGLE, temp.getColor(), temp.getFillColor());
                temp1 = factory.copyShape();
            } else if (temp instanceof Line) {
                factory = new ShapeFactory(((Line) temp).getX1(), ((Line) temp).getY1(), ((Line) temp).getX2(), ((Line) temp).getY2(), ShapeFactory.ShapeType.LINE, temp.getColor(), temp.getFillColor());
                temp1 = factory.copyShape();
            } else if (temp instanceof Circle) {
                factory = new ShapeFactory(((Circle) temp).getX(), ((Circle) temp).getY(), ((Circle) temp).getSide(), ((Circle) temp).getSide(), ShapeFactory.ShapeType.CIRCLE, temp.getColor(), temp.getFillColor());
                temp1 = factory.copyShape();
            } else if (temp instanceof Ellipse) {
                factory = new ShapeFactory(((Ellipse) temp).getX(), ((Ellipse) temp).getY(), ((Ellipse) temp).getWidth(), ((Ellipse) temp).getHeight(), ShapeFactory.ShapeType.ELLIPSE, temp.getColor(), temp.getFillColor());
                temp1 = factory.copyShape();
            } else if (temp instanceof Triangle) {
                factory = new ShapeFactory(((Triangle) temp).getX1(), ((Triangle) temp).getY1(), ((Triangle) temp).getX2(), ((Triangle) temp).getY2(), ((Triangle) temp).getX3(), ((Triangle) temp).getY3(), temp.getColor(), temp.getFillColor());
                temp1 = factory.copyShape();
            }
            if (temp1 != null) {
                a = new Action(null, temp1, Action.Operation.DRAW);
                Action.actions_undo.push(a);
            }
        }
        if (operation != null) {
            switch (operation) {
                case RESIZE:
                    temp = toBeResized_new;
                    temp1 = toBeResized_old;

                    if (temp != null && temp1 != null) {
                        a = new Action(temp1, temp, Action.Operation.RESIZE);
                        Action.actions_undo.push(a);
                    }
                    break;
                case MOVE:
                    temp = toBeMoved_new;
                    temp1 = toBeMoved_old;
                    if (temp != null && temp1 != null) {
                        a = new Action(temp1, temp, Action.Operation.MOVE);
                        Action.actions_undo.push(a);
                    }
                    break;
                case CLONE:
                    temp = shapes.get(shapes.size() - 1);
                    a = new Action(null, temp, Action.Operation.CLONE);
                    Action.actions_undo.push(a);
                    break;
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {

        x2 = e.getX();
        y2 = e.getY();
        if (shapes.size() > 0) {
            if (type != null) {
                switch (type) {
                    case LINE:
                        Line l = (Line) shapes.get(shapes.size() - 1);
                        l.setX2(x2);
                        l.setY2(y2);
                        break;
                    case CIRCLE:
                        Circle c = (Circle) shapes.get(shapes.size() - 1);
                        if (x2 > x1 && y2 > y1) {
                            c.setSide(Math.abs(y1 - y2));
                        } else if (x2 > x1 && y2 < y1) {
                            c.setY(y2);
                            c.setSide(Math.abs(y1 - y2));
                        } else if (x2 < x1 && y2 > y1) {
                            c.setX(x2);
                            c.setSide(Math.abs(x1 - x2));
                        } else if (x2 < x1 && y2 < y1) {
                            double diagonal = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
                            double side = diagonal * Math.cos(Math.PI / 4);
                            int side1 = (int) side;
                            c.setX(x1 - side1);
                            c.setY(y1 - side1);
                            c.setSide(Math.abs(side1));
                        }
                        break;
                    case TRIANGLE:
                        Triangle t = (Triangle) shapes.get(shapes.size() - 1);
                        t.setX2(x2);
                        t.setY2(y2);
                        t.setX3(t.getX1() - (t.getX2() - t.getX1()));
                        t.setY3(y2);
                        break;
                    case ELLIPSE:
                        Ellipse el = (Ellipse) shapes.get(shapes.size() - 1);
                        if (x2 < x1) {
                            el.setX(x2);
                        }
                        if (y2 < y1) {
                            el.setY(y2);
                        }
                        el.setWidth(Math.abs(x2 - x1));
                        el.setHeight(Math.abs(y2 - y1));
                        break;
                    case RECTANGLE:
                        Rectangle r = (Rectangle) shapes.get(shapes.size() - 1);
                        if (x1 > x2) {
                            r.setX(x2);
                        }
                        if (y1 > y2) {
                            r.setY(y2);
                        }
                        r.setWidth(Math.abs(x2 - x1));
                        r.setHeight(Math.abs(y2 - y1));
                        break;
                    case SQUARE:
                        Square s = (Square) shapes.get(shapes.size() - 1);
                        if (x2 > x1 && y2 > y1) {
                            s.setSide(Math.abs(y1 - y2));
                        } else if (x2 > x1 && y2 < y1) {
                            s.setY(y2);
                            s.setSide(Math.abs(y1 - y2));
                        } else if (x2 < x1 && y2 > y1) {
                            s.setX(x2);
                            s.setSide(Math.abs(x1 - x2));
                        } else if (x2 < x1 && y2 < y1) {
                            double diagonal = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
                            double side = diagonal * Math.cos(Math.PI / 4);
                            int side1 = (int) side;
                            s.setX(x1 - side1);
                            s.setY(y1 - side1);
                            s.setSide(Math.abs(side1));
                        }
                        break;
                    default:
                        break;

                }
            }
            if (operation != null) {
                switch (operation) {
                    case RESIZE:
                        if (toBeResized != null && corner != -1) {
                            for (int i = 0; i < shapes.size(); i++) {
                                if (shapes.get(i) == toBeResized) {
                                    if (shapes.get(i) instanceof Rectangle) {
                                        Rectangle r = (Rectangle) shapes.get(i);
                                        int x1, y1;
                                        x1 = r.getX();
                                        y1 = r.getY();
                                        int height = r.getHeight();
                                        int width = r.getWidth();
                                        switch (corner) {
                                            case 0:
                                                if (x2 < (x1 + width) && y2 < (y1 + height)) {
                                                    r.setY(y2);
                                                    r.setX(x2);
                                                    r.setWidth(width - x2 + x1);
                                                    r.setHeight(height - y2 + y1);
                                                }
                                                break;
                                            case 1:
                                                if (x2 > x1) {
                                                    r.setY(y2);
                                                    r.setWidth(x2 - x1);
                                                    r.setHeight(height - y2 + y1);
                                                }
                                                break;
                                            case 2:
                                                if (x1 < x2 && y1 < y2) {
                                                    r.setWidth(Math.abs(x2 - r.getX()));
                                                    r.setHeight(Math.abs(y2 - r.getY()));
                                                }
                                                break;
                                            case 3:
                                                if (y2 > y1) {
                                                    r.setX(x2);
                                                    r.setHeight(y2 - y1);
                                                    r.setWidth(width - x2 + x1);
                                                }
                                                break;
                                        }
                                        factory = new ShapeFactory(r.getX(), r.getY(), r.getWidth(), r.getHeight(), ShapeFactory.ShapeType.RECTANGLE, r.getColor(), r.getFillColor());
                                        toBeResized_new = factory.copyShape();
                                    } else if (shapes.get(i) instanceof Square) {
                                        Square s = (Square) shapes.get(i);
                                        int x1, y1;
                                        x1 = s.getX();
                                        y1 = s.getY();
                                        int side = s.getSide();
                                        switch (corner) {
                                            case 0:
                                                if (x2 < (x_resize + side) && y2 < (y_resize + side)) {
                                                    double diagonal = Math.sqrt((x2 - x_resize) * (x2 - x_resize) + (y2 - y_resize) * (y2 - y_resize));
                                                    double side_new = diagonal * Math.cos(Math.PI / 4);
                                                    int side_new1 = (int) side_new;
                                                    s.setX(Math.abs(x_resize - side_new1));
                                                    s.setY(Math.abs(y_resize - side_new1));
                                                    s.setSide(Math.abs(side_new1));
                                                }
                                                break;
                                            case 1:
                                                if (x2 > x1) {
                                                    s.setY(y2);
                                                    s.setSide(x2 - x1);
                                                    s.setSide(side - y2 + y1);
                                                }
                                                break;
                                            case 2:
                                                if (x1 < x2 && y1 < y2) {
                                                    s.setSide(Math.abs(x2 - s.getX()));
                                                    s.setSide(Math.abs(y2 - s.getY()));
                                                }
                                                break;
                                            case 3:
                                                if (y2 > y1) {
                                                    s.setX(x2);
                                                    s.setSide(y2 - y1);
                                                    s.setSide(side - x2 + x1);
                                                }
                                                break;
                                        }
                                        factory = new ShapeFactory(s.getX(), s.getY(), s.getSide(), s.getSide(), ShapeFactory.ShapeType.SQUARE, s.getColor(), s.getFillColor());
                                        toBeResized_new = factory.copyShape();
                                    } else if (shapes.get(i) instanceof Ellipse) {
                                        Ellipse el = (Ellipse) shapes.get(i);
                                        int x1, y1;
                                        x1 = el.getX();
                                        y1 = el.getY();
                                        int height = el.getHeight();
                                        int width = el.getWidth();
                                        switch (corner) {
                                            case 0:
                                                if (x2 < (x1 + width) && y2 < (y1 + height)) {
                                                    el.setY(y2);
                                                    el.setX(x2);
                                                    el.setWidth(width - x2 + x1);
                                                    el.setHeight(height - y2 + y1);
                                                }
                                                break;
                                            case 1:
                                                if (x2 > x1) {
                                                    el.setY(y2);
                                                    el.setWidth(x2 - x1);
                                                    el.setHeight(height - y2 + y1);
                                                }
                                                break;
                                            case 2:
                                                if (x1 < x2 && y1 < y2) {
                                                    el.setWidth(Math.abs(x2 - el.getX()));
                                                    el.setHeight(Math.abs(y2 - el.getY()));
                                                }
                                                break;
                                            case 3:
                                                if (y2 > y1) {
                                                    el.setX(x2);
                                                    el.setHeight(y2 - y1);
                                                    el.setWidth(width - x2 + x1);
                                                }
                                                break;
                                        }
                                        factory = new ShapeFactory(el.getX(), el.getY(), el.getWidth(), el.getHeight(), ShapeFactory.ShapeType.ELLIPSE, el.getColor(), el.getFillColor());
                                        toBeResized_new = factory.copyShape();
                                    } else if (shapes.get(i) instanceof Circle) {
                                        Circle c = (Circle) shapes.get(i);
                                        int x1, y1;
                                        x1 = c.getX();
                                        y1 = c.getY();
                                        int side = c.getSide();
                                        switch (corner) {
                                            case 0:
                                                if (x2 < (x_resize + side) && y2 < (y_resize + side)) {
                                                    double diagonal = Math.sqrt((x2 - x_resize) * (x2 - x_resize) + (y2 - y_resize) * (y2 - y_resize));
                                                    double side_new = diagonal * Math.cos(Math.PI / 4);
                                                    int side_new1 = (int) side_new;
                                                    c.setX(Math.abs(x_resize - side_new1));
                                                    c.setY(Math.abs(y_resize - side_new1));
                                                    c.setSide(Math.abs(side_new1));
                                                }
                                                break;
                                            case 1:
                                                if (x2 > x1) {
                                                    c.setY(y2);
                                                    c.setSide(x2 - x1);
                                                    c.setSide(side - y2 + y1);
                                                }
                                                break;
                                            case 2:
                                                if (x1 < x2 && y1 < y2) {
                                                    c.setSide(Math.abs(x2 - c.getX()));
                                                    c.setSide(Math.abs(y2 - c.getY()));
                                                }
                                                break;
                                            case 3:
                                                if (y2 > y1) {
                                                    c.setX(x2);
                                                    c.setSide(y2 - y1);
                                                    c.setSide(side - x2 + x1);
                                                }
                                                break;
                                        }
                                        factory = new ShapeFactory(c.getX(), c.getY(), c.getSide(), c.getSide(), ShapeFactory.ShapeType.CIRCLE, c.getColor(), c.getFillColor());
                                        toBeResized_new = factory.copyShape();
                                    } else if (shapes.get(i) instanceof Line) {
                                        Line l = (Line) shapes.get(i);
                                        switch (corner) {
                                            case 0:
                                                l.setX1(x2);
                                                l.setY1(y2);
                                                break;
                                            case 1:
                                                l.setX2(x2);
                                                l.setY2(y2);
                                                break;
                                        }
                                        factory = new ShapeFactory(l.getX1(), l.getY1(), l.getX2(), l.getY2(), ShapeFactory.ShapeType.LINE, l.getColor(), l.getFillColor());
                                        toBeResized_new = factory.copyShape();
                                    } else if (shapes.get(i) instanceof Triangle) {
                                        Triangle t = (Triangle) shapes.get(i);
                                        switch (corner) {
                                            case 0:
                                                t.setX1(x2);
                                                t.setY1(y2);
                                                break;
                                            case 1:
                                                t.setX3(x2);
                                                t.setY3(y2);
                                                break;
                                            case 2:
                                                t.setX2(x2);
                                                t.setY2(y2);
                                                break;
                                        }
                                        factory = new ShapeFactory(t.getX1(), t.getY1(), t.getX2(), t.getY2(), t.getX3(), t.getY3(), t.getColor(), t.getFillColor());
                                        toBeResized_new = factory.copyShape();
                                    }

                                }
                            }
                        }
                        break;

                    case MOVE:
                        if (toBeMoved != null) {
                            for (int i = 0; i < shapes.size(); i++) {
                                Point drag = new Point(x2 - xPressed, y2 - yPressed);
                                if (shapes.get(i) == toBeMoved) {
                                    if (shapes.get(i) instanceof Line) {
                                        Line l = (Line) shapes.get(i);
                                        l.setX1(l.getX1() + drag.x);
                                        l.setX2(l.getX2() + drag.x);
                                        l.setY1(l.getY1() + drag.y);
                                        l.setY2(l.getY2() + drag.y);
                                        factory = new ShapeFactory(l.getX1(), l.getY1(), l.getX2(), l.getY2(), ShapeFactory.ShapeType.LINE, l.getColor(), l.getFillColor());
                                        toBeMoved_new = factory.copyShape();
                                    } else if (shapes.get(i) instanceof Rectangle) {
                                        Rectangle r = (Rectangle) shapes.get(i);

                                        r.setX(r.getX() + drag.x);
                                        r.setY(r.getY() + drag.y);
                                        factory = new ShapeFactory(r.getX(), r.getY(), r.getWidth(), r.getHeight(), ShapeFactory.ShapeType.RECTANGLE, r.getColor(), r.getFillColor());
                                        toBeMoved_new = factory.copyShape();
                                    } else if (shapes.get(i) instanceof Square) {
                                        Square s = (Square) shapes.get(i);
                                        s.setX(s.getX() + drag.x);
                                        s.setY(s.getY() + drag.y);
                                        factory = new ShapeFactory(s.getX(), s.getY(), s.getSide(), s.getSide(), ShapeFactory.ShapeType.SQUARE, s.getColor(), s.getFillColor());
                                        toBeMoved_new = factory.copyShape();
                                    } else if (shapes.get(i) instanceof Circle) {
                                        Circle c = (Circle) shapes.get(i);
                                        c.setX(c.getX() + drag.x);
                                        c.setY(c.getY() + drag.y);
                                        factory = new ShapeFactory(c.getX(), c.getY(), c.getSide(), c.getSide(), ShapeFactory.ShapeType.CIRCLE, c.getColor(), c.getFillColor());
                                        toBeMoved_new = factory.copyShape();
                                    } else if (shapes.get(i) instanceof Ellipse) {
                                        Ellipse el = (Ellipse) shapes.get(i);
                                        el.setX(el.getX() + drag.x);
                                        el.setY(el.getY() + drag.y);
                                        factory = new ShapeFactory(el.getX(), el.getY(), el.getWidth(), el.getHeight(), ShapeFactory.ShapeType.ELLIPSE, el.getColor(), el.getFillColor());
                                        toBeMoved_new = factory.copyShape();
                                    } else if (shapes.get(i) instanceof Triangle) {
                                        Triangle t = (Triangle) shapes.get(i);
                                        t.setX1(t.getX1() + drag.x);
                                        t.setX2(t.getX2() + drag.x);
                                        t.setY1(t.getY1() + drag.y);
                                        t.setY2(t.getY2() + drag.y);
                                        t.setY3(t.getY3() + drag.y);
                                        t.setX3(t.getX3() + drag.x);
                                        factory = new ShapeFactory(t.getX1(), t.getY1(), t.getX2(), t.getY2(), t.getX3(), t.getY3(), t.getColor(), t.getFillColor());
                                        toBeMoved_new = factory.copyShape();
                                    }
                                    repaint();
                                    xPressed += drag.x;
                                    yPressed += drag.y;
                                }
                            }
                        }
                        break;
                }
            }
            repaint();
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    public void exportImage(String imageName) throws IOException {
        BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
        Graphics2D graphics = image.createGraphics();
        paint(graphics);
        graphics.dispose();
        try {
            System.out.println("Saving file " + imageName);
            FileOutputStream file = new FileOutputStream(imageName);
            ImageIO.write(image, "png", file);
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
