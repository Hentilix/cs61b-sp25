package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayDeque61B<T> implements Deque61B<T> {

    private int size;
    private int capacity;
    private int head;
    private int tail;

    private T[] values = (T[]) new Object[8];

    public void resize(int newCapacity) {
        T[] new_values = (T[]) new Object[newCapacity];
        for (int i = 0; i < size; i ++ ) {
            new_values[i] = values[Math.floorMod(head + i, capacity)];
        }

        values = new_values;
        capacity = newCapacity;
        head = 0;
        tail = size;
    }

    @Override
    public void addFirst(T x) {
        if (size == capacity) {
            resize(capacity * 2);
        }
        head = (head == 0) ? capacity - 1 : head - 1;
        values[head] = x;
        size ++ ;
    }

    @Override
    public void addLast(T x) {
        if (size == capacity) {
            resize(capacity * 2);
        }
        values[tail] = x;
        tail = Math.floorMod(tail + 1, capacity);
        size ++ ;
    }

    @Override
    public List<T> toList() {
        List<T> ans = new ArrayList<>();
        for (int i = 0; i < size; i ++ ) {
            ans.add(values[Math.floorMod(head + i, capacity)]);
        }
        return ans;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
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
            T returnEle = values[head];
            head = Math.floorMod(head + 1, capacity);
            size -- ;
            return returnEle;
        }
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        } else {
            T returnEle = values[Math.floorMod(tail - 1, capacity)];
            tail = Math.floorMod(tail - 1, capacity);
            size -- ;
            return returnEle;
        }
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        return values[Math.floorMod(head + index, capacity)];
    }

    public T getRecursiveHelper(int begin, int index) {
        if (index == 0) {
            return values[begin];
        } else {
            return getRecursiveHelper(Math.floorMod(begin + 1, capacity), index - 1);
        }
    }

    @Override
    public T getRecursive(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        return getRecursiveHelper(head, index);
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDeque61BIterator();
    }

    private class ArrayDeque61BIterator implements Iterator<T> {
        private int position;
        private int count;

        ArrayDeque61BIterator() {
            position = head;
        }

        @Override
        public boolean hasNext() {
            if (count < size) {
                return true;
            }
            return false;
        }

        @Override
        public T next() {
            T itemToReturn = values[position];
            position = Math.floorMod(position + 1, capacity);
            count ++ ;
            return itemToReturn;
        }
    }

    public boolean equals(ArrayDeque61B other) {
        if (this.size != other.size) {
            return false;
        } else {
            int thisBegin = this.head;
            int otherBegin = other.head;
            boolean flag = true;
            for (int i = 0; i < this.size; i ++ ) {
                if (!this.values[Math.floorMod(thisBegin + i, capacity)].equals(other.values[Math.floorMod(otherBegin + i, capacity)])) {
                    flag = false;
                    break;
                }
            }
            return flag;
        }
    }

    public String toString() {
        return toList().toString();
    }

    public ArrayDeque61B() {
        head = 0;
        tail = 0;
        size = 0;
        capacity = 8;
    }

}
