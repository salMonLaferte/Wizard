package com.DavidDiaz.Wizard;

/**
 * Class to store two different types of objects 
 */
public class MyPair<T,E> {
    T first;
    E second;
    
    public MyPair(T first, E second){
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString(){
        return first + " : " + second;
    }
}
