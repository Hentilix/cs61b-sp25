import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private int size = 0;
    private Node root = null;

    private class Node {
        K key;
        V value;
        Node left, right;

        private Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    @Override
    public void put(K key, V value) {
        put(key, value, root);
    }

    private Node put(K key, V value, Node node) {
        if (node == null) {
            size ++ ;
            return new Node(key, value);
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            put(key, value, node.left);
        } else if (cmp > 0) {
            put(key, value, node.right);
        } else {
            node.value = value;
        }
        return node;
    }

    @Override
    public V get(K key) {
        return get(key, root);
    }

    private V get(K key, Node node) {
        if (key == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            return get(key, node.left);
        } else if (cmp > 0) {
            return get(key, node.right);
        } else {
            return node.value;
        }
    }

    @Override
    public boolean containsKey(K key) {
        return containsKey(key, root);
    }

    private boolean containsKey(K key, Node node) {
        if (key == null) {
            return false;
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            return containsKey(key, node.left);
        } else if (cmp > 0) {
            return containsKey(key, node.right);
        } else {
            return true;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keys = new TreeSet<>();
        collectKeys(root, keys);
        return keys;
    }

    private void collectKeys(Node n, Set<K> keys) {
        if (n == null) {
            return;
        }

        collectKeys(n.left, keys);
        keys.add(n.key);
        collectKeys(n.right, keys);
    }

    @Override
    public V remove(K key) {
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return null;
    }
}
