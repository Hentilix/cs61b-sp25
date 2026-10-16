package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ArrayDeque61B<T> implements Deque61B<T> {
    private static final int MIN_SHRINK_CAPACITY = 16;
    private static final int HASH_MULTIPLIER = 31;
    private T[] items;
    private int size;
    private int capacity;
    // head is the first element; tail is the next position for addLast.
    private int head;
    private int tail;

    @SuppressWarnings("unchecked")
    public ArrayDeque61B() {
        head = 0;
        tail = 0;
        size = 0;
        capacity = 8;
        items = (T[]) new Object[capacity];
    }

    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        T[] newItems = (T[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newItems[i] = items[Math.floorMod(head + i, capacity)];
        }

        items = newItems;
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
        items[head] = x;
        size++;
    }

    @Override
    public void addLast(T x) {
        if (size == capacity) {
            resize(capacity * 2);
        }
        items[tail] = x;
        tail = Math.floorMod(tail + 1, capacity);
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> ans = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            ans.add(items[Math.floorMod(head + i, capacity)]);
        }
        return ans;
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
        if (isEmpty()) {
            return null;
        }
        shrinkIfNeeded();
        T removed = items[head];
        items[head] = null;
        head = Math.floorMod(head + 1, capacity);
        size--;
        return removed;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        shrinkIfNeeded();
        tail = Math.floorMod(tail - 1, capacity);
        T removed = items[tail];
        items[tail] = null;
        size--;
        return removed;
    }

    private void shrinkIfNeeded() {
        // The specification checks usage before removing an element.
        if (capacity >= MIN_SHRINK_CAPACITY && size <= capacity / 4) {
            resize(capacity / 2);
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return items[Math.floorMod(head + index, capacity)];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T item = get(index);
                index++;
                return item;
            }
        };
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Deque61B<?> deque) || size != deque.size()) {
            return false;
        }
        Iterator<?> otherItems = deque.iterator();
        for (T item : this) {
            if (!Objects.equals(item, otherItems.next())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 1;
        for (T item : this) {
            hash = HASH_MULTIPLIER * hash + Objects.hashCode(item);
        }
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        boolean first = true;
        for (T item : this) {
            if (!first) {
                result.append(", ");
            }
            result.append(item);
            first = false;
        }
        return result.append(']').toString();
    }
}
