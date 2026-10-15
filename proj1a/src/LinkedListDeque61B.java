import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T> {

    private Node sentinel = new Node(null, null, null);
    private int size = 0;

    private class Node {
        T item;
        Node prev;
        Node next;

        private Node(T i, Node p, Node n) {
            this.item = i;
            this.prev = p;
            this.next = n;
        }
    }

    public LinkedListDeque61B() {
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    @Override
    public void addFirst(T x) {
        Node temp = new Node(x, sentinel, sentinel.next);
        sentinel.next.prev = temp;
        sentinel.next = temp;
        size++;
    }

    @Override
    public void addLast(T x) {
        Node temp = new Node(x, sentinel.prev, sentinel);
        sentinel.prev.next = temp;
        sentinel.prev = temp;
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> temp = new ArrayList<>();
        if (this.isEmpty()) {
            return temp;
        } else {
            Node begin = sentinel.next;
            while (begin != sentinel) {
                temp.add(begin.item);
                begin = begin.next;
            }
            return temp;
        }
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public T removeFirst() {
        if (this.isEmpty()) {
            return null;
        } else {
            T returnValue = sentinel.next.item;
            sentinel.next.next.prev = sentinel;
            sentinel.next = sentinel.next.next;
            size--;
            return returnValue;
        }
    }

    @Override
    public T removeLast() {
        if (this.isEmpty()) {
            return null;
        } else {
            T returnValue = sentinel.prev.item;
            sentinel.prev.prev.next = sentinel;
            sentinel.prev = sentinel.prev.prev;
            size--;
            return returnValue;
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= this.size) {
            return null;
        } else {
            Node begin = sentinel.next;
            for (int i = 0; i < index; i++) {
                begin = begin.next;
            }
            return begin.item;
        }
    }

    @Override
    public T getRecursive(int index) {
        if (index < 0 || index >= this.size) {
            return null;
        }
        return getRecursiveHelper(sentinel.next, index);
    }

    private T getRecursiveHelper(Node node, int index) {
        if (index == 0) {
            return node.item;
        } else {
            return getRecursiveHelper(node.next, index - 1);
        }
    }
}
