package be.thibault.spellingbee.domain.game;

import be.thibault.spellingbee.adapters.repository.GameStateRepository;
import be.thibault.spellingbee.domain.enums.GuessResult;
import be.thibault.spellingbee.domain.letterselection.LetterSelection;
import be.thibault.spellingbee.domain.letterselection.LetterSelectionProvider;
import be.thibault.spellingbee.domain.localdictionary.LocalDictionaryComparison;
import be.thibault.spellingbee.domain.localdictionary.LocalDictionaryService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class GameServiceImpl implements GameService {

    private final LetterSelectionProvider letterSelectionProvider;
    private final LocalDictionaryService localDictionaryService;
    private final GameStateRepository gameStateRepository;
    private final UpdateGameStateService updateGameStateService;

    public GameServiceImpl(LetterSelectionProvider letterSelectionProvider,
                           LocalDictionaryService localDictionaryService,
                           GameStateRepository gameStateRepository,
                           UpdateGameStateService updateGameStateService) {
        this.letterSelectionProvider = letterSelectionProvider;
        this.localDictionaryService = localDictionaryService;
        this.gameStateRepository = gameStateRepository;
        this.updateGameStateService = updateGameStateService;
    }

    @Override
    public GameState startNewGame() {

        LetterSelection letterSelection = this.letterSelectionProvider.getLetterSelection();
        LocalDictionaryComparison localDictionaryComparison = this.localDictionaryService.getLocalDictionaryComparison(letterSelection);

        GameState gameState = new GameState(letterSelection, localDictionaryComparison.matchingEntries());
        int maxScore = determineMaxScore(gameState.getPossibleWords());
        gameState.setMaxScore(maxScore);
        gameStateRepository.save(gameState);

        return gameState;
    }

    @Override
    public String verifyGuess(String guess, String gameId) {
        GameState gameState = getGameById(gameId);
        GuessResult guessResult = this.updateGameStateService.updateGameState(gameState, guess);
        return guessResult.getDescription();
    }

    public GameState getGameById(String id) {
        //todo: IDs need to be handled much better
        Optional<GameState> gameStateOptional = this.gameStateRepository.findById(id.replace(",", ""));
        return gameStateOptional.orElseThrow();
    }

    private int determineMaxScore(Set<String> possibleWords) {
        return this.updateGameStateService.determineMaxScore(possibleWords);
    }
}
