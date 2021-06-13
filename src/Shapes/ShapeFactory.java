/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shapes;
import java.awt.Color;

/**
 *
 * @author Nour Hesham Shaheen
 */
public class ShapeFactory {
    
    private int x;
    private int y;
    private int parameter1;
    private int parameter2;
    private int parameter3;
    private int parameter4;
    private ShapeType type;
    private Color outline;
    private Color fill;

    public ShapeFactory(int x, int y, int parameter1, int parameter2, ShapeType type, Color outline, Color fill) {
        this.x = x;
        this.y = y;
        this.parameter1 = parameter1;
        this.parameter2 = parameter2;
        this.type = type;
        this.outline = outline;
        this.fill = fill;
    }
    
     public ShapeFactory(int x, int y, int parameter1, int parameter2, int parameter3, int parameter4, Color outline, Color fill) {
        this.x = x;
        this.y = y;
        this.parameter1 = parameter1;
        this.parameter2 = parameter2;
        this.parameter3 = parameter3;
        this.parameter4 = parameter4;
        this.type = ShapeType.TRIANGLE;
        this.outline = outline;
        this.fill = fill;
    }
    
    public static enum ShapeType {
        RECTANGLE, SQUARE, ELLIPSE, LINE, CIRCLE, TRIANGLE
    };
    
    public Shape addShape()
    {
        if(type != null)
        switch(type)
        {
            case RECTANGLE:
                Rectangle r = new Rectangle(x, y, parameter1, parameter2, outline, Color.white);
                return r;
            case SQUARE:
                Square s = new Square(x, y, parameter1, outline, Color.white);
                return s;
            case ELLIPSE:
                Ellipse el = new Ellipse(x, y, parameter1, parameter2, outline, Color.white);
                return el;
            case LINE:
                Line l = new Line(x, y, parameter1, parameter2, outline, Color.white);
                return l;
            case CIRCLE:
                Circle c = new Circle(x, y, parameter1, outline, Color.white);
                return c;
            case TRIANGLE:
                Triangle t = new Triangle(x, y, x, y, x, y, outline, Color.white);
                return t;
        }
        return null;
    }
    
    public Shape copyShape()
    {
        if(type != null)
        switch(type)
        {
            case RECTANGLE:
                Rectangle r = new Rectangle(x, y, parameter1, parameter2, outline, fill);
                return r;
            case SQUARE:
                Square s = new Square(x, y, parameter1, outline, fill);
                return s;
            case ELLIPSE:
                Ellipse el = new Ellipse(x, y, parameter1, parameter2, outline, fill);
                return el;
            case LINE:
                Line l = new Line(x, y, parameter1, parameter2, outline, fill);
                return l;
            case CIRCLE:
                Circle c = new Circle(x, y, parameter1, outline, fill);
                return c;
            case TRIANGLE:
                Triangle t = new Triangle(x, y, parameter1, parameter2, parameter3, parameter4, outline, fill);
                return t;
        }
        return null;
    }
}
