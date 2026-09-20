package metrics;

public class Metrics {
    private long comparisons;
    private int currentDepth;
    private int maxDepth;
    private long startTime;
    private long elapsedTimeNs;

    public Metrics() {
        reset();
    }

    public void start() {
        this.startTime = System.nanoTime();
    }

    public void stop() {
        this.elapsedTimeNs = System.nanoTime() - this.startTime;
    }

    public void addComparison() {
        this.comparisons++;
    }

    public void enterRecursion() {
        this.currentDepth++;
        if (this.currentDepth > this.maxDepth) {
            this.maxDepth = this.currentDepth;
        }
    }

    public void exitRecursion() {
        this.currentDepth--;
    }

    public void reset() {
        this.comparisons = 0;
        this.currentDepth = 0;
        this.maxDepth = 0;
        this.startTime = 0;
        this.elapsedTimeNs = 0;
    }

    public long getComparisons() {
        return comparisons;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public double getTimeMs() {
        return elapsedTimeNs / 1_000_000.0;
    }
}