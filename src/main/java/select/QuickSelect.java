package select;

import metrics.Metrics;
import sorting.QuickSort;

public class QuickSelect {

    public static int select(int[] a, int k, Metrics metrics) {
        if (a == null || a.length == 0 || k < 0 || k >= a.length) {
            throw new IllegalArgumentException("Array is empty or k is out of range");
        }
        metrics.start();
        int result = select(a, 0, a.length - 1, k, metrics);
        metrics.stop();
        return result;
    }

    private static int select(int[] a, int left, int right, int k, Metrics metrics) {
        metrics.enterRecursion();
        if (left == right) {
            metrics.exitRecursion();
            return a[left];
        }

        int[] p = QuickSort.partition(a, left, right, metrics);
        int lt = p[0];
        int gt = p[1];

        int result;
        if (k >= lt && k <= gt) {
            result = a[k];
        } else if (k < lt) {
            result = select(a, left, lt - 1, k, metrics);
        } else {
            result = select(a, gt + 1, right, k, metrics);
        }

        metrics.exitRecursion();
        return result;
    }
}