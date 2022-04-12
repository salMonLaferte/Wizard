package com.DavidDiaz.Wizard;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleLinkedList<T> {
    protected class Node{
        T data;
        Node next;
        public Node(T data){
            this.data = data;
            this.next = null;
        }
    };


    public class IteratorLinkedList implements Iterator<T>{
        Node next;

        public IteratorLinkedList(){
            next = head;
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public T next() {
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            T n = next.data; 
            next = next.next; 
            return n;
        }

    }

    protected Node head;
    protected Node tail;
    protected int size; 

    public SimpleLinkedList(){
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Returns list size
     * @return
     */
    public int getSize(){
        return size;
    }

    /**
     * Returns true if list is empty
     */
    public boolean isEmpty(){
        if(size == 0){
            return true;
        }
        return false;
    }

    /**
     * Adds an element to the end of the list
     * @param data
     */
    public void add(T data){
        Node node = new Node(data);
        if(size == 0){
            head = node;
            tail = node;
            size++;
            return;
        }
        tail.next = node;
        tail = node;
        size++;
    }

    /**
     * Deletes the first element with the specified data
     * @param data
     * @return
     */
    public boolean delete(T data){
        if(size == 0)
            return false;
        Node current = head;
        if(current.data.equals(data)){
            head = current.next;
            size--;
            if(head == null)
                tail = null;
            return true;
        }
        while(current.next != null){
            if(current.next.data.equals(data)){
                current.next = current.next.next;
                size--;
                if(head == null)
                    tail = null;
                return true;
            }
        }
        return false;   
    }

    /**
     * Clear list
     */
    public void clear(){
        head = null;
        tail = null;
        size = 0;
    }

    /**Returns an iterator pointing to the head of the list
     * 
     */
    public Iterator<T> begin(){
        Iterator<T> it = new IteratorLinkedList();
        return it;
    }

    /**
     * Returns true if list contains data
     */
    public boolean contains(T data){
        if(size == 0)
            return false;
        Node current = head;
        while(current != null){
            if(current.data.equals(data)){
                return true;
            }
            current = current.next;
        }
        return false;
    }
}
