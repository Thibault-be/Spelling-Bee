package be.thibault.spellingbee.domain.lettercombination.model;

public record FiveLetterCombo(String fiveLetterCombo) implements MultiLetterCombo {

    private static final int LETTER_COMBO_LENGTH = 5;

    public FiveLetterCombo {
        if (fiveLetterCombo.length() != LETTER_COMBO_LENGTH){
            throw new IllegalArgumentException("Should be a " + LETTER_COMBO_LENGTH + " letter fiveLetterCombo");
        }
    }

    @Override
    public String getComboAsString() {
        return this.fiveLetterCombo;
    }
}
