package be.thibault.spellingbee.domain.commonword;

import be.thibault.spellingbee.config.DictionaryApiClient;
import org.springframework.stereotype.Component;

@Component
public class CommonWordChecker {

    private final DictionaryApiClient dictionaryApiClient;

    public CommonWordChecker(DictionaryApiClient dictionaryApiClient) {
        this.dictionaryApiClient = dictionaryApiClient;
    }

    public boolean isCommonWord(String localEntry){
        return dictionaryApiClient.entryFoundInExternalDictionary(localEntry);
    }
}
