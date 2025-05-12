package be.thibault.spellingbee.domain.localdictionary;

import be.thibault.spellingbee.domain.letterselection.LetterSelection;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LocalDictionaryService {

    private final LargerDictionaryReader largerDictionaryReader;
    private final SmallerDictionaryReader smallerDictionaryReader;

    public LocalDictionaryService(LargerDictionaryReader largerDictionaryReader, SmallerDictionaryReader smallerDictionaryReader) {
        this.largerDictionaryReader = largerDictionaryReader;
        this.smallerDictionaryReader = smallerDictionaryReader;
    }

    public Set<String> localEntriesFromLetterSelection(LetterSelection letterSelection) {

        LocalDictionary largerDictionary = this.largerDictionaryReader.getLocalDictionary();

        List<String> allLetters = letterSelection.getAllOptionalLettersAsList();
        String compulsoryLetter = String.valueOf(letterSelection.getCompulsoryLetter());
        allLetters.add(compulsoryLetter);

        return largerDictionary.entries().stream()
                .filter(entry -> entry.contains(compulsoryLetter))
                .filter(entry -> entry.length() >= 4)
                .filter(entry -> entry.chars().allMatch(c -> allLetters.contains(Character.toString(c))))
                .collect(Collectors.toSet());
    }

    public LocalDictionaryComparison getLocalDictionaryComparison(LetterSelection letterSelection) {

        Set<String> largerEntries = localEntriesFromLetterSelection(letterSelection);
        return compareToSmallerDictionary(largerEntries);

    }

    private LocalDictionaryComparison compareToSmallerDictionary(Set<String> largerEntries) {

        LocalDictionary mitDictionary = this.smallerDictionaryReader.getLocalDictionary();

        Set<String> partOfMit = new HashSet<>();
        Set<String> notFoundInMit = new HashSet<>();

        largerEntries
                .forEach((entry) -> {
                    if (mitDictionary.contains(entry)) {
                        partOfMit.add(entry);
                    } else {
                        notFoundInMit.add(entry);
                    }
                });
        return new LocalDictionaryComparison(partOfMit, notFoundInMit);
    }
}
