package Shapes;

import java.awt.Color;


public abstract class Shape implements Cloneable{
    
    Color color;
    Color fillColor;
    Color temp;
    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }
    
    public Shape(Color color, Color fillColor) {
        this.color = color;
        this.fillColor = fillColor;
    }
    
    public Color getColor()
    {
       return this.color;
    }

    public Color getTemp() {
        return Color.GREEN;
    }

    public void setTemp(Color temp) {
        this.temp = temp;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    
    
    public Shape()
    {
        
    }
    public Shape(Shape shape)
    {
    }
    
    @Override
    public abstract Shape clone();
    
    
    public abstract boolean contains(int x, int y);
    public abstract int getNearestCorner(int x, int y);
    public abstract boolean equals(Shape shape);
    public abstract void created();
    
    public final void template()
    {
        created();
    }
 
}
