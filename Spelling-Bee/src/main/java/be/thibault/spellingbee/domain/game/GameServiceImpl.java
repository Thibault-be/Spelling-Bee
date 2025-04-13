package be.thibault.spellingbee.domain.game;

import be.thibault.spellingbee.domain.lettercombination.externaldictionary.CommonWordChecker;
import be.thibault.spellingbee.domain.lettercombination.model.LetterCombos;
import be.thibault.spellingbee.domain.lettercombination.service.LetterCombosProvider;
import be.thibault.spellingbee.domain.letterselection.LetterSelection;
import be.thibault.spellingbee.domain.letterselection.LetterSelectionProvider;
import be.thibault.spellingbee.domain.localdictionary.LocalDictionaryService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class GameServiceImpl implements GameService {

    private final LetterSelectionProvider letterSelectionProvider;
    private final LetterCombosProvider letterCombosProvider;
    private final LocalDictionaryService localDictionaryService;
    private final CommonWordChecker commonWordChecker;
    private final GameRepository gameRepository;

    public GameServiceImpl(LetterSelectionProvider letterSelectionProvider,
                           LetterCombosProvider letterCombosProvider,
                           LocalDictionaryService localDictionaryService,
                           CommonWordChecker commonWordChecker,
                           GameRepository gameRepository) {
        this.letterSelectionProvider = letterSelectionProvider;
        this.letterCombosProvider = letterCombosProvider;
        this.localDictionaryService = localDictionaryService;
        this.commonWordChecker = commonWordChecker;
        this.gameRepository = gameRepository;
    }

    @Override
    public GameState startNewGame() {

        LetterSelection letterSelection = this.letterSelectionProvider.getLetterSelection();
        LetterCombos letterCombos = this.letterCombosProvider.getLetterCombos(letterSelection);
        Set<String> localEntries = this.localDictionaryService.filterLocalEntriesFromCombos(letterCombos);
        Set<String> possibleWords = commonWordChecker.filterCommonWordFromLocalEntries(localEntries);

        GameState gameState = new GameState(letterSelection, possibleWords);
        gameRepository.saveGame(gameState.getGameId(), gameState);

        return gameState;

    }

    @Override
    public String verifyGuess(String guess, String gameId) {

        GameState gameState = this.gameRepository.findByGameId(gameId);
        Set<String> foundWords = gameState.getFoundWords();
        Set<String> possibleWords = gameState.getPossibleWords();

        if (foundWords.contains(guess)){
            return "Already found!";
        }

        if (possibleWords.contains(guess)){
            gameState.addScore(guess);
            gameState.addGuessToFoundWords(guess);
            return guess;
        }
        return "Not in list";
    }

    public GameState getGameById(String id){
        return gameRepository.findByGameId(id);
    }

    @Override
    public void updateGameState() {

    }
}
