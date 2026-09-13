package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T>{
    private static class Node<T> {
        public T item;
        public Node<T> next;
        public Node<T> prev;

        public Node(T i, Node<T> n, Node<T> p) {
            item = i;
            next = n;
            prev = p;
        }

    }

    private Node<T> sentinel;
    private int size;

    @Override
    public void addFirst(T x) {
        Node<T> first = new Node<>(x, sentinel.next, sentinel);

        sentinel.next.prev = first;
        sentinel.next = first;

        size ++ ;
    }

    @Override
    public void addLast(T x) {
        Node<T> last = new Node<>(x, sentinel, sentinel.prev);

        sentinel.prev.next = last;
        sentinel.prev = last;

        size ++ ;
    }

    @Override
    public List<T> toList() {
        List<T> ans = new ArrayList<>();
        Node<T> begin = sentinel.next;
        while (begin != sentinel) {
            ans.add(begin.item);
            begin = begin.next;
        }
        return ans;
    }

    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        } else {
            Node<T> return_node = sentinel.next;
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;

            size -- ;
            return return_node.item;
        }
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        } else {
            Node<T> return_node = sentinel.prev;
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;

            size -- ;
            return return_node.item;
        }
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        } else {
            Node<T> ans = sentinel.next;
            for (int i = 0; i < index; i ++ ) {
                ans = ans.next;
            }
            return ans.item;
        }
    }

    private T getRecursiveHelper(Node<T> n, int index) {
        if (index == 0) {
            return n.item;
        } else {
            return getRecursiveHelper(n.next, index - 1);
        }
    }

    @Override
    public T getRecursive(int index) {
        if (index >= size || index < 0) {
            return null;
        } else {
            return getRecursiveHelper(sentinel.next, index);
        }
    }

    private class LinkedListDeque61BIterator implements Iterator<T> {
        private int position;
        Node<T> now = sentinel.next;

        LinkedListDeque61BIterator() {
            position = 0;
        }

        @Override
        public boolean hasNext() {
            if (position < size) {
                return true;
            }
            return false;
        }

        @Override
        public T next() {
            Node<T> itemToReturn = now;
            position ++ ;
            now = now.next;
            return itemToReturn.item;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListDeque61BIterator();
    }

    public boolean equals(LinkedListDeque61B other) {
        if (this.size != other.size) {
            return false;
        } else {
            boolean flag = true;
            Node<T> comp1 = this.sentinel.next;
            Node<T> comp2 = other.sentinel.next;
            for (int i = 0; i < size; i ++ ) {
                if (comp1.item != comp2.item) {
                    flag = false;
                    break;
                } else {
                    comp1 = comp1.next;
                    comp2 = comp2.next;
                }
            }
            return flag;
        }
    }

    public String toString() {
        return toList().toString();
    }

    public LinkedListDeque61B() {
        sentinel = new Node<>(null, null, null);

        sentinel.next = sentinel;
        sentinel.prev = sentinel;

        size = 0;
    }
}
