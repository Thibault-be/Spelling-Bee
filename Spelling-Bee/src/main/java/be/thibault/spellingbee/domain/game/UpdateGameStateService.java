package be.thibault.spellingbee.domain.game;

import be.thibault.spellingbee.domain.enums.GuessResult;

import java.util.Set;

public interface UpdateGameStateService {

    GuessResult updateGameState(GameState gameState, String guess);

    int determineMaxScore(Set<String> possibleWords);
}
