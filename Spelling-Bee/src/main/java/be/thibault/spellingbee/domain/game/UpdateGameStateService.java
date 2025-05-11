package be.thibault.spellingbee.domain.game;

import be.thibault.spellingbee.domain.enums.GuessResult;

public interface UpdateGameStateService {

    GuessResult updateGameState(GameState gameState, String guess);
}
