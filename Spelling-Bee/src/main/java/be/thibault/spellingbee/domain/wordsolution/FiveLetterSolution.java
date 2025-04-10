package be.thibault.spellingbee.domain.wordsolution;

import java.util.List;

public record FiveLetterSolution(String fiveLetterSolution) implements MultiLetterSolution {

    private static final int LETTER_SOLUTION_LENGTH = 5;

    public FiveLetterSolution {
        if (fiveLetterSolution.length() != LETTER_SOLUTION_LENGTH){
            throw new IllegalArgumentException("Should be a " + LETTER_SOLUTION_LENGTH + " letter letterCombo");
        }
    }
}
