package be.thibault.spellingbee.domain.foundwords;

import java.util.Set;

public record PossibleWords(Set<FourLetterPossibleWord> fourLetterSolutions,
                            Set<FiveLetterPossibleWord> fiveLetterFoundWords) {
}
