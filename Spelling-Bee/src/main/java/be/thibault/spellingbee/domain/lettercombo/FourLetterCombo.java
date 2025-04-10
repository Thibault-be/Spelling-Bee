package be.thibault.spellingbee.domain.lettercombo;

public record FourLetterCombo(String fourLetterCombo) implements MultiLetterCombo {

    private static final int LETTER_COMBO_LENGTH = 4;

    public FourLetterCombo {
        if (fourLetterCombo.length() != 4){
            throw new IllegalArgumentException("Should be a " + LETTER_COMBO_LENGTH + " letter letterCombo");
        }
    }
}
