import java.util.Random;

public class QuickSelect {

    public static int select(
            int[] a,
            int k,
            Metrics metrics
    ) {

        if (a == null || a.length == 0) {

            throw new IllegalArgumentException(
                    "Array must not be empty"
            );
        }

        if (k < 0 || k >= a.length) {

            throw new IllegalArgumentException(
                    "k is out of range"
            );
        }

        metrics.reset();
        metrics.start();
        metrics.recordDepth(1);

        Random random = new Random();

        int[] range = new int[2];

        int left = 0;
        int right = a.length - 1;

        while (left <= right) {

            if (left == right) {

                metrics.stop();

                return a[left];
            }

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

            if (k < lt) {

                right = lt - 1;

            } else if (k > gt) {

                left = gt + 1;

            } else {

                metrics.stop();

                return a[k];
            }
        }

        metrics.stop();

        throw new IllegalStateException(
                "Selection failed"
        );
    }
}