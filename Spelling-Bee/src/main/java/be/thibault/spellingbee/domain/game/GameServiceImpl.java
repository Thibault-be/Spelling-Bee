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

    public GameServiceImpl(LetterSelectionProvider letterSelectionProvider,
                           LetterCombosProvider letterCombosProvider,
                           LocalDictionaryService localDictionaryService,
                           CommonWordChecker commonWordChecker) {
        this.letterSelectionProvider = letterSelectionProvider;
        this.letterCombosProvider = letterCombosProvider;
        this.localDictionaryService = localDictionaryService;
        this.commonWordChecker = commonWordChecker;
    }

    @Override
    public GameState startNewGame() {

        LetterSelection letterSelection = this.letterSelectionProvider.getLetterSelection();
        LetterCombos letterCombos = this.letterCombosProvider.getLetterCombos(letterSelection);
        Set<String> localEntries = this.localDictionaryService.filterLocalEntriesFromCombos(letterCombos);
        Set<String> commonWords = commonWordChecker.filterCommonWordFromLocalEntries(localEntries);

        return new GameState(letterSelection, commonWords);
    }

    @Override
    public void updateGameState() {

    }
}
