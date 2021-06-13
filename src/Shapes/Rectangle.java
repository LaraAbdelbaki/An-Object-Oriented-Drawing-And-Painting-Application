/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shapes;

import java.awt.Color;
import java.awt.Point;


public class Rectangle extends Shape {
    
    private int x, y;
    private int height, width;

    public Rectangle(int x, int y, int width,int height, Color color, Color fillColor) {
             super(color,fillColor);        

        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
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

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public boolean contains(int x, int y) {
        if(x >= this.getX() && x <= (this.getX() + this.getWidth()) && y >= this.getY() && y <= (this.getY() + this.getHeight()))
        {
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
            p.distance(this.getX() + this.getWidth(), this.getY()),
            p.distance(this.getX() + this.getWidth(),this.getY() + this.getHeight()),
            p.distance(this.getX(), this.getY() + this.getHeight())
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

       public Rectangle(Rectangle rectangle)
    {
        super(rectangle);
        this.x=rectangle.x+20;
        this.color=rectangle.color;
        this.y=rectangle.y+20;
        this.fillColor=rectangle.fillColor;
        this.width=rectangle.width;
        this.height=rectangle.height;
    }
    @Override
    public Shape clone() {
            return new Rectangle(this);
    }
    @Override
    public boolean equals(Shape shape)
    {
        Rectangle r = (Rectangle) shape;
        return (this.x==r.getX()&&this.y==r.getY()&&this.width==r.getWidth()&&this.height==r.getHeight());//&&this.color==r.getColor()&&this.fillColor==r.getFillColor());
    }
        
    @Override
    public void created()
    {
        System.out.println("Rectangle Created.");
    }
   
  }


   