import deque.ArrayDeque61B;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static com.google.common.truth.Truth.assertThat;

/** Local implementation checks, separate from the interface-only coverage tests. */
public class ArrayDeque61BStorageTest {
    private Object[] backingArray(ArrayDeque61B<?> deque) throws ReflectiveOperationException {
        for (Field field : ArrayDeque61B.class.getDeclaredFields()) {
            if (field.getType().isArray()) {
                field.setAccessible(true);
                return (Object[]) field.get(deque);
            }
        }
        throw new AssertionError("No backing array found");
    }

    @Test
    public void startsAtEightAndShrinksAtQuarterUsage() throws ReflectiveOperationException {
        for (boolean removeFirst : new boolean[]{true, false}) {
            ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
            assertThat(backingArray(deque).length).isEqualTo(8);
            for (int i = 0; i < 16; i++) {
                deque.addLast(i);
            }
            for (int i = 0; i < 12; i++) {
                if (removeFirst) {
                    deque.removeFirst();
                } else {
                    deque.removeLast();
                }
            }
            assertThat(deque.size()).isEqualTo(4);
            if (removeFirst) {
                assertThat(deque.removeFirst()).isEqualTo(12);
                assertThat(deque.toList()).containsExactly(13, 14, 15).inOrder();
            } else {
                assertThat(deque.removeLast()).isEqualTo(3);
                assertThat(deque.toList()).containsExactly(0, 1, 2).inOrder();
            }
            assertThat(backingArray(deque).length).isEqualTo(8);
        }
    }

    @Test
    public void removalReleasesReferencesAndExcessCapacity() throws ReflectiveOperationException {
        ArrayDeque61B<Object> deque = new ArrayDeque61B<>();
        Object retained = new Object();
        for (int i = 0; i < 1024; i++) {
            deque.addLast(new Object());
        }
        deque.addLast(retained);
        while (deque.size() > 1) {
            deque.removeFirst();
        }
        assertThat(backingArray(deque).length).isEqualTo(8);
        int occupied = 0;
        for (Object item : backingArray(deque)) {
            if (item != null) {
                assertThat(item).isSameInstanceAs(retained);
                occupied++;
            }
        }
        assertThat(occupied).isEqualTo(1);
        assertThat(deque.removeLast()).isSameInstanceAs(retained);
        for (Object item : backingArray(deque)) {
            assertThat(item).isNull();
        }
    }
}
