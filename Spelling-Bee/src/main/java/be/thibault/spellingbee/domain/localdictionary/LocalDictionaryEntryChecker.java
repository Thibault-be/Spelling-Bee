package be.thibault.spellingbee.domain.localdictionary;

import be.thibault.spellingbee.domain.lettercombination.model.LetterCombos;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LocalDictionaryEntryChecker {

    //todo: should this also be an interface?

    public Set<String> filterLocalEntriesFromCombos(LetterCombos letterCombos, LocalDictionary localDictionary) {
        Set<String> combosAsString = letterCombos.getCombosAsString();
        return combosAsString.stream()
                .filter(localDictionary::containsLetterCombo)
                .collect(Collectors.toSet());
    }
}
