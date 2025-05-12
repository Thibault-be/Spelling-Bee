package be.thibault.spellingbee.domain.game;

public interface GameService {

    GameState startNewGame();

    String verifyGuess(String guess, String gameId);

    GameState getGameById(String id);

}
