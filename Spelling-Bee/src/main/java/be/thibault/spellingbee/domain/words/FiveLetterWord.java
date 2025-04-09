package be.thibault.spellingbee.domain.words;

public record FiveLetterWord(String word) {

    private static final int WORD_LENGTH = 5;

    public FiveLetterWord {
        if (word.length() != WORD_LENGTH){
            throw new IllegalArgumentException("Should be a " + WORD_LENGTH + " letter word");
        }
    }
}
