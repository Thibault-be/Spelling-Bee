package be.thibault.spellingbee.domain.game;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/spelling-bollie")
public class GameController {

    private final GameService gameService;
    private final ObjectMapper objectMapper;

    public GameController(GameService gameService) {
        this.gameService = gameService;
        this.objectMapper = new ObjectMapper();

    }

    @GetMapping ("/start-game")
    public String startNewGame(){
        GameState gameState = gameService.startNewGame();

        try {
            return objectMapper.writer().writeValueAsString(gameState);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping ("/{id}")
    public GameState getGameById(@PathVariable String id){
        return gameService.getGameById(id);
    }


    //post word
    @PostMapping ("/try-guess")
    public String tryGuess(@RequestParam String guess,
                           @RequestParam String gameId){

        return gameService.verifyGuess(guess, gameId);
    }

}
