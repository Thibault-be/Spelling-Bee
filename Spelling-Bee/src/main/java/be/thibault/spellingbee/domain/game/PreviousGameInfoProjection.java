package be.thibault.spellingbee.domain.game;

import be.thibault.spellingbee.domain.enums.Ranking;
import be.thibault.spellingbee.domain.letterselection.LetterSelection;

public interface PreviousGameInfoProjection {
    String getId();
    LetterSelection getLetterSelection();
    Ranking getRanking();
}