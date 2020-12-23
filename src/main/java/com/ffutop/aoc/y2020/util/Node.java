package com.ffutop.aoc.y2020.util;

/**
 * @author fangfeng
 * @since 2020-12-23
 */
public class Node {

    private int value;
    private Node prev;
    private Node next;

    public Node(int value, Node prev, Node next) {
        this.value = value;
        this.prev = prev;
        this.next = next;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Node getPrev() {
        return prev;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
