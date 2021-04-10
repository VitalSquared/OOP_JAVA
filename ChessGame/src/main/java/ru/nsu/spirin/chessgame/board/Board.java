package ru.nsu.spirin.chessgame.board;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import ru.nsu.spirin.chessgame.player.Alliance;
import ru.nsu.spirin.chessgame.board.tile.Tile;
import ru.nsu.spirin.chessgame.move.Move;
import ru.nsu.spirin.chessgame.pieces.Bishop;
import ru.nsu.spirin.chessgame.pieces.King;
import ru.nsu.spirin.chessgame.pieces.Knight;
import ru.nsu.spirin.chessgame.pieces.Pawn;
import ru.nsu.spirin.chessgame.pieces.Piece;
import ru.nsu.spirin.chessgame.pieces.Queen;
import ru.nsu.spirin.chessgame.pieces.Rook;
import ru.nsu.spirin.chessgame.player.BlackPlayer;
import ru.nsu.spirin.chessgame.player.Player;
import ru.nsu.spirin.chessgame.player.WhitePlayer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Board {

    private final List<Tile> gameBoard;
    private final Collection<Piece> whitePieces;
    private final Collection<Piece> blackPieces;

    private final WhitePlayer whitePlayer;
    private final BlackPlayer blackPlayer;
    private final Player currentPlayer;

    private final Pawn enPassantPawn;

    Board(BoardBuilder boardBuilder) {
        this.gameBoard = createGameBoard(boardBuilder);
        this.whitePieces = calculateActivePieces(this.gameBoard, Alliance.WHITE);
        this.blackPieces = calculateActivePieces(this.gameBoard, Alliance.BLACK);
        this.enPassantPawn = boardBuilder.enPassantPawn;

        final Collection<Move> whiteStandardLegalMoves = calculateLegalMoves(this.whitePieces);
        final Collection<Move> blackStandardLegalMoves = calculateLegalMoves(this.blackPieces);

        this.whitePlayer = new WhitePlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
        this.blackPlayer = new BlackPlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
        this.currentPlayer = boardBuilder.nextMoveMaker.choosePlayer(this.whitePlayer, this.blackPlayer);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            final String tileText = this.gameBoard.get(i).toString();
            builder.append(tileText).append(" ");
            if ((i + 1) % BoardUtils.NUM_TILES_PER_ROW == 0) {
                builder.append(System.lineSeparator());
            }
        }
        return builder.toString();
    }

    public Collection<Piece> getWhitePieces() {
        return this.whitePieces;
    }

    public Collection<Piece> getBlackPieces() {
        return this.blackPieces;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public Player getBlackPlayer() {
        return blackPlayer;
    }

    public Pawn getEnPassantPawn() {
        return this.enPassantPawn;
    }

    private Collection<Move> calculateLegalMoves(final Collection<Piece> pieces) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final Piece piece : pieces) {
            legalMoves.addAll(piece.calculateLegalMoves(this));
        }
        return ImmutableList.copyOf(legalMoves);
    }

    private Collection<Piece> calculateActivePieces(final List<Tile> gameBoard,
                                                    final Alliance alliance) {
        List<Piece> activePieces = new ArrayList<>();
        for (final Tile tile : gameBoard) {
            if (tile.isTileOccupied()) {
                final Piece piece = tile.getPiece();
                if (piece.getPieceAlliance() == alliance) {
                    activePieces.add(piece);
                }
            }
        }
        return ImmutableList.copyOf(activePieces);
    }

    public Tile getTile(final int tileCoordinate) {
        return gameBoard.get(tileCoordinate);
    }

    public static List<Tile> createGameBoard(final BoardBuilder boardBuilder) {
        final Tile[] tiles = new Tile[BoardUtils.NUM_TILES];
        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            tiles[i] = Tile.createTile(i, boardBuilder.boardConfig.get(i));
        }
        return ImmutableList.copyOf(tiles);
    }

    public static Board createStandardBoard() {
        final BoardBuilder boardBuilder = new BoardBuilder();
        //black layout
        boardBuilder.setPiece(new Rook(Alliance.BLACK, 0));
        boardBuilder.setPiece(new Knight(Alliance.BLACK, 1));
        boardBuilder.setPiece(new Bishop(Alliance.BLACK, 2));
        boardBuilder.setPiece(new Queen(Alliance.BLACK, 3));
        boardBuilder.setPiece(new King(Alliance.BLACK, 4));
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
        //white layout
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
        boardBuilder.setPiece(new King(Alliance.WHITE, 60));
        boardBuilder.setPiece(new Bishop(Alliance.WHITE, 61));
        boardBuilder.setPiece(new Knight(Alliance.WHITE, 62));
        boardBuilder.setPiece(new Rook(Alliance.WHITE, 63));

        boardBuilder.setMoveMaker(Alliance.WHITE);
        return boardBuilder.build();
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public Iterable<Move> getAllLegalMoves() {
        return Iterables.unmodifiableIterable(Iterables.concat(this.whitePlayer.getLegalMoves(), this.blackPlayer.getLegalMoves()));
    }

}
