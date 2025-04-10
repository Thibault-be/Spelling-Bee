package be.thibault.spellingbee.domain.lettercombo;

import java.util.Set;

public record LetterCombos(Set<FourLetterCombo> fourLetterCombos,
                           Set<FiveLetterCombo> fiveLetterCombos) {
}
