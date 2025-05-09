package be.thibault.spellingbee.domain.localdictionary;

import be.thibault.spellingbee.domain.lettercombination.model.LetterCombos;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LocalDictionaryEntryChecker {

    public Set<String> filterLocalEntriesFromCombos(LetterCombos letterCombos, LocalDictionary localDictionary) {
        Set<String> combosAsString = letterCombos.getCombosAsString();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        return combosAsString.stream()
                .filter(localDictionary::containsLetterCombo)
                .collect(Collectors.toSet());
    }
}
