import deque.ArrayDeque61B;
import deque.Deque61B;
import deque.LinkedListDeque61B;
import deque.Maximizer61B;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class Maximizer61BTest {
    @Test
    public void emptyIterablesReturnNull() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();
        assertThat(Maximizer61B.max(deque)).isNull();
        assertThat(Maximizer61B.max(deque, Comparator.naturalOrder())).isNull();
        assertThat(Maximizer61B.max(List.<Integer>of())).isNull();
    }

    @Test
    public void naturalOrderHandlesNegativeValuesAndDuplicates() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addFirst(-9);
        deque.addLast(-2);
        deque.addFirst(-2);
        deque.addLast(-12);
        assertThat(Maximizer61B.max(deque)).isEqualTo(-2);
        assertThat(deque.toList()).containsExactly(-2, -9, -2, -12).inOrder();
        assertThat(Maximizer61B.max(List.of(42))).isEqualTo(42);
    }

    @Test
    public void comparatorChangesTheMeaningOfMaximum() {
        Deque61B<String> deque = new LinkedListDeque61B<>();
        deque.addLast("z");
        deque.addLast("longest");
        deque.addLast("abc");
        assertThat(Maximizer61B.max(deque)).isEqualTo("z");
        assertThat(Maximizer61B.max(deque, Comparator.comparingInt(String::length)))
                .isEqualTo("longest");
        assertThat(Maximizer61B.max(deque, Comparator.reverseOrder())).isEqualTo("abc");
    }

    @Test
    public void comparatorSupportsNonComparableObjects() {
        Object first = new Object();
        Object second = new Object();
        Comparator<Object> order = Comparator.comparingInt(item -> item == second ? 100 : 0);
        assertThat(Maximizer61B.max(List.of(first, second), order)).isSameInstanceAs(second);
    }

    @Test
    public void acceptsAnIterableWithOneTraversal() {
        Iterator<Integer> iterator = List.of(4, 20, 1, 9).iterator();
        Iterable<Integer> iterable = () -> iterator;
        assertThat(Maximizer61B.max(iterable)).isEqualTo(20);
    }
}
