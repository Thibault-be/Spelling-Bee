package be.thibault.spellingbee.domain.game;

import be.thibault.spellingbee.domain.enums.Ranking;
import be.thibault.spellingbee.domain.letterselection.LetterSelection;

public class PreviousGameInfo {

    private String id;
    private LetterSelection letterSelection;
    private Ranking ranking;

    public PreviousGameInfo(String id, LetterSelection letterSelection, Ranking ranking) {
        this.id = id;
        this.letterSelection = letterSelection;
        this.ranking = ranking;
    }

    public String getId() {
        return id;
    }

    public LetterSelection getLetterSelection() {
        return letterSelection;
    }

    public Ranking getRanking() {
        return ranking;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLetterSelection(LetterSelection letterSelection) {
        this.letterSelection = letterSelection;
    }

    public void setRanking(Ranking ranking) {
        this.ranking = ranking;
    }
}
