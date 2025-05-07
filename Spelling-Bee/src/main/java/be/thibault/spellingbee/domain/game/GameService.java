package be.thibault.spellingbee.domain.game;

public interface GameService {

    GameState startNewGame();

    void updateGameState(GameState gameState, String guess);

    String verifyGuess(String guess, String gameId);

    GameState getGameById(String id);

}
