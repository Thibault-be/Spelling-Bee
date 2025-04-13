package be.thibault.spellingbee.domain.localdictionary;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LocalDictionaryEntryChecker {

    //todo: should this also be an interface?

    private final LocalDictionary localDictionary;

    public LocalDictionaryEntryChecker(LocalDictionary localDictionary) {
        this.localDictionary = localDictionary;
    }

    public String isLocalDictionaryEntry(String letterCombo){
        List<String> entries = localDictionary.entries();
        boolean contains = entries.contains(letterCombo);

        return entries.contains(letterCombo) ? letterCombo : null;
    }

}
