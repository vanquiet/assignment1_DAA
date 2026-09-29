import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import java.util.Random;

public class Benchmark {

    private static final int[] SIZES = {
            1_000,
            10_000,
            100_000,
            1_000_000
    };

    private static final String[] INPUT_TYPES = {
            "random",
            "sorted",
            "duplicates"
    };

    private static final int REPEATS = 5;

    public static void main(String[] args)
            throws IOException {

        Locale.setDefault(Locale.US);

        PrintWriter writer =
                new PrintWriter(
                        new FileWriter("results.csv")
                );

        writer.println(
                "algorithm,input,n,time_ms,comparisons,max_depth"
        );

        for (String inputType : INPUT_TYPES) {

            for (int n : SIZES) {

                int[] original =
                        createInput(
                                inputType,
                                n
                        );

                runCase(
                        "mergesort",
                        inputType,
                        n,
                        original,
                        writer
                );

                runCase(
                        "quicksort",
                        inputType,
                        n,
                        original,
                        writer
                );

                runCase(
                        "quickselect",
                        inputType,
                        n,
                        original,
                        writer
                );
            }
        }

        writer.close();

        System.out.println();
        System.out.println(
                "Benchmark finished."
        );

        System.out.println(
                "results.csv created."
        );
    }

    private static void runCase(
            String algorithm,
            String inputType,
            int n,
            int[] original,
            PrintWriter writer
    ) {

        RunResult[] results =
                new RunResult[REPEATS];

        for (int run = 0;
             run < REPEATS;
             run++) {

            int[] data =
                    original.clone();

            Metrics metrics =
                    new Metrics();

            if (algorithm.equals("mergesort")) {

                MergeSort.sort(
                        data,
                        metrics
                );

            } else if (
                    algorithm.equals("quicksort")
            ) {

                QuickSort.sort(
                        data,
                        metrics
                );

            } else {

                QuickSelect.select(
                        data,
                        n / 2,
                        metrics
                );
            }

            results[run] =
                    new RunResult(
                            metrics.getTimeMs(),
                            metrics.getComparisons(),
                            metrics.getMaxDepth()
                    );
        }

        Arrays.sort(
                results,
                Comparator.comparingDouble(
                        r -> r.time
                )
        );

        RunResult median =
                results[REPEATS / 2];

        writer.printf(
                Locale.US,
                "%s,%s,%d,%.6f,%d,%d%n",
                algorithm,
                inputType,
                n,
                median.time,
                median.comparisons,
                median.depth
        );

        System.out.printf(
                Locale.US,
                "%-12s %-10s n=%-8d time=%.3f ms%n",
                algorithm,
                inputType,
                n,
                median.time
        );
    }

    private static int[] createInput(
            String type,
            int n
    ) {

        int[] a = new int[n];

        Random random =
                new Random(
                        12345L
                                + n
                                + type.hashCode()
                );

        if (type.equals("sorted")) {

            for (int i = 0; i < n; i++) {
                a[i] = i;
            }

        } else if (
                type.equals("duplicates")
        ) {

            for (int i = 0; i < n; i++) {
                a[i] = random.nextInt(10);
            }

        } else {

            for (int i = 0; i < n; i++) {
                a[i] = random.nextInt();
            }
        }

        return a;
    }

    private static class RunResult {

        double time;
        long comparisons;
        int depth;

        RunResult(
                double time,
                long comparisons,
                int depth
        ) {

            this.time = time;
            this.comparisons = comparisons;
            this.depth = depth;
        }
    }
}