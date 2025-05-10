package be.thibault.spellingbee.domain.game;

import be.thibault.spellingbee.adapters.repository.GameStateRepository;
import be.thibault.spellingbee.domain.externaldictionary.CommonWordChecker;

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
    private final CommonWordChecker commonWordChecker;
    private final GameStateRepository gameStateRepository;

    public GameServiceImpl(LetterSelectionProvider letterSelectionProvider,
                           LocalDictionaryService localDictionaryService,
                           CommonWordChecker commonWordChecker,
                           GameStateRepository gameStateRepository) {
        this.letterSelectionProvider = letterSelectionProvider;
        this.localDictionaryService = localDictionaryService;
        this.commonWordChecker = commonWordChecker;
        this.gameStateRepository = gameStateRepository;
    }

    @Override
    public GameState startNewGame() {

        LetterSelection letterSelection = this.letterSelectionProvider.getLetterSelection();
        LocalDictionaryComparison localDictionaryComparison = this.localDictionaryService.getLocalDictionaryComparison(letterSelection);

        // todo: implement again later Set<String> possibleWords = commonWordChecker.filterCommonWordFromLocalEntries(entriesFoundAndNotFoundInMitDictionary.get(1));

        GameState gameState = new GameState(letterSelection, localDictionaryComparison.matchingEntries());
        gameStateRepository.save(gameState);

        return gameState;
    }

    @Override
    public String verifyGuess(String guess, String gameId) {

        GameState gameState = getGameById(gameId);
        Set<String> foundWords = gameState.getFoundWords();
        Set<String> possibleWords = gameState.getPossibleWords();

        if (guess.length() < 4) {
            return "Guess not long enough";
        }

        String compulsoryLetter = gameState.getCompulsoryLetter();
        if (!guess.contains(compulsoryLetter)) {
            return "You must use letter '" + compulsoryLetter + "'";
        }

        if (foundWords.contains(guess)) {
            return "Already found";
        }

        if (possibleWords.contains(guess)) {
            updateGameState(gameState, guess);
            this.gameStateRepository.save(gameState);
            return "Found word";
        }
        return "Not in list";

    }

    public GameState getGameById(String id) {
        Optional<GameState> gameStateOptional = this.gameStateRepository.findById(id);
        return gameStateOptional.orElseThrow();
    }

    @Override
    public void updateGameState(GameState gameState, String guess) {
        gameState.updateScore(guess);
        gameState.addGuessToFoundWords(guess);
        gameState.determineRanking();
        //todo: encapsulate all of this in just one method
    }
}
