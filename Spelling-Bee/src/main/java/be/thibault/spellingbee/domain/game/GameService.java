package be.thibault.spellingbee.domain.game;

public interface GameService {

    GameState startNewGame();

    void updateGameState();

}
