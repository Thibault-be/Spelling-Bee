package be.thibault.spellingbee.domain.wordsolution;

public record FourLetterSolution(String fourLetterSolution) implements MultiLetterSolution {

    private static final int LETTER_SOLUTION_LENGTH = 5;

    public FourLetterSolution {
        if (fourLetterSolution.length() != LETTER_SOLUTION_LENGTH){
            throw new IllegalArgumentException("Should be a " + LETTER_SOLUTION_LENGTH + " letter letterCombo");
        }
    }
}
