package ru.nsu.spirin.logoworld.math;

public class Pair {
    private final int first;
    private final int second;

    /**
     * Creates pair of integers
     * @param first first element of pair
     * @param second second element of pair
     */
    public Pair(int first, int second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Get first element of pair
     * @return first element of pair
     */
    public int getFirst() {
        return first;
    }

    /**
     * Get second element of pair
     * @return second element of pair
     */
    public int getSecond() {
        return second;
    }
}
