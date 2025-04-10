package be.thibault.spellingbee.domain;

import be.thibault.spellingbee.domain.lettercombo.LetterCombos;

public interface LetterCombosProvider {

    LetterCombos generateLetterCombos(LetterSelection letterSelection);

}
