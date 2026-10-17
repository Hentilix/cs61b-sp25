package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    @Override
    public void put(K key, V value) {
        Node existing = findNode(key);
        if (existing != null) {
            existing.value = value;
            return;
        }

        int index = Math.floorMod(key.hashCode(), capacity);
        buckets[index].add(new Node(key, value));
        size++;

        while ((double) size / capacity > loadFactor) {
            resize(capacity * 2);
        }
    }

    private void resize(int newCapacity) {
        Collection<Node>[] newBuckets = (Collection<Node>[]) new Collection[newCapacity];

        for (int i = 0; i< newCapacity; i++) {
            newBuckets[i] = createBucket();
        }

        for(Collection<Node> bucket : buckets) {
            for (Node node : bucket) {
                int index = Math.floorMod(node.key.hashCode(), newCapacity);
                newBuckets[index].add(node);
            }
        }

        buckets = newBuckets;
        capacity = newCapacity;
    }

    @Override
    public V get(K key) {
        Node node = findNode(key);
        return node == null ? null : node.value;
    }

    @Override
    public boolean containsKey(K key) {
        return findNode(key) != null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        for (Collection<Node> bucket : buckets) {
            bucket.clear();
        }
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (Collection<Node> bucket : buckets) {
            for (Node node : bucket) {
                keys.add(node.key);
            }
        }
        return keys;
    }

    @Override
    public V remove(K key) {
        int index = Math.floorMod(key.hashCode(), capacity);
        Iterator<Node> iterator = buckets[index].iterator();

        while (iterator.hasNext()) {
            Node node = iterator.next();
            if (node.key.equals(key)) {
                V returnValue = node.value;
                iterator.remove();
                size--;
                return returnValue;
            }
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return new MyHashMapIterator();
    }

    public class MyHashMapIterator implements Iterator<K> {
        private int bucketIndex = 0;
        private Iterator<Node> nodeIterator;

        private void advanceToNextNonEmptyBucket() {
            while ((nodeIterator == null || !nodeIterator.hasNext())
                    && bucketIndex < buckets.length) {
                nodeIterator = buckets[bucketIndex].iterator();
                bucketIndex++;
            }
        }

        @Override
        public boolean hasNext() {
            advanceToNextNonEmptyBucket();
            return nodeIterator != null && nodeIterator.hasNext();
        }

        @Override
        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return nodeIterator.next().key;
        }
    }

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private int size;
    private int capacity;
    private double loadFactor;
    // You should probably define some more!

    /** Constructors */
    public MyHashMap() {
        this(16, 0.75);
    }

    public MyHashMap(int initialCapacity) {
        this(initialCapacity, 0.75);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        if (initialCapacity <= 0 || loadFactor <= 0) {
            throw new IllegalArgumentException();
        }
        this.size = 0;
        this.loadFactor = loadFactor;
        this.capacity = initialCapacity;

        this.buckets = (Collection<Node>[]) new Collection[initialCapacity];
        for (int i = 0; i < initialCapacity; i++) {
            buckets[i] = createBucket();
        }
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *  Note that that this is referring to the hash table bucket itself,
     *  not the hash map itself.
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        // TODO: Fill in this method.
        return new LinkedList<>();
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!
    private Node findNode(K key) {
        int index = Math.floorMod(key.hashCode(), capacity);
        for (Node node : buckets[index]) {
            if (node.key.equals(key)) {
                return node;
            }
        }
        return null;
    }
}
