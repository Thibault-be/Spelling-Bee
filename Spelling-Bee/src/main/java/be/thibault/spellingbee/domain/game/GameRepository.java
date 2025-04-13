package be.thibault.spellingbee.domain.game;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class GameRepository {

    private final Map<String, GameState> gameRepository = new HashMap();

    public void saveGame(String gameId, GameState gameState){
        gameRepository.put(gameId, gameState);
    }

    public GameState findByGameId(String gameId){
        return this.gameRepository.get(gameId);
    }

}
