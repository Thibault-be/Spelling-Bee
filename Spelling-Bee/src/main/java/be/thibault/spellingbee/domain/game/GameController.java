package be.thibault.spellingbee.domain.game;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            System.out.println("trying");
            return objectMapper.writer().writeValueAsString(gameState);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    //post word

    //get gamestate


}
