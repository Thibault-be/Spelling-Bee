package be.thibault.spellingbee.domain.letterselection;

import be.thibault.spellingbee.adapters.repository.GameStateRepository;
import be.thibault.spellingbee.domain.localdictionary.LargerDictionaryReader;
import be.thibault.spellingbee.domain.localdictionary.LocalDictionary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class LetterSelectionProviderImpl implements LetterSelectionProvider {

    private static final String ALL_VOWELS = "aeiou";
    private static final Random RANDOM = new Random();
    private final LargerDictionaryReader largerDictionaryReader;
    private final GameStateRepository gameStateRepository;

    public LetterSelectionProviderImpl(LargerDictionaryReader largerDictionaryReader, GameStateRepository gameStateRepository) {
        this.largerDictionaryReader = largerDictionaryReader;
        this.gameStateRepository = gameStateRepository;
    }

    @Override
    public LetterSelection getLetterSelection() {
        return getNewLetterSelectionFromDictionary();
    }

    private LetterSelection getNewLetterSelectionFromDictionary() {
        LocalDictionary largerDictionary = this.largerDictionaryReader.getLocalDictionary();
        Set<String> entries = largerDictionary.entries();

        Optional<LetterSelection> letterSelection = entries.stream()
                .filter(this::doesNotContainLetterS)
                .filter(this::hasSevenDifferentLetters)
                .map(this::getLetterSelectionFromWord)
                .filter(this::isAvailable)
                .findFirst();

        return letterSelection.orElseThrow();
    }

    private boolean doesNotContainLetterS(String entry) {
        return !entry.contains("s");
    }

    private boolean hasSevenDifferentLetters(String entry) {
        return entry.chars().distinct().count() == 7;
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

    private char determineCompulsoryLetter(String entry) {

        int randomInt = RANDOM.nextInt(7);
        return entry.toCharArray()[randomInt];

    }

    private boolean isAvailable(LetterSelection letterSelection) {

        List<LetterSelection> usedSelections = this.gameStateRepository.findUsedLetterSelections();

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
