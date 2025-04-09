package be.thibault.spellingbee.domain.words;

public record FourLetterCombo(String word) {

    private static final int WORD_LENGTH = 4;

    public FourLetterCombo {
        if (word.length() != 4){
            throw new IllegalArgumentException("Should be a " + WORD_LENGTH + " letter word");
        }
    }
}
