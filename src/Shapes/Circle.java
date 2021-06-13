/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shapes;

import java.awt.Color;
import java.awt.Point;

/**
 *
 * @author Nour Hesham Shaheen
 */
public class Circle extends Shape  {
    
    private int x, y;
    private int side;
     
    public Circle(int x, int y, int side, Color color, Color fillColor) {
             super(color,fillColor);        

        this.x = x;
        this.y = y;
        this.side = side;

    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public int getCenterX() {
      
        
        return (this.getX()+this.getSide()/2);
    }

    public int getCenterY() {
       
       return (this.getY()+this.getSide()/2);

    }
    
    public int getRadius() {
        return this.getSide()/2;
    }
   
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSide() {
        return side;
    }

    public void setSide(int side) {
        this.side = side;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public boolean contains(int x, int y) {
       // System.out.println("Shapes.Circle.contains() side"+this.getSide()+"x and y and side"+this.getX() + " "+this.getY()+" "+this.getSide()+" center "+this.getCenterX());
        return (((x - this.getCenterX()) * (x - this.getCenterX()) +
                (y - this.getCenterY()) * (y - this.getCenterY())) <= (this.getRadius() * this.getRadius()));

    }
    
     @Override
    public int getNearestCorner(int x, int y) {
        Point p = new Point(x,y);
            double[] distances = {p.distance(this.getX(), this.getY()),
            p.distance(this.getX() + this.getSide(), this.getY()),
            p.distance(this.getX() + this.getSide(), this.getSide() + this.getY()),
            p.distance(this.getX(), this.getY() + this.getSide())};
        int minIndex = -1;
        double minDistance = Long.MAX_VALUE;
        for (int i = 0; i < 4; i++) {
            if (minDistance > distances[i]) {
                minDistance = distances[i];
                minIndex = i;
            }
        }
        return minIndex;    }

    public Circle(Circle circle)
    {
        super(circle);
        this.x=circle.x+20;
        this.color=circle.color;
        this.y=circle.y+20;
        this.fillColor=circle.fillColor;
        this.side=circle.side;
    }
    @Override
    public Shape clone() {
            return new Circle(this);
    }
        @Override
    public boolean equals(Shape shape)
    {
        Circle c = (Circle) shape;
        return (this.x==c.getX()&&this.y==c.getY()&&this.side==c.getSide());//&&this.color==c.getColor()&&this.fillColor==c.getFillColor());
    }
    
        
    @Override
    public void created()
    {
        System.out.println("Circle Created.");
    }
    
}
