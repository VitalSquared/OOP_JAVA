package ru.nsu.spirin.chess.model.board;

import com.google.common.collect.ImmutableList;
import ru.nsu.spirin.chess.model.player.Alliance;
import ru.nsu.spirin.chess.model.board.tile.Tile;
import ru.nsu.spirin.chess.model.move.Move;
import ru.nsu.spirin.chess.model.pieces.Bishop;
import ru.nsu.spirin.chess.model.pieces.King;
import ru.nsu.spirin.chess.model.pieces.Knight;
import ru.nsu.spirin.chess.model.pieces.Pawn;
import ru.nsu.spirin.chess.model.pieces.Piece;
import ru.nsu.spirin.chess.model.pieces.Queen;
import ru.nsu.spirin.chess.model.pieces.Rook;
import ru.nsu.spirin.chess.model.player.BlackPlayer;
import ru.nsu.spirin.chess.model.player.Player;
import ru.nsu.spirin.chess.model.player.WhitePlayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Board implements Serializable {
    private final List<Tile> gameBoard;

    private final Collection<Piece> whitePieces;
    private final Collection<Piece> blackPieces;

    private final WhitePlayer whitePlayer;
    private final BlackPlayer blackPlayer;
    private final Player      currentPlayer;

    private final Pawn enPassantPawn;

    Board(BoardBuilder builder) {
        this.gameBoard = createGameBoard(builder);
        this.whitePieces = calculateActivePieces(Alliance.WHITE);
        this.blackPieces = calculateActivePieces(Alliance.BLACK);
        this.enPassantPawn = builder.getEnPassantPawn();

        Collection<Move> whiteStandardLegalMoves = calculateLegalMoves(this.whitePieces);
        Collection<Move> blackStandardLegalMoves = calculateLegalMoves(this.blackPieces);

        this.whitePlayer = new WhitePlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
        this.blackPlayer = new BlackPlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
        this.currentPlayer = builder.getNextMoveMaker().choosePlayer(this.whitePlayer, this.blackPlayer);

        if (builder.isWhitePlayerResigned()) this.whitePlayer.resign();
        if (builder.isBlackPlayerResigned()) this.blackPlayer.resign();
    }

    public static List<Tile> createGameBoard(BoardBuilder builder) {
        Tile[] tiles = new Tile[BoardUtils.TOTAL_NUMBER_OF_TILES];
        for (int i = 0; i < BoardUtils.TOTAL_NUMBER_OF_TILES; i++) {
            tiles[i] = Tile.createTile(i, builder.getBoardConfig().get(i));
        }
        return ImmutableList.copyOf(tiles);
    }

    public static Board createStandardBoard() {
        BoardBuilder builder = new BoardBuilder();
        builder.setPiece(new Rook(Alliance.BLACK, 0));
        builder.setPiece(new Knight(Alliance.BLACK, 1));
        builder.setPiece(new Bishop(Alliance.BLACK, 2));
        builder.setPiece(new Queen(Alliance.BLACK, 3));
        builder.setPiece(new King(Alliance.BLACK, 4, true, true));
        builder.setPiece(new Bishop(Alliance.BLACK, 5));
        builder.setPiece(new Knight(Alliance.BLACK, 6));
        builder.setPiece(new Rook(Alliance.BLACK, 7));
        builder.setPiece(new Pawn(Alliance.BLACK, 8));
        builder.setPiece(new Pawn(Alliance.BLACK, 9));
        builder.setPiece(new Pawn(Alliance.BLACK, 10));
        builder.setPiece(new Pawn(Alliance.BLACK, 11));
        builder.setPiece(new Pawn(Alliance.BLACK, 12));
        builder.setPiece(new Pawn(Alliance.BLACK, 13));
        builder.setPiece(new Pawn(Alliance.BLACK, 14));
        builder.setPiece(new Pawn(Alliance.BLACK, 15));
        builder.setPiece(new Pawn(Alliance.WHITE, 48));
        builder.setPiece(new Pawn(Alliance.WHITE, 49));
        builder.setPiece(new Pawn(Alliance.WHITE, 50));
        builder.setPiece(new Pawn(Alliance.WHITE, 51));
        builder.setPiece(new Pawn(Alliance.WHITE, 52));
        builder.setPiece(new Pawn(Alliance.WHITE, 53));
        builder.setPiece(new Pawn(Alliance.WHITE, 54));
        builder.setPiece(new Pawn(Alliance.WHITE, 55));
        builder.setPiece(new Rook(Alliance.WHITE, 56));
        builder.setPiece(new Knight(Alliance.WHITE, 57));
        builder.setPiece(new Bishop(Alliance.WHITE, 58));
        builder.setPiece(new Queen(Alliance.WHITE, 59));
        builder.setPiece(new King(Alliance.WHITE, 60, true, true));
        builder.setPiece(new Bishop(Alliance.WHITE, 61));
        builder.setPiece(new Knight(Alliance.WHITE, 62));
        builder.setPiece(new Rook(Alliance.WHITE, 63));
        builder.setMoveMaker(Alliance.WHITE);
        return builder.build();
    }

    public Collection<Piece> getWhitePieces() {
        return this.whitePieces;
    }

    public Collection<Piece> getBlackPieces() {
        return this.blackPieces;
    }

    public Collection<Piece> getAllPieces() {
        return Stream.concat(this.whitePieces.stream(), this.blackPieces.stream()).collect(Collectors.toList());
    }

    public Player getWhitePlayer() {
        return this.whitePlayer;
    }

    public Player getBlackPlayer() {
        return this.blackPlayer;
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public Pawn getEnPassantPawn() {
        return this.enPassantPawn;
    }

    public Tile getTile(int coordinate) {
        return this.gameBoard.get(coordinate);
    }

    private Collection<Piece> calculateActivePieces(Alliance alliance) {
        List<Piece> activePieces = new ArrayList<>();
        for (Tile tile : this.gameBoard) {
            if (tile.isTileOccupied()) {
                Piece piece = tile.getPiece();
                if (piece.getAlliance() == alliance) {
                    activePieces.add(piece);
                }
            }
        }
        return ImmutableList.copyOf(activePieces);
    }

    private Collection<Move> calculateLegalMoves(Collection<Piece> pieces) {
        List<Move> legalMoves = new ArrayList<>();
        for (Piece piece : pieces) {
            legalMoves.addAll(piece.calculateLegalMoves(this));
        }
        return ImmutableList.copyOf(legalMoves);
    }
}
