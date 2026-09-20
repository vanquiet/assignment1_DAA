package benchmark;

import metrics.Metrics;
import sorting.MergeSort;
import sorting.QuickSort;
import select.QuickSelect;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

public class Benchmark {
    private static final int[] SIZES = {1000, 10000, 100000, 1000000};
    private static final String[] TYPES = {"random", "sorted", "duplicates"};
    private static final int RUNS = 5;

    public static void main(String[] args) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("results.csv"))) {
            writer.println("algorithm,input,n,time_ms,comparisons,max_depth");

            for (int n : SIZES) {
                for (String type : TYPES) {
                    runBenchmarkForCase("MergeSort", type, n, writer);
                    runBenchmarkForCase("QuickSort", type, n, writer);
                    runBenchmarkForCase("QuickSelect", type, n, writer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void runBenchmarkForCase(String algorithm, String inputType, int n, PrintWriter writer) {
        double[] times = new double[RUNS];
        long[] comps = new long[RUNS];
        int[] depths = new int[RUNS];

        Metrics metrics = new Metrics();

        for (int r = 0; r < RUNS; r++) {
            int[] base = generateArray(n, inputType);
            int[] a = Arrays.copyOf(base, base.length);
            metrics.reset();

            if (algorithm.equals("MergeSort")) {
                MergeSort.sort(a, metrics);
            } else if (algorithm.equals("QuickSort")) {
                QuickSort.sort(a, metrics);
            } else if (algorithm.equals("QuickSelect")) {
                QuickSelect.select(a, n / 2, metrics);
            }

            times[r] = metrics.getTimeMs();
            comps[r] = metrics.getComparisons();
            depths[r] = metrics.getMaxDepth();
        }

        Arrays.sort(times);
        Arrays.sort(comps);
        Arrays.sort(depths);

        double medianTime = times[RUNS / 2];
        long medianComp = comps[RUNS / 2];
        int medianDepth = depths[RUNS / 2];

        writer.printf("%s,%s,%d,%.4f,%d,%d\n", algorithm, inputType, n, medianTime, medianComp, medianDepth);
        writer.flush();
        System.out.printf("Done: %s, %s, n=%d\n", algorithm, inputType, n);
    }

    private static int[] generateArray(int n, String type) {
        int[] a = new int[n];
        Random random = new Random();

        if (type.equals("random")) {
            for (int i = 0; i < n; i++) {
                a[i] = random.nextInt();
            }
        } else if (type.equals("sorted")) {
            for (int i = 0; i < n; i++) {
                a[i] = i;
            }
        } else if (type.equals("duplicates")) {
            for (int i = 0; i < n; i++) {
                a[i] = random.nextInt(10);
            }
        }
        return a;
    }
}
