package be.thibault.spellingbee.domain.lettercombination.service;

import be.thibault.spellingbee.domain.letterselection.LetterSelection;
import be.thibault.spellingbee.domain.lettercombination.model.LetterCombos;

public interface LetterCombosProvider {

    LetterCombos generateLetterCombos(LetterSelection letterSelection);

}
