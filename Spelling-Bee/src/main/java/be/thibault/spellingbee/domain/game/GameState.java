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
    private int score;

    public GameState(LetterSelection letterSelection, Set<String> possibleWords) {
        this.gameId = UUID.randomUUID().toString();
        this.letterSelection = letterSelection;
        this.possibleWords = possibleWords;
        this.foundWords = new HashSet<>();
        this.score = 0;
    }

    public void addGuessToFoundWords(String guess){
        this.foundWords.add(guess);
    }

    public void  addScore(String guess){
        int length = guess.length();

        this.score += guess.length() - 4;

        //todo: incorrect, check if all letters were used;
        if (guess.length() == 7) {
            this.score += 7;
        }


    }




    // Getters

    public String getGameId() {
        return gameId;
    }

    public LetterSelection getLetterSelection() {
        return letterSelection;
    }

    public Set<String> getPossibleWords() {
        return possibleWords;
    }

    public Set<String> getFoundWords() {
        return foundWords;
    }
}
