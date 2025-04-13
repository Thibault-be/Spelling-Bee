package be.thibault.spellingbee.domain.lettercombination.model;

public record FiveLetterCombo(String letterCombo) implements MultiLetterCombo {

    private static final int LETTER_COMBO_LENGTH = 5;

    public FiveLetterCombo {
        if (letterCombo.length() != LETTER_COMBO_LENGTH){
            throw new IllegalArgumentException("Should be a " + LETTER_COMBO_LENGTH + " letter letterCombo");
        }
    }
}
