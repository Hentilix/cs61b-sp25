import deque.ArrayDeque61B;
import deque.Deque61B;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.google.common.truth.Truth.assertThat;

public class ArrayDeque61BTest {
    @Test
    public void emptyDeque() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();
        assertThat(deque.isEmpty()).isTrue();
        assertThat(deque.size()).isEqualTo(0);
        assertThat(deque.toList()).isEmpty();
        assertThat(deque.get(0)).isNull();
        assertThat(deque.removeFirst()).isNull();
        assertThat(deque.removeLast()).isNull();
        assertThat(deque.size()).isEqualTo(0);
        assertThat(deque.isEmpty()).isTrue();
    }

    @Test
    public void addAtBothEnds() {
        Deque61B<String> deque = new ArrayDeque61B<>();
        deque.addFirst("middle");
        deque.addFirst("front");
        deque.addLast("back");
        assertThat(deque.toList()).containsExactly("front", "middle", "back").inOrder();
        assertThat(deque.size()).isEqualTo(3);
        assertThat(deque.isEmpty()).isFalse();
    }

    @Test
    public void addLastToEmptyAndNonempty() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addLast(1);
        deque.addLast(2);
        assertThat(deque.toList()).containsExactly(1, 2).inOrder();
    }

    @Test
    public void getUsesLogicalIndices() {
        Deque61B<String> deque = new ArrayDeque61B<>();
        deque.addLast("middle");
        deque.addFirst("front");
        deque.addLast("back");
        assertThat(deque.get(0)).isEqualTo("front");
        assertThat(deque.get(1)).isEqualTo("middle");
        assertThat(deque.get(2)).isEqualTo("back");
        assertThat(deque.get(-1)).isNull();
        assertThat(deque.get(3)).isNull();
        assertThat(deque.get(Integer.MAX_VALUE)).isNull();
    }

    @Test
    public void removeFirstThroughEmpty() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        assertThat(deque.removeFirst()).isEqualTo(1);
        assertThat(deque.removeFirst()).isEqualTo(2);
        assertThat(deque.size()).isEqualTo(1);
        assertThat(deque.toList()).containsExactly(3);
        assertThat(deque.removeFirst()).isEqualTo(3);
        assertThat(deque.removeFirst()).isNull();
        assertThat(deque.size()).isEqualTo(0);
        assertThat(deque.isEmpty()).isTrue();
        deque.addFirst(4);
        assertThat(deque.toList()).containsExactly(4);
        assertThat(deque.removeLast()).isEqualTo(4);
    }

    @Test
    public void removeLastThroughEmpty() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addFirst(3);
        deque.addFirst(2);
        deque.addFirst(1);
        assertThat(deque.removeLast()).isEqualTo(3);
        assertThat(deque.removeLast()).isEqualTo(2);
        assertThat(deque.size()).isEqualTo(1);
        assertThat(deque.toList()).containsExactly(1);
        assertThat(deque.removeLast()).isEqualTo(1);
        assertThat(deque.removeLast()).isNull();
        assertThat(deque.size()).isEqualTo(0);
        assertThat(deque.isEmpty()).isTrue();
        deque.addLast(4);
        assertThat(deque.toList()).containsExactly(4);
        assertThat(deque.removeFirst()).isEqualTo(4);
    }

    @Test
    public void toListIsAnIndependentCopy() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addLast(1);
        deque.addLast(2);
        List<Integer> snapshot = deque.toList();
        deque.addFirst(0);
        assertThat(snapshot).containsExactly(1, 2).inOrder();
        snapshot.clear();
        assertThat(deque.toList()).containsExactly(0, 1, 2).inOrder();
    }

    @Test
    public void addFirstResizesWrappedArray() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addLast(4);
        deque.addLast(5);
        for (int i = 3; i >= -3; i--) {
            deque.addFirst(i);
        }
        assertThat(deque.toList()).containsExactly(-3, -2, -1, 0, 1, 2, 3, 4, 5).inOrder();
        assertThat(deque.size()).isEqualTo(9);
    }

    @Test
    public void addLastResizesWrappedArray() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addFirst(1);
        deque.addFirst(0);
        for (int i = 2; i <= 8; i++) {
            deque.addLast(i);
        }
        assertThat(deque.toList()).containsExactly(0, 1, 2, 3, 4, 5, 6, 7, 8).inOrder();
        assertThat(deque.size()).isEqualTo(9);
    }

    @Test
    public void growShrinkAndReuseFromBothEnds() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();
        for (int i = 0; i < 1024; i++) {
            deque.addLast(i);
        }
        for (int i = 0; i < 1023; i++) {
            assertThat(deque.removeFirst()).isEqualTo(i);
        }
        assertThat(deque.toList()).containsExactly(1023);
        assertThat(deque.removeLast()).isEqualTo(1023);
        for (int i = 0; i < 1024; i++) {
            deque.addFirst(i);
        }
        for (int i = 0; i < 1023; i++) {
            assertThat(deque.removeLast()).isEqualTo(i);
        }
        assertThat(deque.toList()).containsExactly(1023);
        assertThat(deque.removeFirst()).isEqualTo(1023);
        assertThat(deque.isEmpty()).isTrue();
    }

    @Test
    public void alternatingEndsMatchJavaDeque() {
        Deque61B<Integer> actual = new ArrayDeque61B<>();
        ArrayDeque<Integer> expected = new ArrayDeque<>();
        Random random = new Random(61);
        for (int step = 0; step < 20000; step++) {
            switch (random.nextInt(4)) {
                case 0 -> {
                    actual.addFirst(step);
                    expected.addFirst(step);
                }
                case 1 -> {
                    actual.addLast(step);
                    expected.addLast(step);
                }
                case 2 -> assertThat(actual.removeFirst()).isEqualTo(expected.pollFirst());
                default -> assertThat(actual.removeLast()).isEqualTo(expected.pollLast());
            }
            List<Integer> values = new ArrayList<>(expected);
            assertThat(actual.size()).isEqualTo(values.size());
            assertThat(actual.isEmpty()).isEqualTo(expected.isEmpty());
            assertThat(actual.toList()).containsExactlyElementsIn(values).inOrder();
            for (int i = 0; i < values.size(); i++) {
                assertThat(actual.get(i)).isEqualTo(values.get(i));
            }
            assertThat(actual.get(-1)).isNull();
            assertThat(actual.get(values.size())).isNull();
        }
    }
}
