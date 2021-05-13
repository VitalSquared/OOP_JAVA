package ru.nsu.spirin.chess.move;

import ru.nsu.spirin.chess.board.Board;
import ru.nsu.spirin.chess.utils.Pair;

import java.util.ArrayList;
import java.util.List;

public final class MoveLog {
    private final List<Pair<Move, String>> moves;

    public MoveLog() {
        this.moves = new ArrayList<>();
    }

    public List<Pair<Move, String>> getMoves() {
        return this.moves;
    }

    public void addMove(Board board, Move move) {
        String moveString = move.toString();
        if (board.getCurrentPlayer().isInCheck()) moveString += "+";
        else if (board.getCurrentPlayer().isInCheckMate())  moveString += "#";
        this.moves.add(new Pair<>(move, moveString));
    }

    public int size() {
        return this.moves.size();
    }
}
