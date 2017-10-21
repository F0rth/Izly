package com.fasterxml.jackson.databind.util;

public final class LinkedNode<T> {
    private LinkedNode<T> next;
    private final T value;

    public LinkedNode(T t, LinkedNode<T> linkedNode) {
        this.value = t;
        this.next = linkedNode;
    }

    public static <ST> boolean contains(LinkedNode<ST> linkedNode, ST st) {
        LinkedNode next;
        while (next != null) {
            if (next.value() == st) {
                return true;
            }
            next = next.next();
        }
        return false;
    }

    public final void linkNext(LinkedNode<T> linkedNode) {
        if (this.next != null) {
            throw new IllegalStateException();
        }
        this.next = linkedNode;
    }

    public final LinkedNode<T> next() {
        return this.next;
    }

    public final T value() {
        return this.value;
    }
}
