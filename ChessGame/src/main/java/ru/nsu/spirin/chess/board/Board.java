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
import ru.nsu.spirin.chess.player.ai.MiniMax;
import ru.nsu.spirin.chess.player.ai.MoveStrategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class Board {

    private final List<Tile>        gameBoard;
    private final Collection<Piece> whitePieces;
    private final Collection<Piece> blackPieces;
    private final WhitePlayer       whitePlayer;
    private final BlackPlayer       blackPlayer;
    private final Player            currentPlayer;
    private final Pawn              enPassantPawn;

    Board(BoardBuilder boardBuilder) {
        this.gameBoard = createGameBoard(boardBuilder);
        this.whitePieces = calculateActivePieces(this.gameBoard, Alliance.WHITE);
        this.blackPieces = calculateActivePieces(this.gameBoard, Alliance.BLACK);
        this.enPassantPawn = boardBuilder.getEnPassantPawn();

        final Collection<Move> whiteStandardLegalMoves = calculateLegalMoves(this.whitePieces);
        final Collection<Move> blackStandardLegalMoves = calculateLegalMoves(this.blackPieces);

        this.whitePlayer = new WhitePlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves, boardBuilder.isWhiteAI(), boardBuilder.getWhitePlayerName());
        this.blackPlayer = new BlackPlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves, boardBuilder.isBlackAI(), boardBuilder.getBlackPlayerName());
        this.currentPlayer = boardBuilder.getNextMoveMaker().choosePlayer(this.whitePlayer, this.blackPlayer);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        for (Tile tile : gameBoard) {
            int cur = 31;
            Piece piece = tile.getPiece();
            if (piece != null) cur = piece.hashCode();
            hash += cur * tile.getTileCoordinate();
        }
        return hash;
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

    public Tile getTile(final int tileCoordinate) {
        return this.gameBoard.get(tileCoordinate);
    }

    public static List<Tile> createGameBoard(final BoardBuilder boardBuilder) {
        final Tile[] tiles = new Tile[BoardUtils.NUM_TILES];
        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            tiles[i] = Tile.createTile(i, boardBuilder.getBoardConfig().get(i));
        }
        return ImmutableList.copyOf(tiles);
    }

    public static Board createStandardBoard(final boolean isWhiteAI, final boolean isBlackAI, final String whitePlayerName, final String blackPlayerName) {
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

        boardBuilder.setWhiteAI(isWhiteAI);
        boardBuilder.setBlackAI(isBlackAI);

        boardBuilder.setWhitePlayerName(whitePlayerName);
        boardBuilder.setBlackPlayerName(blackPlayerName);

        boardBuilder.setMoveMaker(Alliance.WHITE);
        return boardBuilder.build();
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public Iterable<Move> getAllLegalMoves() {
        return Iterables.unmodifiableIterable(Iterables.concat(this.whitePlayer.getLegalMoves(), this.blackPlayer.getLegalMoves()));
    }

    public Move checkAI(final int miniMaxDepth) {
        if (this.currentPlayer.isAI()) {
            final MoveStrategy miniMax = new MiniMax(miniMaxDepth);
            return miniMax.execute(this);
        }
        return null;
    }

    private Collection<Move> calculateLegalMoves(final Collection<Piece> pieces) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final Piece piece : pieces) {
            legalMoves.addAll(piece.calculateLegalMoves(this));
        }
        return ImmutableList.copyOf(legalMoves);
    }

    private Collection<Piece> calculateActivePieces(final List<Tile> gameBoard, final Alliance alliance) {
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
}
