import java.util.ArrayList;
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
        } else {
            return false;
        }
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
            sentinel.next.next.prev = sentinel;

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
            sentinel.prev.prev.next = sentinel;

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

    public LinkedListDeque61B() {
        sentinel = new Node<>(null, null, null);

        sentinel.next = sentinel;
        sentinel.prev = sentinel;

        size = 0;
    }
}
