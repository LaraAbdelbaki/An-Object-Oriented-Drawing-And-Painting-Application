/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shapes;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;

/**
 *
 * @author Nour Hesham Shaheen
 */
public class Line extends Shape {
    
    private int x1, y1;
    private int x2, y2;

    public Line(int x1, int y1, int x2, int y2, Color color, Color fillColor) {
        super(color,fillColor);
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    @Override
    public boolean contains(int x, int y) {
       boolean check = false;
       double lineSlope=0;
       double pointSlope1=0;
       double pointSlope2=0;
       if(this.getX1()!=this.getX2() && x!=this.getX1() && x !=this.getX2())
       {
         lineSlope = (this.getY2() - this.getY1()) / (this.getX2()-this.getX1());
         pointSlope1 = (y - this.getY1()) / (x - this.getX1());
         pointSlope2 = (y - this.getY2()) / (x - this.getX2());
        }
        check = Math.abs(lineSlope - pointSlope1) <= 0.01 
                && Math.abs(lineSlope - pointSlope2) <= 0.01
                && Math.min(this.getX1(), this.getX2()) <= x
                && Math.max(this.getX1(), this.getX2()) >= x
                && Math.min(this.getY1(), this.getY2()) <= y
                && Math.max(this.getY1(), this.getY2()) >= y; 
        return check;
    }

    @Override
    public int getNearestCorner(int x, int y) {
                Point p1 = new Point(this.getX1(),this.getY1());
                Point p2 = new Point(this.getX2(),this.getY2());
                Point p = new Point(x,y);
            return p1.distance(p) < p2.distance(p) ? 0 : 1;
    }  

    public Line(Line line)
    {
        super(line);
        this.color=line.color;
        this.fillColor = line.getFillColor();
        this.x1 = line.getX1() + 20;
        this.x2 = line.getX2() + 20;
        this.y1 = line.getY1() + 20;
        this.y2 = line.getY2() + 20;
    }
    @Override
    public Shape clone() {
            return new Line(this);
    }
    @Override
    public boolean equals(Shape shape)
    {
        Line l = (Line) shape;
        return (this.x1==l.getX1()&&this.y1==l.getY1()&&this.x2==l.getX2()&&this.y2==l.getY2());//&&this.color==l.getColor()&&this.fillColor==l.getFillColor());
    }
        
    @Override
    public void created()
    {
        System.out.println("Line Created.");
    }
    
}
