package be.thibault.spellingbee.domain.game;

import java.util.List;

public interface GameService {

    GameState startNewGame();

    String verifyGuess(String guess, String gameId);

    GameState getGameById(String id);

    List<PreviousGameInfo> getPreviousGames();

}
