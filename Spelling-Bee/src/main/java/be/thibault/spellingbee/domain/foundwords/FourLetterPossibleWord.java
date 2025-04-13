package be.thibault.spellingbee.domain.foundwords;

public record FourLetterPossibleWord(String fourLetterSolution) implements MultiLetterPossibleWord {

    private static final int LETTER_SOLUTION_LENGTH = 5;

    public FourLetterPossibleWord {
        if (fourLetterSolution.length() != LETTER_SOLUTION_LENGTH){
            throw new IllegalArgumentException("Should be a " + LETTER_SOLUTION_LENGTH + " letter letterCombo");
        }
    }
}
