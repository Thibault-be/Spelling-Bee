package be.thibault.spellingbee.adapters.web;

import be.thibault.spellingbee.configuration.FreemarkerConfig;
import be.thibault.spellingbee.domain.game.GameService;
import be.thibault.spellingbee.domain.game.GameState;
import be.thibault.spellingbee.domain.game.PreviousGameInfo;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping ("/spelling-bollie")
public class GameController {

    private static final Logger log = LoggerFactory.getLogger(GameController.class);
    private final GameService gameService;
    private final FreemarkerConfig freemarkerConfig;

    public GameController(GameService gameService, FreemarkerConfig freemarkerConfig) {
        this.gameService = gameService;
        this.freemarkerConfig = freemarkerConfig;
    }

    @GetMapping ("/start-game")
    public String startNewGame(){
        log.info("Starting a new game");
        GameState gameState = gameService.startNewGame();
        Map<String, Object> dataModel = generateDataModel(gameState);
        return generateHtml(dataModel, "main-screen.ftl");
    }

    @PostMapping ("/try-guess")
    public String tryGuess(@RequestParam String guess,
                           @RequestParam String gameId){
        String verification = gameService.verifyGuess(guess, gameId);
        Map<String, Object> dataModel = generateDataModel(gameService.getGameById(gameId), verification);

        return generateHtml(dataModel, "main-screen.ftl");
    }

    @GetMapping("/previous-games")
    public String previousGames(){
        List<PreviousGameInfo> previousGames = this.gameService.getPreviousGames();
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("previousGames", previousGames);

        return generateHtml(dataModel, "games.ftl");
    }

    @GetMapping("/resume-game/{gameId}")
    public String resumeGame(@PathVariable String gameId){
        System.out.println("I am in resume game");

        GameState gameState = gameService.getGameById(gameId);
        Map<String, Object> dataModel = generateDataModel(gameState);


        String html = generateHtml(dataModel, "main-screen.ftl");

        return generateHtml(dataModel, "main-screen.ftl");
    }

    private String generateHtml(Map<String, Object> dataModel, String templateName){
        try {
            Template template = freemarkerConfig.freemarkerConfiguration().getTemplate(templateName);
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
        dataModel.put("possibleWords", gameState.getPossibleWords());
        dataModel.put("score", gameState.getScore());
        dataModel.put("letterLayout", gameState.getLetterSelection().getFrontendLetterLayout());
        dataModel.put("ranking", gameState.getRanking().getFrontName());

        return dataModel;
    }

    private Map<String, Object> generateDataModel(GameState gameState, String verifyGuess){
        Map<String, Object> dataModel = generateDataModel(gameState);
        dataModel.put("lastGuess", verifyGuess);

        return dataModel;
    }


}
