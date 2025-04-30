package be.thibault.spellingbee.domain.game;

import be.thibault.spellingbee.config.FreemarkerConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.core.ParseException;
import freemarker.template.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping ("/spelling-bollie")
public class GameController {

    private final GameService gameService;
    private final ObjectMapper objectMapper;
    private final FreemarkerConfig freemarkerConfig;

    public GameController(GameService gameService, FreemarkerConfig freemarkerConfig) {
        this.gameService = gameService;
        this.objectMapper = new ObjectMapper();
        this.freemarkerConfig = freemarkerConfig;
    }

    @GetMapping ("/start-game")
    public String startNewGame(){
        GameState gameState = gameService.startNewGame();

        return generateHtml(gameState);
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


    private String generateHtml(GameState gameState){


        try {
            Map<String, Object> dataModel = generateDataModel(gameState);
            Template template = freemarkerConfig.freemarkerConfiguration().getTemplate("spelling-bollie.ftl");
            Writer out = new StringWriter();
            template.process(dataModel, out);
            return out.toString();
        } catch (TemplateException | IOException e){
            throw new RuntimeException("problem");
        }
    }


    private Map<String, Object> generateDataModel(GameState gameState){

        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("gameId", gameState.getGameId());
        dataModel.put("foundWords", gameState.getFoundWords());
        dataModel.put("vowelSelection", gameState.getVowelSelection());
        dataModel.put("consonantSelection", gameState.getConsonantSelection());
        dataModel.put("compulsoryLetter", gameState.getCompulsoryLetter());

        return dataModel;
    }


}
