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
public class Triangle extends Shape { // always Isoceles triangle for easier user interface just like MS Paint
    
    private int x1, y1;
    private int x2, y2;
    private int x3,y3;

    public int getX3() {
        return x3;
    }

    public void setX3(int x3) {
        this.x3 = x3;
    }

    public int getY3() {
        return this.y3;
    }

    public void setY3(int y3) {
        this.y3 = y3;
    }

 public Triangle(int x1, int y1, int x2, int y2, int x3, int y3, Color color,Color fillColor) {
             super(color,fillColor);        

        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
        
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
     public double area(int x1, int y1, int x2, int y2, int x3, int y3)
    {
       return Math.abs((x1*(y2-y3) + x2*(y3-y1)+x3*(y1-y2))/2.0);
    }

    private int sign (int x1, int y1, int x2, int y2, int x3, int y3)
    {
        return (x1 - x3) * (y2 - y3) - (x2 - x3) * (y1 - y3);
    }
  
    @Override
    public boolean contains(int x, int y) {
       int d1, d2, d3;
       boolean has_neg, has_pos;

        d1 = sign(x, y, this.getX1(), this.getY1(), this.getX2(), this.getY2());
        d2 = sign(x, y, this.getX2(), this.getY2(), this.getX3(), this.getY3());
        d3 = sign(x, y, this.getX3(), this.getY3(), this.getX1(), this.getY1());

        has_neg = (d1 < 0) || (d2 < 0) || (d3 < 0);
        has_pos = (d1 > 0) || (d2 > 0) || (d3 > 0);

        return !(has_neg && has_pos);   
    }
    
    public int getNearestCorner(int a, int b) {
        Point p = new Point();
        p.x=a;
        p.y=b;
        double[] distances = new double[3];
  
            distances[0] = (new Point(this.getX1(), this.getY1()).distance(p));
            distances[1] = (new Point(this.getX1() - (this.getX2() - this.getX1()), this.getY2()).distance(p));
            distances[2] = (new Point(this.getX2(), this.getY2()).distance(p));

        int minIndex = -1;
        double minDistance = Long.MAX_VALUE;
        for (int i = 0; i < 3; i++) {
            if (minDistance > distances[i]) {
                minDistance = distances[i];
                minIndex = i;
            }
        }
        return minIndex;    }

       public Triangle(Triangle triangle)
    {
        super(triangle);
        this.color=triangle.color;
        this.fillColor = triangle.getFillColor();
        this.x1 = triangle.getX1() + 20;
        this.x2 = triangle.getX2() + 20;
        this.x3 = triangle.getX3() + 20;
        this.y1 = triangle.getY1() + 20;
        this.y2 = triangle.getY2() + 20;
        this.y3 = triangle.getY3() + 20;
       

        
                                
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }
    @Override
    public Shape clone() {
            return new Triangle(this);
    }
    @Override
    public boolean equals(Shape shape)
    {
        Triangle t = (Triangle) shape;
        return (this.x1==t.getX1()&&this.y1==t.getY1()&&this.x2==t.getX2()&&this.y2==t.getY2()&&this.x3==t.getX3()&&this.y3==t.getY3());//&&this.color==t.getColor()&&this.fillColor==t.getFillColor());
    }
    
    @Override
    public void created()
    {
        System.out.println("Triangle Created.");
    }
    
}
