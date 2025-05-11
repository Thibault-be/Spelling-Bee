package be.thibault.spellingbee.domain.letterselection;

import be.thibault.spellingbee.adapters.repository.GameStateRepository;
import be.thibault.spellingbee.domain.game.GameState;
import be.thibault.spellingbee.domain.localdictionary.LocalDictionary;
import be.thibault.spellingbee.domain.localdictionary.LocalDictionaryReader;
import be.thibault.spellingbee.domain.localdictionary.LocalDictionaryReaderImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class LetterSelectionProviderImpl implements LetterSelectionProvider {

    private static final String ALL_VOWELS = "aeiou";
    private static final int NUMBER_OF_LETTERS = 7;
    private static final Random RANDOM = new Random();
    private final LocalDictionaryReader localDictionaryReader;
    private final GameStateRepository gameStateRepository;

    public LetterSelectionProviderImpl(LocalDictionaryReader localDictionaryReader, GameStateRepository gameStateRepository) {
        this.localDictionaryReader = localDictionaryReader;
        this.gameStateRepository = gameStateRepository;
    }

    @Override
    public LetterSelection getLetterSelection() {
        return getNewLetterSelectionFromDictionary();
    }

    private LetterSelection getNewLetterSelectionFromDictionary() {
        LocalDictionary largerDictionary = this.localDictionaryReader.getLocalDictionary(LocalDictionaryReaderImpl.LARGER_DICTIONARY);
        Set<String> entries = largerDictionary.entries();

        Optional<LetterSelection> letterSelection = entries.stream()
                .filter(this::hasSevenDifferentLetters)
                .map(this::getLetterSelectionFromWord)
                .filter(this::isAvailable)
                .findFirst();

        return letterSelection.orElseThrow();
    }

    private LetterSelection getLetterSelectionFromWord(String sevenLetterWord) {
        char compulsoryLetter = determineCompulsoryLetter(sevenLetterWord);

        StringBuilder vowelBuilder = new StringBuilder();
        StringBuilder consonantBuilder = new StringBuilder();

        sevenLetterWord.chars()
                .distinct()
                .forEach(c -> {
                    if (ALL_VOWELS.contains(Character.toString(c))) {
                        vowelBuilder.append(Character.toString(c));
                    } else {
                        consonantBuilder.append(Character.toString(c));
                    }
                });


        char[] vowelSelection = vowelBuilder.toString().toCharArray();
        char[] consonantSelection = consonantBuilder.toString().toCharArray();

        return new LetterSelection(vowelSelection, consonantSelection, compulsoryLetter);
    }

    private boolean hasSevenDifferentLetters(String entry) {
        return entry.chars().distinct().count() == 7;
    }

    private char determineCompulsoryLetter(String entry) {

        int randomInt = RANDOM.nextInt(7);
        return entry.toCharArray()[randomInt];

    }

    //todo: refactor. Now we are loading all gamestates. This method probably shouldn't be here.
    private boolean isAvailable(LetterSelection letterSelection) {
        List<GameState> allGameStates = this.gameStateRepository.findAll();
        Set<LetterSelection> usedSelections = allGameStates.stream()
                .map(GameState::getLetterSelection)
                .collect(Collectors.toSet());

        if (usedSelections.isEmpty()) return true;

        for (LetterSelection usedSelection : usedSelections) {
            if (hasSameLetters(usedSelection, letterSelection)) {
                return false;
            }
        }
        return true;
    }

    private boolean hasSameLetters(LetterSelection used, LetterSelection newSelection) {

        List<String> usedLetters = used.getAllOptionalLettersAsList();
        usedLetters.add(Character.toString(used.getCompulsoryLetter()));

        List<String> newLetters = newSelection.getAllOptionalLettersAsList();
        newLetters.add(Character.toString(newSelection.getCompulsoryLetter()));

        Set<String> mismatch = newLetters.stream()
                .filter(x -> !usedLetters.contains(x))
                .collect(Collectors.toSet());

        return mismatch.isEmpty();
    }
}
