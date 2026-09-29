import java.util.Random;

public class Partition3Way {

    public static void partition(
            int[] a,
            int left,
            int right,
            Metrics metrics,
            Random random,
            int[] range
    ) {

        int pivotIndex =
                left + random.nextInt(right - left + 1);

        int pivot = a[pivotIndex];

        int lt = left;
        int i = left;
        int gt = right;

        while (i <= gt) {

            metrics.comparison();

            if (a[i] < pivot) {

                swap(a, lt, i);

                lt++;
                i++;

            } else {

                metrics.comparison();

                if (a[i] > pivot) {

                    swap(a, i, gt);
                    gt--;

                } else {

                    i++;
                }
            }
        }

        range[0] = lt;
        range[1] = gt;
    }

    private static void swap(int[] a, int i, int j) {

        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}