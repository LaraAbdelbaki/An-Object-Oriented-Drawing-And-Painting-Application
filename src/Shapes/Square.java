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
public class Square extends Shape {
    
    private int x,y;
    private int side;

    public Square(int x, int y, int side, Color color,Color fillColor) {
             super(color,fillColor);        

        this.x = x;
        this.y = y;
        this.side = side;
    }

    public int getX() {
        return x;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
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
if(x >= this.getX() && x <= (this.getX() + this.getSide()) && y >= this.getY() && y <= (this.getY() + this.getSide()))
        {
           // if(x <= (this.getX() + 20) || x>=(this.getX() + (this.getSide() - 20)) || y<=(this.getY() +20) || y>=(this.getY() + this.getSide()-20))
                return true;
            
        }
      return false;  
    }
    
    @Override
    public int getNearestCorner(int a, int b) {
        Point p = new Point();
        p.x=a;
        p.y=b;
        double[] distances = 
        {
            p.distance(this.getX(), this.getY()),
            p.distance(this.getX() + this.getSide(), this.getY()),
            p.distance(this.getX() + this.getSide(),this.getY() + this.getSide()),
            p.distance(this.getX(), this.getY() + this.getSide())
        };
        int minIndex = -1;
        double minDistance = Long.MAX_VALUE;
        for (int i = 0; i < 4; i++) {
            if (minDistance > distances[i]) {
                minDistance = distances[i];
                minIndex = i;
            }
        }
        return minIndex;

    }
       public Square(Square square)
    {
        super(square);
        this.x=square.x+20;
        this.color=square.color;
        this.y=square.y+20;
        this.fillColor=square.fillColor;
        this.side=square.side;
    }
    @Override
    public Shape clone() {
            return new Square(this);
    }
    @Override
    public boolean equals(Shape shape)
    {
        Square s = (Square) shape;
        if(this.getX()==s.getX()&&this.getY()==s.getY()&&this.getSide()==s.getSide())//&&this.getColor()==s.getColor()&&this.getFillColor()==s.getFillColor())
        {
            return true;
        }
     return false;
    }
    
        
    @Override
    public void created()
    {
        System.out.println("Square Created.");
    }

}
    

