package be.thibault.spellingbee.domain.lettercombination.model;

import java.util.Set;

public record LetterCombos(Set<FourLetterCombo> fourLetterCombos,
                           Set<FiveLetterCombo> fiveLetterCombos) {
}
