package be.thibault.spellingbee.domain.wordsolution;

import java.util.List;

public record Solution(List<FourLetterSolution> fourLetterSolutions,
                       List<FiveLetterSolution> fiveLetterSolutions) {
}
