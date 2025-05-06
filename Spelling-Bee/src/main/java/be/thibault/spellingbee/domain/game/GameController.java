package be.thibault.spellingbee.domain.game;

import be.thibault.spellingbee.config.FreemarkerConfig;
import be.thibault.spellingbee.domain.letterselection.LetterSelection;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
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
        Map<String, Object> dataModel = generateDataModel(gameState);
        return generateHtml(dataModel);
    }

    @GetMapping ("/{id}")
    public GameState getGameById(@PathVariable String id){
        return gameService.getGameById(id);
    }


    //todo: change in postmapping with proper frontend
    @GetMapping ("/try-guess")
    public String tryGuess(@RequestParam String guess,
                           @RequestParam String gameId){
        String verification = gameService.verifyGuess(guess, gameId);
        Map<String, Object> dataModel = generateDataModel(gameService.getGameById(gameId), verification);

        return generateHtml(dataModel);
    }

    private String generateHtml(Map<String, Object> dataModel){
        try {
            Template template = freemarkerConfig.freemarkerConfiguration().getTemplate("post.ftl");
            Writer out = new StringWriter();
            template.process(dataModel, out);
            return out.toString();
        } catch (TemplateException | IOException e){
            System.out.println(e.getMessage());
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
        dataModel.put("possibleWords", gameState.getPossibleWords());
        dataModel.put("score", gameState.getScore());
        dataModel.put("letterLayout", gameState.getLetterSelection().getFrontendLetterLayout());

        return dataModel;
    }

    private Map<String, Object> generateDataModel(GameState gameState, String verifyGuess){
        Map<String, Object> dataModel = generateDataModel(gameState);
        dataModel.put("lastGuess", verifyGuess);

        return dataModel;
    }


}
