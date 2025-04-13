package be.thibault.spellingbee.domain.lettercombination.externaldictionary;

import be.thibault.spellingbee.config.DictionaryApiClient;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CommonWordChecker {

    private final DictionaryApiClient dictionaryApiClient;

    public CommonWordChecker(DictionaryApiClient dictionaryApiClient) {
        this.dictionaryApiClient = dictionaryApiClient;
    }

    private boolean isCommonWord(String localEntry){
        return dictionaryApiClient.entryFoundInExternalDictionary(localEntry);
    }

    public Set<String> filterCommonWordFromLocalEntries(Set<String> localEntries){
        return localEntries.stream()
                .filter(this::isCommonWord)
                .collect(Collectors.toSet());
    }

}
