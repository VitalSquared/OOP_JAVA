package ru.nsu.spirin.chess.player;

import ru.nsu.spirin.chess.board.BoardUtils;

public enum Alliance {
    WHITE {
        @Override
        public int getDirection() {
            return -1;
        }

        @Override
        public int getOppositeDirection() {
            return 1;
        }

        @Override
        public boolean isWhite() {
            return true;
        }

        @Override
        public boolean isBlack() {
            return false;
        }

        @Override
        public Alliance getOpposite() {
            return BLACK;
        }

        @Override
        public boolean isPawnPromotionTile(int tilePosition) {
            return BoardUtils.isPositionInRow(tilePosition, 8);
        }

        @Override
        public Player choosePlayer(Player whitePlayer, Player blackPlayer) {
            return whitePlayer;
        }
    },
    BLACK {
        @Override
        public int getDirection() {
            return 1;
        }

        @Override
        public int getOppositeDirection() {
            return -1;
        }

        @Override
        public boolean isWhite() {
            return false;
        }

        @Override
        public boolean isBlack() {
            return true;
        }

        @Override
        public Alliance getOpposite() {
            return WHITE;
        }

        @Override
        public boolean isPawnPromotionTile(int tilePosition) {
            return BoardUtils.isPositionInRow(tilePosition, 1);
        }

        @Override
        public Player choosePlayer(Player whitePlayer, Player blackPlayer) {
            return blackPlayer;
        }
    };

    public abstract int getDirection();

    public abstract int getOppositeDirection();

    public abstract boolean isWhite();

    public abstract boolean isBlack();

    public abstract Alliance getOpposite();

    public abstract boolean isPawnPromotionTile(int tilePosition);

    public abstract Player choosePlayer(Player whitePlayer, Player blackPlayer);
}
