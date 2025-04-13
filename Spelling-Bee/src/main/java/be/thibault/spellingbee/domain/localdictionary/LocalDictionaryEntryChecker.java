package be.thibault.spellingbee.domain.localdictionary;

import be.thibault.spellingbee.domain.lettercombination.model.LetterCombos;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class LocalDictionaryEntryChecker {

    //todo: should this also be an interface?

    private final LocalDictionary localDictionary;

    public LocalDictionaryEntryChecker(LocalDictionary localDictionary) {
        this.localDictionary = localDictionary;
    }

    public String isLocalDictionaryEntry(String letterCombo) {
        List<String> entries = localDictionary.entries();
        boolean contains = entries.contains(letterCombo);

        return entries.contains(letterCombo) ? letterCombo : null;
    }

    public Set<String> filterLocalEntriesFromCombos(LetterCombos letterCombos) {
        Set<String> combosAsString = letterCombos.getCombosAsString();
        return combosAsString.stream()
                .filter(localDictionary::containsLetterCombo)
                .collect(Collectors.toSet());
    }
}
