package be.thibault.spellingbee.domain.localdictionary;

import be.thibault.spellingbee.domain.lettercombination.model.LetterCombos;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class LocalDictionaryService {

    private final LocalDictionaryReader localDictionaryReader;
    private final LocalDictionaryEntryChecker localDictionaryEntryChecker;

    public LocalDictionaryService(LocalDictionaryReader localDictionaryReader, LocalDictionaryEntryChecker localDictionaryEntryChecker) {
        this.localDictionaryReader = localDictionaryReader;
        this.localDictionaryEntryChecker = localDictionaryEntryChecker;
    }

    public LocalDictionary getLocalDictionary(){
        return this.localDictionaryReader.getLocalDictionary();
    }

    public Set<String> filterLocalEntriesFromCombos(LetterCombos letterCombos) {
        LocalDictionary localDictionary = getLocalDictionary();
        return localDictionaryEntryChecker.filterLocalEntriesFromCombos(letterCombos, localDictionary);

    }




    }
