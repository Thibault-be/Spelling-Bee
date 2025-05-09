package be.thibault.spellingbee.domain.lettercombination.externaldictionary;

import be.thibault.spellingbee.configuration.DictionaryApiClient;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

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
