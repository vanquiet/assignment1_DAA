import java.util.Random;

public class QuickSort {

    public static void sort(int[] a, Metrics metrics) {

        metrics.reset();
        metrics.start();

        if (a.length > 1) {

            Random random = new Random();

            int[] range = new int[2];

            quickSort(
                    a,
                    0,
                    a.length - 1,
                    metrics,
                    random,
                    range,
                    1
            );
        }

        metrics.stop();
    }

    private static void quickSort(
            int[] a,
            int left,
            int right,
            Metrics metrics,
            Random random,
            int[] range,
            int depth
    ) {

        while (left < right) {

            metrics.recordDepth(depth);

            Partition3Way.partition(
                    a,
                    left,
                    right,
                    metrics,
                    random,
                    range
            );

            int lt = range[0];
            int gt = range[1];

            int leftSize = lt - left;
            int rightSize = right - gt;

            if (leftSize < rightSize) {

                if (left < lt - 1) {

                    quickSort(
                            a,
                            left,
                            lt - 1,
                            metrics,
                            random,
                            range,
                            depth + 1
                    );
                }

                left = gt + 1;

            } else {

                if (gt + 1 < right) {

                    quickSort(
                            a,
                            gt + 1,
                            right,
                            metrics,
                            random,
                            range,
                            depth + 1
                    );
                }

                right = lt - 1;
            }
        }
    }
}