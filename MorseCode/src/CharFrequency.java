public class CharFrequency implements Comparable<CharFrequency> {
    private char _char;
    private int _frequency;

    public CharFrequency(char ch) {
        this._char = ch;
        this._frequency = 1;
    }

    public char getChar() {
        return this._char;
    }

    public int getFrequency() {
        return this._frequency;
    }

    public void increaseFrequency() {
        this._frequency++;
    }

    @Override
    public int compareTo(CharFrequency o) {
        if (this._char == o._char) return 0;
        int diff = this._frequency - o._frequency;
        if (diff != 0) return diff;
        else return this._char - o._char;
    }
}
