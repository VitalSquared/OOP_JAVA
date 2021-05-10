package ru.nsu.spirin.chess.board;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import ru.nsu.spirin.chess.player.Alliance;
import ru.nsu.spirin.chess.board.tile.Tile;
import ru.nsu.spirin.chess.move.Move;
import ru.nsu.spirin.chess.pieces.Bishop;
import ru.nsu.spirin.chess.pieces.King;
import ru.nsu.spirin.chess.pieces.Knight;
import ru.nsu.spirin.chess.pieces.Pawn;
import ru.nsu.spirin.chess.pieces.Piece;
import ru.nsu.spirin.chess.pieces.Queen;
import ru.nsu.spirin.chess.pieces.Rook;
import ru.nsu.spirin.chess.player.BlackPlayer;
import ru.nsu.spirin.chess.player.Player;
import ru.nsu.spirin.chess.player.WhitePlayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Board implements Serializable {

    private final List<Tile>        gameBoard;
    private final Collection<Piece> whitePieces;
    private final Collection<Piece> blackPieces;
    private final WhitePlayer       whitePlayer;
    private final BlackPlayer       blackPlayer;
    private final Player            currentPlayer;
    private final Pawn              enPassantPawn;

    Board(BoardBuilder boardBuilder) {
        this.gameBoard = createGameBoard(boardBuilder);
        this.whitePieces = calculateActivePieces(Alliance.WHITE);
        this.blackPieces = calculateActivePieces(Alliance.BLACK);
        this.enPassantPawn = boardBuilder.getEnPassantPawn();

        Collection<Move> whiteStandardLegalMoves = calculateLegalMoves(this.whitePieces);
        Collection<Move> blackStandardLegalMoves = calculateLegalMoves(this.blackPieces);

        this.whitePlayer = new WhitePlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
        this.blackPlayer = new BlackPlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
        this.currentPlayer = boardBuilder.getNextMoveMaker().choosePlayer(this.whitePlayer, this.blackPlayer);

        if (boardBuilder.isWhitePlayerResigned()) this.whitePlayer.resign();
        if (boardBuilder.isBlackPlayerResigned()) this.blackPlayer.resign();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        for (Tile tile : gameBoard) {
            int cur = 31;
            Piece piece = tile.getPiece();
            if (piece != null) cur = piece.hashCode();
            hash += cur * tile.getCoordinate();
        }
        hash += 31 * blackPlayer.hashCode();
        hash += 31 * whitePlayer.hashCode();
        return hash;
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
        return whitePlayer;
    }

    public Player getBlackPlayer() {
        return blackPlayer;
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

    public static List<Tile> createGameBoard(BoardBuilder boardBuilder) {
        final Tile[] tiles = new Tile[BoardUtils.TOTAL_NUMBER_OF_TILES];
        for (int i = 0; i < BoardUtils.TOTAL_NUMBER_OF_TILES; i++) {
            tiles[i] = Tile.createTile(i, boardBuilder.getBoardConfig().get(i));
        }
        return ImmutableList.copyOf(tiles);
    }

    public static Board createStandardBoard() {
        final BoardBuilder boardBuilder = new BoardBuilder();
        boardBuilder.setPiece(new Rook(Alliance.BLACK, 0));
        boardBuilder.setPiece(new Knight(Alliance.BLACK, 1));
        boardBuilder.setPiece(new Bishop(Alliance.BLACK, 2));
        boardBuilder.setPiece(new Queen(Alliance.BLACK, 3));
        boardBuilder.setPiece(new King(Alliance.BLACK, 4, true, true));
        boardBuilder.setPiece(new Bishop(Alliance.BLACK, 5));
        boardBuilder.setPiece(new Knight(Alliance.BLACK, 6));
        boardBuilder.setPiece(new Rook(Alliance.BLACK, 7));
        boardBuilder.setPiece(new Pawn(Alliance.BLACK, 8));
        boardBuilder.setPiece(new Pawn(Alliance.BLACK, 9));
        boardBuilder.setPiece(new Pawn(Alliance.BLACK, 10));
        boardBuilder.setPiece(new Pawn(Alliance.BLACK, 11));
        boardBuilder.setPiece(new Pawn(Alliance.BLACK, 12));
        boardBuilder.setPiece(new Pawn(Alliance.BLACK, 13));
        boardBuilder.setPiece(new Pawn(Alliance.BLACK, 14));
        boardBuilder.setPiece(new Pawn(Alliance.BLACK, 15));
        boardBuilder.setPiece(new Pawn(Alliance.WHITE, 48));
        boardBuilder.setPiece(new Pawn(Alliance.WHITE, 49));
        boardBuilder.setPiece(new Pawn(Alliance.WHITE, 50));
        boardBuilder.setPiece(new Pawn(Alliance.WHITE, 51));
        boardBuilder.setPiece(new Pawn(Alliance.WHITE, 52));
        boardBuilder.setPiece(new Pawn(Alliance.WHITE, 53));
        boardBuilder.setPiece(new Pawn(Alliance.WHITE, 54));
        boardBuilder.setPiece(new Pawn(Alliance.WHITE, 55));
        boardBuilder.setPiece(new Rook(Alliance.WHITE, 56));
        boardBuilder.setPiece(new Knight(Alliance.WHITE, 57));
        boardBuilder.setPiece(new Bishop(Alliance.WHITE, 58));
        boardBuilder.setPiece(new Queen(Alliance.WHITE, 59));
        boardBuilder.setPiece(new King(Alliance.WHITE, 60, true, true));
        boardBuilder.setPiece(new Bishop(Alliance.WHITE, 61));
        boardBuilder.setPiece(new Knight(Alliance.WHITE, 62));
        boardBuilder.setPiece(new Rook(Alliance.WHITE, 63));

        boardBuilder.setMoveMaker(Alliance.WHITE);
        return boardBuilder.build();
    }

    public Iterable<Move> getAllLegalMoves() {
        return Iterables.unmodifiableIterable(Iterables.concat(this.whitePlayer.getLegalMoves(), this.blackPlayer.getLegalMoves()));
    }

    private Collection<Piece> calculateActivePieces(Alliance alliance) {
        List<Piece> activePieces = new ArrayList<>();
        for (Tile tile : this.gameBoard) {
            if (tile.isTileOccupied()) {
                final Piece piece = tile.getPiece();
                if (piece.getAlliance() == alliance) {
                    activePieces.add(piece);
                }
            }
        }
        return ImmutableList.copyOf(activePieces);
    }

    private Collection<Move> calculateLegalMoves(Collection<Piece> pieces) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final Piece piece : pieces) {
            legalMoves.addAll(piece.calculateLegalMoves(this));
        }
        return ImmutableList.copyOf(legalMoves);
    }
}
