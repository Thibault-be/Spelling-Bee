package be.thibault.spellingbee.domain.localdictionary;

import be.thibault.spellingbee.domain.lettercombination.model.LetterCombos;
import be.thibault.spellingbee.domain.letterselection.LetterSelection;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LocalDictionaryService {

    private final LocalDictionaryReader localDictionaryReader;
    private final LocalDictionaryEntryChecker localDictionaryEntryChecker;

    public LocalDictionaryService(LocalDictionaryReader localDictionaryReader, LocalDictionaryEntryChecker localDictionaryEntryChecker) {
        this.localDictionaryReader = localDictionaryReader;
        this.localDictionaryEntryChecker = localDictionaryEntryChecker;
    }

    public LocalDictionary getLocalDictionary() {
        return this.localDictionaryReader.getLocalDictionary();
    }

    public Set<String> filterLocalEntriesFromCombos(LetterCombos letterCombos) {
        LocalDictionary localDictionary = getLocalDictionary();
        return localDictionaryEntryChecker.filterLocalEntriesFromCombos(letterCombos, localDictionary);

    }

    public Set<String> localEntriesFromLetterSelection(LetterSelection letterSelection) {

        LocalDictionary localDictionary = getLocalDictionary();

        List<String> allLetters = letterSelection.getAllOptionalLettersAsList();
        String compulsoryLetter = String.valueOf(letterSelection.getCompulsoryLetter());
        allLetters.add(compulsoryLetter);

        return localDictionary.entries().parallelStream()
                .filter(entry -> entry.contains(compulsoryLetter))
                .filter(entry -> entry.length() >= 4)
                .filter(entry -> entry.chars().allMatch(c -> allLetters.contains(Character.toString(c))))
                .collect(Collectors.toSet());


    }


}
