package sorting;

import metrics.Metrics;
import java.util.Random;

public class QuickSort {
    private static final Random random = new Random();

    public static void sort(int[] a, Metrics metrics) {
        if (a == null || a.length <= 1) {
            return;
        }
        metrics.start();
        sort(a, 0, a.length - 1, metrics);
        metrics.stop();
    }

    private static void sort(int[] a, int left, int right, Metrics metrics) {
        while (left < right) {
            metrics.enterRecursion();
            int[] p = partition(a, left, right, metrics);
            int leftSize = p[0] - 1 - left;
            int rightSize = right - (p[1] + 1);

            if (leftSize < rightSize) {
                sort(a, left, p[0] - 1, metrics);
                left = p[1] + 1;
            } else {
                sort(a, p[1] + 1, right, metrics);
                right = p[0] - 1;
            }
            metrics.exitRecursion();
        }
    }

    public static int[] partition(int[] a, int left, int right, Metrics metrics) {
        int randomIndex = left + random.nextInt(right - left + 1);
        swap(a, left, randomIndex);
        int pivot = a[left];
        int lt = left;
        int i = left + 1;
        int gt = right;

        while (i <= gt) {
            metrics.addComparison();
            if (a[i] < pivot) {
                swap(a, lt, i);
                lt++;
                i++;
            } else if (a[i] > pivot) {
                swap(a, i, gt);
                gt--;
            } else {
                i++;
            }
        }
        return new int[]{lt, gt};
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}