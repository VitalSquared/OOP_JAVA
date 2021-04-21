package ru.nsu.spirin.chessgame.view;

import com.google.common.primitives.Ints;
import ru.nsu.spirin.chessgame.board.BoardUtils;
import ru.nsu.spirin.chessgame.controller.Controller;
import ru.nsu.spirin.chessgame.move.Move;
import ru.nsu.spirin.chessgame.pieces.Piece;
import ru.nsu.spirin.chessgame.scene.Scene;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public final class ConsoleView implements GameView {
    private final Controller controller;
    private final Scanner    scanner;

    public ConsoleView(Controller controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void render(Scene scene) {
        switch (scene.getSceneState()) {
            case MAIN_MENU -> {
                printCommands();
                System.out.print("Enter command: ");
                boolean execResult = controller.execute(scanner.nextLine(), false);
            }
            case GAME -> {
                printScene(scene);
                if (scene.getBoard().getCurrentPlayer().isInCheckMate() || scene.getBoard().getCurrentPlayer().isInStaleMate() || scene.getBoard().getCurrentPlayer().hasSurrendered()) {
                    System.out.println("[" + scene.getBoard().getCurrentPlayer().getOpponent().getAlliance() + "] " + scene.getBoard().getCurrentPlayer().getOpponent().getPlayerName() + " won!");
                    scene.setBoard(null);
                    return;
                }
                System.out.print("[" + scene.getBoard().getCurrentPlayer().getAlliance().toString() + "]" + " Enter command: ");
                if (!scene.getBoard().getCurrentPlayer().isAI()) {
                    boolean execResult = controller.execute(scanner.nextLine(), false);
                }
                else {
                    boolean execResult = controller.execute("ai_move", true);
                }
            }
        }
    }

    @Override
    public void close() {
    }

    private void printCommands() {
        System.out.println("List of commands:");
        System.out.println("new_game -singleplayer <white_player_name> <black_player_name> <white_ai [true|false]> <black_ai [true|false]>");
        System.out.println("high_scores");
        System.out.println("about");
        System.out.println("exit");
    }

    private void printScene(final Scene scene) {
        System.out.println("############################################################");
        printMoveLog(scene);
        System.out.println("------------------------------------------------------------");
        System.out.println("BLACK: " + scene.getBoard().getBlackPlayer().getPlayerName());
        printPlayerTakenPieces(scene, false);
        printBoard(scene);
        printPlayerTakenPieces(scene, true);
        System.out.println("WHITE: " + scene.getBoard().getWhitePlayer().getPlayerName());
        System.out.println("############################################################");
    }

    private void printBoard(final Scene scene) {
        final StringBuilder builder = new StringBuilder();
        builder.append("8| ");
        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            final String tileText = scene.getBoard().getTile(i).toString();
            builder.append(tileText).append(" ");
            if ((i + 1) % BoardUtils.NUM_TILES_PER_ROW == 0) {
                builder.append(System.lineSeparator());
                if (i != BoardUtils.NUM_TILES - 1) {
                    builder.append(8 - (i + 1) / 8).append("| ");
                }
            }
        }
        builder.append(" +");
        for (char i = 0; i < 8; i++) {
            builder.append("--");
        }
        builder.append(System.lineSeparator());
        builder.append("   ");
        for (char i = 'a'; i <= 'h'; i++) {
            builder.append(i).append(" ");
        }
        builder.append(System.lineSeparator());
        System.out.println(builder.toString());
    }

    private void printPlayerTakenPieces(final Scene scene, final boolean isWhite) {
        final List<Piece> takenPieces = new ArrayList<>();
        for (final Move move : scene.getMoveLog().getMoves()) {
            if (move.isAttack()) {
                final Piece takenPiece = move.getAttackedPiece();
                if (takenPiece.getPieceAlliance().isWhite() && !isWhite) {
                    takenPieces.add(takenPiece);
                }
                else if (takenPiece.getPieceAlliance().isBlack() && isWhite) {
                    takenPieces.add(takenPiece);
                }
            }
        }
        takenPieces.sort((o1, o2) -> Ints.compare(o1.getPieceType().getPieceValue(), o2.getPieceType().getPieceValue()));
        for (final Piece piece : takenPieces) {
            System.out.print(piece.toString() + " ");
        }
        System.out.println();
    }

    private void printMoveLog(final Scene scene) {
        System.out.println(" WHITE | BLACK ");
        for (final Move move : scene.getMoveLog().getMoves()) {
            final String moveText = move.toString();
            if (move.getMovedPiece().getPieceAlliance().isWhite()) {
                System.out.printf("%7s", moveText);
            }
            if (move.getMovedPiece().getPieceAlliance().isBlack()) {
                System.out.printf("|%7s\n", moveText);
            }
        }
        System.out.println();
    }
}
