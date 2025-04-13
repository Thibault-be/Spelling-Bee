package be.thibault.spellingbee.domain.game;

import be.thibault.spellingbee.domain.letterselection.LetterSelection;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class GameState {

    private final String gameId;
    private final LetterSelection letterSelection;
    private final Set<String> possibleWords;
    private final Set<String> foundWords;

    public GameState(LetterSelection letterSelection, Set<String> possibleWords) {
        this.gameId = UUID.randomUUID().toString();
        this.letterSelection = letterSelection;
        this.possibleWords = possibleWords;
        this.foundWords = new HashSet<>();
    }
}
