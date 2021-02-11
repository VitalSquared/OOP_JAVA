package ru.nsu.spirin.LogoWorld.math;

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
     * Returns first element of pair
     * @return first element of pair
     */
    public int getFirst() {
        return first;
    }

    /**
     * Returns second element of pair
     * @return second element of pair
     */
    public int getSecond() {
        return second;
    }
}
