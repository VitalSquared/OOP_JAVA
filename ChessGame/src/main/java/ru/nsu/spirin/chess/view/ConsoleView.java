package ru.nsu.spirin.chess.view;

import com.google.common.primitives.Ints;
import ru.nsu.spirin.chess.model.board.BoardUtils;
import ru.nsu.spirin.chess.model.match.MatchEntity;
import ru.nsu.spirin.chess.controller.Controller;
import ru.nsu.spirin.chess.model.move.Move;
import ru.nsu.spirin.chess.model.pieces.Piece;
import ru.nsu.spirin.chess.model.player.Alliance;
import ru.nsu.spirin.chess.model.scene.Scene;
import ru.nsu.spirin.chess.model.scene.SceneState;
import ru.nsu.spirin.chess.utils.Pair;
import ru.nsu.spirin.chess.utils.ThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class ConsoleView extends GameView {
    private final Scanner    scanner;

    public ConsoleView(Scene scene, Controller controller) {
        super(scene, controller);
        this.scanner = new Scanner(System.in);

        ThreadPool.submitThread(new Thread(() -> {
            while (getScene().getSceneState() != SceneState.NONE) {
                String command = scanner.nextLine();
                String extra = "";
                if (command.toLowerCase().startsWith("move")) {
                    try {
                        extra = getScene().getActiveGame().getPlayerAlliance().toString();
                    }
                    catch (Exception e) {
                        extra = "";
                    }
                }
                getController().execute(command + " " + extra);
            }
        }));
    }

    @Override
    public void render() {
        if (viewChanged()) {
            updateMainMenuPanel();
            updateNewGamePanel();
            updateHighScoresPanel();
            updateAboutPanel();
            updateConnectionPanel();
            updateBoardPanel();
            updateResultsPanel();
        }
    }

    @Override
    public void close() {
    }

    private void updateMainMenuPanel() {
        if (getScene().getSceneState() == SceneState.MAIN_MENU) {
            System.out.println("############################################################");
            System.out.println("CHESS\n");
            System.out.println("new_game \tenter new game menu");
            System.out.println("high_scores \tenter high scores menu");
            System.out.println("about \tenter about menu");
            System.out.println("exit \texit game");
            System.out.println("############################################################");
        }
    }

    private void updateNewGamePanel() {
        if (getScene().getSceneState() == SceneState.NEW_GAME_MENU) {
            System.out.println("############################################################");
            System.out.println("NEW GAME\n");
            System.out.println("start <player team: [white | black]> <player name>\tplay game with ai");
            System.out.println("join <ip> <port> <player name> \tjoin game");
            System.out.println("back \treturn to previous menu");
            System.out.println("############################################################");
        }
    }

    private void updateHighScoresPanel() {
        if (getScene().getSceneState() == SceneState.HIGH_SCORES_MENU) {
            System.out.println("############################################################");
            System.out.println("HIGH SCORES\n");
            System.out.println(getHighScores());
            System.out.println("\nback \treturn to previous menu");
            System.out.println("############################################################");
        }
    }

    private void updateAboutPanel() {
        if (getScene().getSceneState() == SceneState.ABOUT_MENU) {
            System.out.println("############################################################");
            System.out.println("ABOUT\n");
            System.out.println(getAbout());
            System.out.println("\nback \treturn to previous menu");
            System.out.println("############################################################");
        }
    }

    private void updateConnectionPanel() {
        if (getScene().getSceneState() == SceneState.CONNECTION_MENU) {
            System.out.println("############################################################");
            MatchEntity matchEntity = getScene().getActiveGame();
            switch (matchEntity.connected()) {
                case FAILED -> {
                    System.out.println("Failed to connect");
                    System.out.println("\n\nback \treturn to previous menu");
                }
                case NOT_CONNECTED -> {
                    System.out.println("Awaiting connection");
                    System.out.println("\n\ncancel \tCancel connection and return to previous menu");
                }
                case WAITING_FOR_PLAYER -> {
                    System.out.println("Waiting for other player");
                    System.out.println("\n\ncancel \tCancel connection and return to previous menu");
                }
                case CONNECTED -> {
                    System.out.printf("%15s ", "WHITE TEAM");
                    System.out.printf("%15s ", "");
                    System.out.printf("%15s\n", "BLACK TEAM");

                    System.out.printf("%15s ", (matchEntity.getOpponentAlliance() == Alliance.WHITE ? matchEntity.getOpponentName() : ""));
                    System.out.printf("%15s ", (matchEntity.getOpponentAlliance() == null ? matchEntity.getOpponentName() : ""));
                    System.out.printf("%15s\n", (matchEntity.getOpponentAlliance() == Alliance.BLACK ? matchEntity.getOpponentName() : ""));

                    System.out.printf("%15s ", (matchEntity.getPlayerAlliance() == Alliance.WHITE ? matchEntity.getPlayerName() : ""));
                    System.out.printf("%15s ", (matchEntity.getPlayerAlliance() == null ? matchEntity.getPlayerName() : ""));
                    System.out.printf("%15s\n", (matchEntity.getPlayerAlliance() == Alliance.BLACK ? matchEntity.getPlayerName() : ""));

                    System.out.println();

                    System.out.printf("%15s ", (matchEntity.getOpponentAlliance() == Alliance.WHITE && matchEntity.getPlayerAlliance() != Alliance.WHITE ? (matchEntity.isOpponentReady() ? "Ready" : "Not Ready") : ""));
                    System.out.printf("%15s ", "");
                    System.out.printf("%15s\n", (matchEntity.getOpponentAlliance() == Alliance.BLACK && matchEntity.getPlayerAlliance() != Alliance.BLACK ? (matchEntity.isOpponentReady() ? "Ready" : "Not Ready") : ""));

                    System.out.printf("%15s ", (matchEntity.getPlayerAlliance() == Alliance.WHITE && matchEntity.getOpponentAlliance() != Alliance.WHITE ? (matchEntity.isPlayerReady() ? "Ready" : "Not Ready") : ""));
                    System.out.printf("%15s ", "");
                    System.out.printf("%15s\n", (matchEntity.getPlayerAlliance() == Alliance.BLACK && matchEntity.getOpponentAlliance() != Alliance.BLACK ? (matchEntity.isPlayerReady() ? "Ready" : "Not Ready") : ""));

                    System.out.println("\n\nteam <[white | none | black] \tchoose team");
                    if (matchEntity.getPlayerAlliance() != null && matchEntity.getPlayerAlliance() != matchEntity.getOpponentAlliance()) {
                        System.out.println("ready \tset ready state");
                    }
                    System.out.println("disconnect \tdisconnect from match and return to previous menu");
                }
            }
            System.out.println("############################################################");
        }
    }

    private void updateBoardPanel() {
        if (getScene().getSceneState() == SceneState.BOARD_MENU) {
            printBoardPanel();
            if (!BoardUtils.isEndGame(getScene().getActiveGame().getBoard())) {
                System.out.println("\n\nmove <piece coordinate> <piece destination> \tmake a move");
                System.out.println("move resign \tresign/surrender");
            }
            else {
                System.out.println(getRoundResult());
                System.out.println("\nproceed \tenter results menu");
            }
            System.out.println("############################################################");
        }
    }

    private void updateResultsPanel() {
        if (getScene().getSceneState() == SceneState.RESULTS_MENU) {
            System.out.println("############################################################");
            System.out.println("RESULTS");
            System.out.println(getResultScores());
            System.out.println("\n\nback \treturn to main menu");
            System.out.println("############################################################");
        }
    }

    private void printBoardPanel() {
        Scene scene = getScene();
        String opponentTurn = scene.getActiveGame().getBoard().getCurrentPlayer().getAlliance() == scene.getActiveGame().getOpponentAlliance() ? " - makes a move" : "";
        String playerTurn = scene.getActiveGame().getBoard().getCurrentPlayer().getAlliance() == scene.getActiveGame().getPlayerAlliance() ? " - makes a move" : "";
        System.out.println("############################################################");
        printMoveLog(scene);
        System.out.println("------------------------------------------------------------");
        System.out.println(scene.getActiveGame().getOpponentAlliance().toString() + ": " + scene.getActiveGame().getOpponentName() + opponentTurn);
        printPlayerTakenPieces(scene, scene.getActiveGame().getOpponentAlliance().isWhite());
        printBoard(scene);
        printPlayerTakenPieces(scene, scene.getActiveGame().getPlayerAlliance().isWhite());
        System.out.println(scene.getActiveGame().getPlayerAlliance().toString() + ": " + scene.getActiveGame().getPlayerName() + playerTurn);
        System.out.println("############################################################");
    }

    private void printBoard(Scene scene) {
        StringBuilder builder = new StringBuilder();
        builder.append(scene.getActiveGame().getPlayerAlliance() == Alliance.WHITE ? "8| " : "1| ");
        for (int i = 0; i < BoardUtils.TOTAL_NUMBER_OF_TILES; i++) {
            int index = scene.getActiveGame().getPlayerAlliance() == Alliance.WHITE ? i : BoardUtils.TOTAL_NUMBER_OF_TILES - 1 - i;
            String tileText = scene.getActiveGame().getBoard().getTile(index).toString();
            builder.append(tileText).append(" ");
            if ((i + 1) % BoardUtils.NUMBER_OF_TILES_IN_ROW == 0) {
                builder.append(System.lineSeparator());
                if (scene.getActiveGame().getPlayerAlliance() == Alliance.WHITE) {
                    if (i != BoardUtils.TOTAL_NUMBER_OF_TILES - 1) {
                        builder.append(8 - (index + 1) / 8).append("| ");
                    }
                }
                else {
                    if (index != 0) {
                        builder.append(8 - (index - 1) / 8).append("| ");
                    }
                }
            }
        }
        builder.append(" +");
        for (char i = 0; i < 8; i++) builder.append("--");
        builder.append(System.lineSeparator());
        builder.append("   ");
        for (char i = 'a'; i <= 'h'; i++) {
            char letter = scene.getActiveGame().getPlayerAlliance() == Alliance.WHITE ? i : (char) ('a' + 'h' - i);
            builder.append(letter).append(" ");
        }
        builder.append(System.lineSeparator());
        System.out.println(builder);
    }

    private void printPlayerTakenPieces(Scene scene, boolean isWhite) {
        List<Piece> takenPieces = new ArrayList<>();
        for (Pair<Move, String> logEntry : scene.getActiveGame().getMoveLog().getMoves()) {
            Move move = logEntry.getFirst();
            if (move.isAttack()) {
                Piece takenPiece = move.getAttackedPiece();
                if (takenPiece.getAlliance().isWhite() && !isWhite) {
                    takenPieces.add(takenPiece);
                }
                else if (takenPiece.getAlliance().isBlack() && isWhite) {
                    takenPieces.add(takenPiece);
                }
            }
        }
        takenPieces.sort((o1, o2) -> Ints.compare(o1.getType().getPieceValue(), o2.getType().getPieceValue()));
        for (Piece piece : takenPieces) {
            System.out.print(piece.toString() + " ");
        }
        System.out.println();
    }

    private void printMoveLog(final Scene scene) {
        System.out.println("  WHITE  |  BLACK  ");
        for (Pair<Move, String> logEntry : scene.getActiveGame().getMoveLog().getMoves()) {
            Move move = logEntry.getFirst();
            String moveText = logEntry.getSecond();
            if (move.getMovedPiece() == null || move.getMovedPiece().getAlliance().isWhite()) {
                System.out.printf("%9s", moveText);
            }
            if (move.getMovedPiece() == null || move.getMovedPiece().getAlliance().isBlack()) {
                System.out.printf("|%9s\n", moveText);
            }
        }
        System.out.println();
    }
}
