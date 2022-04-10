package com.DavidDiaz.Wizard;

public class Stack<T> extends SimpleLinkedList<T> {
    
    /**
     * Inserts an element into the stack
     * @param data
     */
    public void push(T data){
        Node node = new Node(data);
        node.next = head;
        head = node;
        size++;
    }

    /**Deletes the  element at the top
     * 
     */
    public void pop(){
        if(size == 0){
            return;
        }
        head = head.next;
        size--;
    }


    /**
     * Return the element at the top of the stack
     * @return
     */
    public T top(){
        return head.data;
    }


}
