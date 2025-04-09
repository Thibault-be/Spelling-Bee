package be.thibault.spellingbee.domain.words;

import java.util.List;

public record LetterCombos(List<FourLetterCombo> fourLetterCombos,
                           List<FiveLetterCombo> fiveLetterCombos) {
}
