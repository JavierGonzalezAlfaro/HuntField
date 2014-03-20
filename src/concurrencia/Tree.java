package concurrencia;

import java.util.Random;


public class Tree implements FieldItem{
    
    
    public Tree(HuntField field) {
        boolean searchPosition = true;
        Position position;
        while (searchPosition){
            position = new Position(new Random().nextInt(field.getXLength()), new Random().nextInt(field.getYLength()));
            if(field.setItem(this,position)){
                searchPosition = false;
            }
        }
    }
    
    @Override
    public boolean fired(){ 
        return false;
    }
    
    @Override
    public char getType(){
        return 'T';
    }
    
}