package be.thibault.spellingbee.domain.game;

import be.thibault.spellingbee.adapters.repository.GameStateRepository;
import be.thibault.spellingbee.domain.lettercombination.externaldictionary.CommonWordChecker;
import be.thibault.spellingbee.domain.lettercombination.model.LetterCombos;
import be.thibault.spellingbee.domain.lettercombination.service.LetterCombosProvider;
import be.thibault.spellingbee.domain.letterselection.LetterSelection;
import be.thibault.spellingbee.domain.letterselection.LetterSelectionProvider;
import be.thibault.spellingbee.domain.localdictionary.LocalDictionaryService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class GameServiceImpl implements GameService {

    private final LetterSelectionProvider letterSelectionProvider;
    private final LetterCombosProvider letterCombosProvider;
    private final LocalDictionaryService localDictionaryService;
    private final CommonWordChecker commonWordChecker;
    private final GameStateRepository gameStateRepository;

    public GameServiceImpl(LetterSelectionProvider letterSelectionProvider,
                           LetterCombosProvider letterCombosProvider,
                           LocalDictionaryService localDictionaryService,
                           CommonWordChecker commonWordChecker,
                           GameStateRepository gameStateRepository) {
        this.letterSelectionProvider = letterSelectionProvider;
        this.letterCombosProvider = letterCombosProvider;
        this.localDictionaryService = localDictionaryService;
        this.commonWordChecker = commonWordChecker;
        this.gameStateRepository = gameStateRepository;
    }

    @Override
    public GameState startNewGame() {

        LetterSelection letterSelection = this.letterSelectionProvider.getLetterSelection();
        LetterCombos letterCombos = this.letterCombosProvider.getLetterCombos(letterSelection);
        Set<String> localEntries = this.localDictionaryService.filterLocalEntriesFromCombos(letterCombos);
        Set<String> possibleWords = commonWordChecker.filterCommonWordFromLocalEntries(localEntries);

        GameState gameState = new GameState(letterSelection, possibleWords);
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
