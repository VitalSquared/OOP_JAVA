package ru.nsu.spirin.chess.view.swing.board;

import javax.swing.JLabel;

class TileCaptionPanel extends JLabel {
    private final boolean isEmpty;
    private final boolean isLetter;
    private final char    letter;
    private final int     number;

    public TileCaptionPanel(boolean isEmpty, boolean isLetter, char letter, int number) {
        super("");
        this.setHorizontalAlignment(CENTER);
        this.setVerticalAlignment(CENTER);
        this.isEmpty = isEmpty;
        this.isLetter = isLetter;
        this.letter = letter;
        this.number = number;
    }

    public void updateText(boolean inverted) {
        this.setText(isEmpty ?
                "" :
                (isLetter ?
                        (inverted ?
                                getInvertedLetter() + "" :
                                Character.toLowerCase(this.letter) + "") :
                        (inverted ?
                                getInvertedNumber() + "" :
                                this.number + "")));
    }

    private char getInvertedLetter() {
        return (char) Character.toLowerCase('a' + 'h' - (int) Character.toLowerCase(letter));
    }

    private int getInvertedNumber() {
        return 8 + 1 - number;
    }
}
