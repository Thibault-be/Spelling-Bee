package be.thibault.spellingbee.domain.words;

import java.util.Set;

public record LetterCombos(Set<FourLetterCombo> fourLetterCombos,
                           Set<FiveLetterCombo> fiveLetterCombos) {
}
