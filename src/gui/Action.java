/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Shapes.Shape;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author Nour Hesham Shaheen
 */
public class Action {
    
    private Shape old_shape;
    private Shape new_shape;
    private Operation op;
    private ArrayList<Shape> shapes;
    private Color oldColor, newColor;
    public static Stack<Action> actions_undo = new Stack<Action>();
    public static Stack<Action> actions_redo = new Stack<Action>();
    
    public static enum Operation {
     RESIZE, MOVE, DELETE, CLEAR, UNDO, REDO, COLOR, COPY, DRAW, CLONE, PASTE, BACKGROUND};

    public Action(Shape old_shape, Color oldColor, Color newColor) {
        this.old_shape = old_shape;
        this.oldColor = oldColor;
        this.newColor = newColor;
        this.op = Operation.COLOR;
    }
    
    public Action(Color bg_old, Color bg_new) {
        
        this.oldColor = bg_old;
        this.newColor = bg_new;
        this.op = Operation.BACKGROUND;

    }

    public Color getOldColor() {
        return oldColor;
    }

    public void setOldColor(Color oldColor) {
        this.oldColor = oldColor;
    }

    public Color getNewColor() {
        return newColor;
    }

    public void setNewColor(Color newColor) {
        this.newColor = newColor;
    }
      
    public Action(ArrayList<Shape> shapes)
    {
        this.shapes = shapes;
        this.op = Operation.CLEAR;
    }

    public Shape getOld_shape() {
        return old_shape;
    }

    public void setOld_shape(Shape old_shape) {
        this.old_shape = old_shape;
    }

    public Shape getNew_shape() {
        return new_shape;
    }

    public void setNew_shape(Shape new_shape) {
        this.new_shape = new_shape;
    }

    public Operation getOp() {
        return op;
    }

    public void setOp(Operation op) {
        this.op = op;
    }

    public ArrayList<Shape> getShapes() {
        return shapes;
    }

    public void setShapes(ArrayList<Shape> shapes) {
        this.shapes = shapes;
    }

    public Action(Shape old_shape, Shape new_shape, Operation op)
    {
        this.old_shape = old_shape;
        this.new_shape = new_shape;
        this.op = op;
    }
    
}
