package be.thibault.spellingbee.domain.foundwords;

public record FiveLetterPossibleWord(String fiveLetterSolution) implements MultiLetterPossibleWord {

    private static final int LETTER_SOLUTION_LENGTH = 5;

    public FiveLetterPossibleWord {
        if (fiveLetterSolution.length() != LETTER_SOLUTION_LENGTH){
            throw new IllegalArgumentException("Should be a " + LETTER_SOLUTION_LENGTH + " letter letterCombo");
        }
    }
}
