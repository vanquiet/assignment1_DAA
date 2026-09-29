import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class AlgorithmsTest {

    @Test
    void sortsMatchArraysSort() {

        Random random =
                new Random(12345);

        for (int test = 0;
             test < 100;
             test++) {

            int size =
                    random.nextInt(500);

            int[] original =
                    new int[size];

            for (int i = 0; i < size; i++) {

                original[i] =
                        random.nextInt(10_000)
                                - 5_000;
            }

            int[] expected =
                    original.clone();

            Arrays.sort(expected);


            int[] merge =
                    original.clone();

            MergeSort.sort(
                    merge,
                    new Metrics()
            );

            assertArrayEquals(
                    expected,
                    merge
            );


            int[] quick =
                    original.clone();

            QuickSort.sort(
                    quick,
                    new Metrics()
            );

            assertArrayEquals(
                    expected,
                    quick
            );
        }
    }


    @Test
    void edgeCases() {

        int[][] cases = {

                {},

                {5},

                {7, 7, 7, 7},

                {1, 2, 3, 4, 5}
        };

        for (int[] input : cases) {

            int[] expected =
                    input.clone();

            Arrays.sort(expected);


            int[] merge =
                    input.clone();

            MergeSort.sort(
                    merge,
                    new Metrics()
            );

            assertArrayEquals(
                    expected,
                    merge
            );


            int[] quick =
                    input.clone();

            QuickSort.sort(
                    quick,
                    new Metrics()
            );

            assertArrayEquals(
                    expected,
                    quick
            );
        }
    }


    @Test
    void quickSortDepthCheck() {

        int n = 100_000;

        int[] a =
                new int[n];

        for (int i = 0; i < n; i++) {
            a[i] = i;
        }

        Metrics metrics =
                new Metrics();

        QuickSort.sort(
                a,
                metrics
        );

        double log2 =
                Math.log(n)
                        / Math.log(2);

        int limit =
                (int) Math.ceil(
                        2 * log2
                );

        assertTrue(
                metrics.getMaxDepth()
                        <= limit
        );
    }


    @Test
    void quickSelectMatchesSortedArray() {

        Random random =
                new Random(54321);

        for (int test = 0;
             test < 100;
             test++) {

            int size =
                    1
                            + random.nextInt(500);

            int[] a =
                    new int[size];

            for (int i = 0; i < size; i++) {

                a[i] =
                        random.nextInt(10_000)
                                - 5_000;
            }

            int k =
                    random.nextInt(size);

            int[] sorted =
                    a.clone();

            Arrays.sort(sorted);

            int result =
                    QuickSelect.select(
                            a.clone(),
                            k,
                            new Metrics()
                    );

            assertEquals(
                    sorted[k],
                    result
            );
        }
    }


    @Test
    void quickSelectInvalidInput() {

        assertThrows(
                IllegalArgumentException.class,
                () ->
                        QuickSelect.select(
                                new int[]{},
                                0,
                                new Metrics()
                        )
        );

        assertThrows(
                IllegalArgumentException.class,
                () ->
                        QuickSelect.select(
                                new int[]{1, 2, 3},
                                -1,
                                new Metrics()
                        )
        );

        assertThrows(
                IllegalArgumentException.class,
                () ->
                        QuickSelect.select(
                                new int[]{1, 2, 3},
                                3,
                                new Metrics()
                        )
        );
    }
}