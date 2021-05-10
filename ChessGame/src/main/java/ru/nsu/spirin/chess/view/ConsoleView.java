package ru.nsu.spirin.chess.view;

import com.google.common.primitives.Ints;
import ru.nsu.spirin.chess.board.BoardUtils;
import ru.nsu.spirin.chess.communication.NetworkEntity;
import ru.nsu.spirin.chess.controller.Controller;
import ru.nsu.spirin.chess.move.Move;
import ru.nsu.spirin.chess.pieces.Piece;
import ru.nsu.spirin.chess.player.Alliance;
import ru.nsu.spirin.chess.scene.Scene;
import ru.nsu.spirin.chess.scene.SceneState;
import ru.nsu.spirin.chess.utils.ThreadUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class ConsoleView extends GameView {
    private final Scanner    scanner;

    public ConsoleView(Scene scene, Controller controller) {
        super(scene, controller);
        this.scanner = new Scanner(System.in);

        ThreadUtils.submitThread(new Thread(() -> {
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
                controller.execute(command + " " + extra, false);
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
            System.out.println("MAIN MENU");
            System.out.println("new_game \tenter new game menu");
            System.out.println("high_scores \tenter high scores menu");
            System.out.println("about \tenter about menu");
            System.out.println("exit \texit game");
        }
    }

    private void updateNewGamePanel() {
        if (getScene().getSceneState() == SceneState.NEW_GAME_MENU) {
            System.out.println("NEW GAME");
            System.out.println("start <player team: [white | black]> <player name>\tplay game with ai");
            System.out.println("host <ip> <port> <player name> \thost game");
            System.out.println("join <ip> <port> <player name> \tjoin game");
            System.out.println("back \treturn to previous menu");
        }
    }

    private void updateHighScoresPanel() {
        if (getScene().getSceneState() == SceneState.HIGH_SCORES_MENU) {
            System.out.println("List of top 10 high scores: ");

            System.out.println("\n\nback \treturn to previous menu");
        }
    }

    private void updateAboutPanel() {
        if (getScene().getSceneState() == SceneState.ABOUT_MENU) {
            System.out.println("This is a chess game. Created by Vitaly Spirin");

            System.out.println("\n\nback \treturn to previous menu");
        }
    }

    private void updateConnectionPanel() {
        if (getScene().getSceneState() == SceneState.CONNECTION_MENU) {
            NetworkEntity networkEntity = (NetworkEntity) getScene().getActiveGame();
            switch (networkEntity.connected()) {
                case FAILED -> {
                    System.out.println("Failed to connect");
                    System.out.println("\n\nback \treturn to previous menu");
                }
                case NOT_CONNECTED -> {
                    System.out.println("Awaiting connection");
                    System.out.println("\n\ncancel \tCancel connection and return to previous menu");
                }
                case CONNECTED -> {
                    System.out.println("WHITE TEAM\t\t\t\tBLACK TEAM");

                    System.out.print((networkEntity.getOpponentTeam() == Alliance.WHITE ? networkEntity.getOpponentName() : "") + "\t\t");
                    System.out.print((networkEntity.getOpponentTeam() == null ? networkEntity.getOpponentName() : "") + "\t\t");
                    System.out.print((networkEntity.getOpponentTeam() == Alliance.BLACK ? networkEntity.getOpponentName() : ""));
                    System.out.println();

                    System.out.print((networkEntity.getPlayerAlliance() == Alliance.WHITE ? networkEntity.getPlayerName() : "") + "\t\t");
                    System.out.print((networkEntity.getPlayerAlliance() == null ? networkEntity.getPlayerName() : "") + "\t\t");
                    System.out.print((networkEntity.getPlayerAlliance() == Alliance.BLACK ? networkEntity.getPlayerName() : ""));
                    System.out.println();

                    System.out.println();

                    System.out.println((networkEntity.getOpponentTeam() == Alliance.WHITE && networkEntity.getPlayerAlliance() != Alliance.WHITE ? (networkEntity.isOpponentReady() ? "Ready" : "Not Ready") : "") + "\t\t");
                    System.out.println("\t\t");
                    System.out.println((networkEntity.getOpponentTeam() == Alliance.BLACK && networkEntity.getPlayerAlliance() != Alliance.BLACK ? (networkEntity.isOpponentReady() ? "Ready" : "Not Ready") : "") + "\t\t");
                    System.out.println();

                    System.out.println((networkEntity.getPlayerAlliance() == Alliance.WHITE && networkEntity.getOpponentTeam() != Alliance.WHITE ? (networkEntity.isPlayerReady() ? "Ready" : "Not Ready") : ""));
                    System.out.println("\t\t");
                    System.out.println((networkEntity.getPlayerAlliance() == Alliance.BLACK && networkEntity.getOpponentTeam() != Alliance.BLACK ? (networkEntity.isPlayerReady() ? "Ready" : "Not Ready") : ""));
                    System.out.println();

                    System.out.println("\n\nteam <[white | none | black] \tchoose team");
                    if (networkEntity.getPlayerAlliance() != null && networkEntity.getPlayerAlliance() != networkEntity.getOpponentTeam()) {
                        System.out.println("ready \tset ready state");
                    }
                    System.out.println("disconnect \tdisconnect from game and return to previous menu");
                }
            }
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
                System.out.println("\n\n");
                System.out.println("\nproceed \tenter results menu");
            }
        }
    }

    private void updateResultsPanel() {
        if (getScene().getSceneState() == SceneState.RESULTS_MENU) {

            System.out.println("\n\nback \treturn to main menu");
        }
    }

    private void printBoardPanel() {
        Scene scene = getScene();
        System.out.println("############################################################");
        printMoveLog(scene);
        System.out.println("------------------------------------------------------------");
        System.out.println(scene.getActiveGame().getOpponentTeam().toString() + ": " + scene.getActiveGame().getOpponentName());
        printPlayerTakenPieces(scene, false);
        printBoard(scene);
        printPlayerTakenPieces(scene, true);
        System.out.println(scene.getActiveGame().getPlayerAlliance().toString() + ": " + scene.getActiveGame().getPlayerName());
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
        for (char i = 0; i < 8; i++) {
            builder.append("--");
        }
        builder.append(System.lineSeparator());
        builder.append("   ");
        for (char i = 'a'; i <= 'h'; i++) {
            char letter = scene.getActiveGame().getPlayerAlliance() == Alliance.WHITE ? i : (char) ('a' + 'h' - i);
            builder.append(letter).append(" ");
        }
        builder.append(System.lineSeparator());
        System.out.println(builder);
    }

    private void printPlayerTakenPieces(final Scene scene, final boolean isWhite) {
        final List<Piece> takenPieces = new ArrayList<>();
        for (final Move move : scene.getActiveGame().getMoveLog().getMoves()) {
            if (move.isAttack()) {
                final Piece takenPiece = move.getAttackedPiece();
                if (takenPiece.getAlliance().isWhite() && !isWhite) {
                    takenPieces.add(takenPiece);
                }
                else if (takenPiece.getAlliance().isBlack() && isWhite) {
                    takenPieces.add(takenPiece);
                }
            }
        }
        takenPieces.sort((o1, o2) -> Ints.compare(o1.getType().getPieceValue(), o2.getType().getPieceValue()));
        for (final Piece piece : takenPieces) {
            System.out.print(piece.toString() + " ");
        }
        System.out.println();
    }

    private void printMoveLog(final Scene scene) {
        System.out.println(" WHITE | BLACK ");
        for (Move move : scene.getActiveGame().getMoveLog().getMoves()) {
            String moveText = move.toString();
            if (move.getMovedPiece().getAlliance().isWhite()) {
                System.out.printf("%7s", moveText);
            }
            if (move.getMovedPiece().getAlliance().isBlack()) {
                System.out.printf("|%7s\n", moveText);
            }
        }
        System.out.println();
    }
}
