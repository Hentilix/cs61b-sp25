import deque.ArrayDeque61B;
import deque.Deque61B;
import deque.LinkedListDeque61B;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DequeObjectMethodsTest {
    private List<Deque61B<String>> implementations() {
        return List.of(new ArrayDeque61B<>(), new LinkedListDeque61B<>());
    }

    @Test
    public void emptyIteratorsAndStrings() {
        for (Deque61B<String> deque : implementations()) {
            Iterator<String> iterator = deque.iterator();
            assertThat(iterator.hasNext()).isFalse();
            assertThrows(NoSuchElementException.class, iterator::next);
            assertThat(deque.toString()).isEqualTo("[]");
        }
    }

    @Test
    public void iteratorsAreOrderedIndependentAndExhaustible() {
        for (Deque61B<String> deque : implementations()) {
            deque.addLast("middle");
            deque.addFirst("front");
            deque.addLast("back");
            Iterator<String> first = deque.iterator();
            Iterator<String> second = deque.iterator();
            assertThat(first.hasNext()).isTrue();
            assertThat(first.hasNext()).isTrue();
            assertThat(first.next()).isEqualTo("front");
            assertThat(first.next()).isEqualTo("middle");
            assertThat(second.next()).isEqualTo("front");
            assertThat(first.next()).isEqualTo("back");
            assertThat(first.hasNext()).isFalse();
            assertThrows(NoSuchElementException.class, first::next);
            assertThat(deque).containsExactly("front", "middle", "back").inOrder();
            assertThat(deque.toString()).isEqualTo("[front, middle, back]");
            assertThat(deque.size()).isEqualTo(3);
        }
    }

    @Test
    public void toStringKeepsSeparatorsAroundEmptyStrings() {
        for (Deque61B<String> deque : implementations()) {
            deque.addLast("");
            deque.addLast("middle");
            deque.addLast("");
            assertThat(deque.toString()).isEqualTo("[, middle, ]");
        }
    }

    @Test
    public void equalityWorksAcrossImplementationsAndUsesElementValues() {
        for (Deque61B<String> left : implementations()) {
            for (Deque61B<String> right : implementations()) {
                assertThat(left.equals(right)).isTrue();
                left.addFirst(new String("same"));
                right.addLast(new String("same"));
                assertThat(left.equals(right)).isTrue();
                assertThat(right.equals(left)).isTrue();
                assertThat(left.hashCode()).isEqualTo(right.hashCode());
                left.removeFirst();
            }
        }
    }

    @Test
    public void equalityRejectsDifferentSizesOrdersAndTypes() {
        for (Deque61B<String> deque : implementations()) {
            deque.addLast("a");
            deque.addLast("b");
            assertThat(deque.equals(deque)).isTrue();
            assertThat(deque.equals(null)).isFalse();
            assertThat(deque.equals(List.of("a", "b"))).isFalse();
            Deque61B<String> other = new ArrayDeque61B<>();
            other.addLast("a");
            assertThat(deque.equals(other)).isFalse();
            other.addFirst("b");
            assertThat(deque.equals(other)).isFalse();
            Deque61B<Integer> integers = new LinkedListDeque61B<>();
            integers.addLast(1);
            integers.addLast(2);
            assertThat(deque.equals(integers)).isFalse();
        }
    }

    @Test
    public void iterationAndEqualitySurviveWrappingAndResizing() {
        Deque61B<Integer> array = new ArrayDeque61B<>();
        Deque61B<Integer> linked = new LinkedListDeque61B<>();
        for (int i = 0; i < 128; i++) {
            array.addFirst(i);
            linked.addFirst(i);
        }
        for (int i = 0; i < 120; i++) {
            array.removeLast();
            linked.removeLast();
        }
        for (int i = 128; i < 256; i++) {
            array.addLast(i);
            linked.addLast(i);
        }
        assertThat(array).containsExactlyElementsIn(linked).inOrder();
        assertThat(array.equals(linked)).isTrue();
        assertThat(linked.equals(array)).isTrue();
        assertThat(array.toString()).isEqualTo(linked.toString());
    }

    @Test
    public void recursiveAccessIsOnlySupportedByLinkedDeque() {
        Deque61B<String> linked = new LinkedListDeque61B<>();
        assertThat(linked.getRecursive(0)).isNull();
        linked.addFirst("middle");
        linked.addFirst("front");
        linked.addLast("back");
        for (int i = 0; i < 3; i++) {
            assertThat(linked.getRecursive(i)).isEqualTo(linked.get(i));
        }
        assertThat(linked.getRecursive(-1)).isNull();
        assertThat(linked.getRecursive(3)).isNull();
        Deque61B<String> array = new ArrayDeque61B<>();
        assertThrows(UnsupportedOperationException.class, () -> array.getRecursive(0));
    }

    @Test
    public void linkedDequeCanBeClearedAndReused() {
        Deque61B<Integer> deque = new LinkedListDeque61B<>();
        assertThat(deque.removeFirst()).isNull();
        assertThat(deque.removeLast()).isNull();
        deque.addFirst(1);
        assertThat(deque.removeLast()).isEqualTo(1);
        deque.addLast(2);
        assertThat(deque.removeFirst()).isEqualTo(2);
        assertThat(deque.size()).isEqualTo(0);
        assertThat(deque.isEmpty()).isTrue();
        assertThat(deque.toList()).isEmpty();
        deque.addFirst(3);
        assertThat(deque.get(0)).isEqualTo(3);
        assertThat(deque.get(-1)).isNull();
        assertThat(deque.get(1)).isNull();
    }
}
