public class MergeSort {

    private static final int CUTOFF = 15;

    public static void sort(int[] a, Metrics metrics) {

        metrics.reset();
        metrics.start();

        int[] buffer = new int[a.length];

        if (a.length > 0) {
            mergeSort(a, buffer, 0, a.length - 1, metrics, 1);
        }

        metrics.stop();
    }

    private static void mergeSort(
            int[] a,
            int[] buffer,
            int left,
            int right,
            Metrics metrics,
            int depth
    ) {

        metrics.recordDepth(depth);

        if (left >= right) {
            return;
        }

        if (right - left + 1 <= CUTOFF) {
            insertionSort(a, left, right, metrics);
            return;
        }

        int mid = left + (right - left) / 2;

        mergeSort(a, buffer, left, mid, metrics, depth + 1);
        mergeSort(a, buffer, mid + 1, right, metrics, depth + 1);

        merge(a, buffer, left, mid, right, metrics);
    }

    private static void insertionSort(
            int[] a,
            int left,
            int right,
            Metrics metrics
    ) {

        for (int i = left + 1; i <= right; i++) {

            int value = a[i];
            int j = i - 1;

            while (j >= left) {

                metrics.comparison();

                if (a[j] <= value) {
                    break;
                }

                a[j + 1] = a[j];
                j--;
            }

            a[j + 1] = value;
        }
    }

    private static void merge(
            int[] a,
            int[] buffer,
            int left,
            int mid,
            int right,
            Metrics metrics
    ) {

        for (int i = left; i <= right; i++) {
            buffer[i] = a[i];
        }

        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {

            metrics.comparison();

            if (buffer[i] <= buffer[j]) {
                a[k++] = buffer[i++];
            } else {
                a[k++] = buffer[j++];
            }
        }

        while (i <= mid) {
            a[k++] = buffer[i++];
        }

        while (j <= right) {
            a[k++] = buffer[j++];
        }
    }
}