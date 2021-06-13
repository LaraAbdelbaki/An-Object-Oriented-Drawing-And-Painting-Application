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
public class Ellipse extends Shape {
    
    private int x, y;
    private int height, width;

    public Ellipse(int x, int y, int width, int height, Color color, Color fillColor) {
     super(color,fillColor);        
     this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    
    public double getCenterX()
    {
        return this.getX() + (this.getWidth()/2);
    }
    
    public double getCenterY()
    {
        return this.getY() + (this.getHeight()/2); 
    }

    @Override
    public boolean contains(int x, int y) {
        
        double p = 2.0;
        if(this.getWidth() != 0 && this.getHeight() != 0)
            p = Math.pow((x - this.getCenterX()), 2) / Math.pow(this.getWidth()/2, 2)  
            + Math.pow((y - this.getCenterY()), 2) / Math.pow(this.getHeight()/2, 2);
        
        return p<=1;  
    }       
    @Override
    public int getNearestCorner(int x, int y) {
        Point p = new Point(x, y);
        double[] distances = {
            p.distance(this.getX(), this.getY()),
            p.distance(this.getX() + this.getWidth(), this.getY()),
            p.distance(this.getX() + this.getWidth(), this.getHeight() + this.getY()),
            p.distance(this.getX(), this.getY() + this.getHeight())};
        int minIndex = -1;
        double minDistance = Long.MAX_VALUE;
        for (int i = 0; i < 4; i++) {
            if (minDistance > distances[i]) {
                minDistance = distances[i];
                minIndex = i;
            }
        }
        return minIndex;    }

   
    public Ellipse(Ellipse ellipse)
    {
        super(ellipse);
        this.x=ellipse.x+20;
        this.color=ellipse.color;
        this.y=ellipse.y+20;
        this.fillColor=ellipse.fillColor;
        this.width=ellipse.width;
        this.height=ellipse.height;
    }
    @Override
    public Shape clone() {
            return new Ellipse(this);
    }
    
       @Override
    public boolean equals(Shape shape)
    {
        boolean temp;
        Ellipse el = (Ellipse) shape;
        temp =this.getX()==el.getX()&&this.getY()==el.getY()&&this.getWidth()==el.getWidth()&&this.getHeight()==el.getHeight();//&&this.getColor()==el.getColor()&&this.getFillColor()==el.getFillColor();
        return temp;
    }
    
        
    @Override
    public void created()
    {
        System.out.println("Ellipse Created.");
    }
}
