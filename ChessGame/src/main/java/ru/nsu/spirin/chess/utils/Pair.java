package ru.nsu.spirin.chess.utils;

import java.io.Serializable;

public final class Pair<First, Second> implements Serializable {
    private final First  first;
    private final Second second;

    public Pair(First first, Second second) {
        this.first = first;
        this.second = second;
    }

    public First getFirst() {
        return this.first;
    }

    public Second getSecond() {
        return this.second;
    }
}
