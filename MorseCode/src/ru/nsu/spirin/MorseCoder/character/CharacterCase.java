package ru.nsu.spirin.MorseCoder.character;

import java.util.Comparator;

public enum CharacterCase {
    LOWER,
    UPPER;

    public static final Comparator<Character> CASE_INSENSITIVE_ORDER
            = new CaseInsensitiveComparator();

    private static class CaseInsensitiveComparator
            implements Comparator<Character>, java.io.Serializable {
        @java.io.Serial
        private static final long serialVersionUID = 8575799808933029326L;

        public int compare(Character c1, Character c2) {
            return Character.compare(Character.toLowerCase(c1), Character.toLowerCase(c2));
        }

        /** Replaces the de-serialized object. */
        @java.io.Serial
        private Object readResolve() { return CASE_INSENSITIVE_ORDER; }
    }
}
