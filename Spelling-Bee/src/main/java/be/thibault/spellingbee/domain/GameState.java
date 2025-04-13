package be.thibault.spellingbee.domain;

import be.thibault.spellingbee.domain.letterselection.LetterSelection;

import java.util.Set;
import java.util.UUID;

public class GameState {

    private final String gameId;
    private final LetterSelection letterSelection;
    private final Set<String> possibleWords;
    private final Set<String> foundWords;

    public GameState(String gameId, LetterSelection letterSelection, Set<String> possibleWords, Set<String> foundWords) {
        this.gameId = UUID.randomUUID().toString();
        this.letterSelection = letterSelection;
        this.possibleWords = possibleWords;
        this.foundWords = foundWords;
    }
}
