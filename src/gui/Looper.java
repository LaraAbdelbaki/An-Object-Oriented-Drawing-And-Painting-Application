/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Shapes.Shape;
import java.util.ArrayList;

/**
 *
 * @author Nour Hesham Shaheen
 */
public class Looper implements Container{
    
     public ArrayList<Shape> shapes;

   @Override
   public Iterator getIterator() {
      return new ShapeIterator();
   }

   private class ShapeIterator implements Iterator {

      int index;

      @Override
      public boolean hasNext() {
      
         if(index < shapes.size()){
            return true;
         }
         return false;
      }

      @Override
      public Object next() {
      
         if(this.hasNext()){
            return shapes.get(index++);
         }
         return null;
      }		
   }   
    
}
