import metrics.Metrics;
import org.junit.jupiter.api.Test;
import select.QuickSelect;
import sorting.MergeSort;
import sorting.QuickSort;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class AlgorithmTest {

    @Test
    public void testMerge() {
        Random random = new Random();
        Metrics metrics = new Metrics();
        for (int i = 0; i < 100; i++) {
            int n = 100 + random.nextInt(900);
            int[] a = new int[n];
            for (int j = 0; j < n; j++) {
                a[j] = random.nextInt(10000);
            }
            int[] exp = Arrays.copyOf(a, a.length);
            Arrays.sort(exp);

            metrics.reset();
            MergeSort.sort(a, metrics);
            assertArrayEquals(exp, a);
        }
    }

    @Test
    public void testQuick() {
        Random random = new Random();
        Metrics metrics = new Metrics();
        for (int i = 0; i < 100; i++) {
            int n = 100 + random.nextInt(900);
            int[] a = new int[n];
            for (int j = 0; j < n; j++) {
                a[j] = random.nextInt(10000);
            }
            int[] exp = Arrays.copyOf(a, a.length);
            Arrays.sort(exp);

            metrics.reset();
            QuickSort.sort(a, metrics);
            assertArrayEquals(exp, a);
        }
    }

    @Test
    public void testEdge() {
        Metrics metrics = new Metrics();

        int[] empty = new int[0];
        MergeSort.sort(empty, metrics);
        QuickSort.sort(empty, metrics);
        assertEquals(0, empty.length);

        int[] single = {42};
        MergeSort.sort(single, metrics);
        QuickSort.sort(single, metrics);
        assertEquals(42, single[0]);

        int[] allEqual = {5, 5, 5, 5, 5};
        MergeSort.sort(allEqual, metrics);
        QuickSort.sort(allEqual, metrics);
        assertArrayEquals(new int[]{5, 5, 5, 5, 5}, allEqual);

        int[] sorted = {1, 2, 3, 4, 5};
        MergeSort.sort(sorted, metrics);
        QuickSort.sort(sorted, metrics);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, sorted);
    }

    @Test
    public void testDepth() {
        int n = 100000;
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = i;
        }
        Metrics metrics = new Metrics();
        QuickSort.sort(a, metrics);

        double limit = 2 * (Math.log(n) / Math.log(2));
        assertTrue(metrics.getMaxDepth() <= limit);
    }

    @Test
    public void testSelect() {
        Random random = new Random();
        Metrics metrics = new Metrics();
        for (int i = 0; i < 100; i++) {
            int n = 100 + random.nextInt(900);
            int[] a = new int[n];
            for (int j = 0; j < n; j++) {
                a[j] = random.nextInt(10000);
            }
            int[] copy = Arrays.copyOf(a, a.length);
            Arrays.sort(copy);

            int k = random.nextInt(n);
            metrics.reset();
            int res = QuickSelect.select(a, k, metrics);
            assertEquals(copy[k], res);
        }
    }

    @Test
    public void testErrors() {
        Metrics metrics = new Metrics();
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.select(new int[0], 0, metrics));
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.select(new int[]{1, 2, 3}, -1, metrics));
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.select(new int[]{1, 2, 3}, 3, metrics));
    }
}