package be.thibault.spellingbee.domain.externaldictionary;

import be.thibault.spellingbee.configuration.DictionaryApiClient;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

@Component
public class CommonWordChecker {

    private final DictionaryApiClient dictionaryApiClient;

    public CommonWordChecker(DictionaryApiClient dictionaryApiClient) {
        this.dictionaryApiClient = dictionaryApiClient;
    }

    private boolean isCommonWord(String localEntry) {
        return dictionaryApiClient.entryFoundInExternalDictionary(localEntry);
    }

    public Set<String> filterCommonWordFromLocalEntries(Set<String> localEntries) {

        Set<String> results = ConcurrentHashMap.newKeySet();
        ExecutorService executor = Executors.newFixedThreadPool(10);
        try {
            List<Future<?>> futures = new ArrayList<>();
            for (String word : localEntries) {
                futures.add(executor.submit(() -> {
                    if (isCommonWord(word)) {
                        results.add(word);
                    }
                }));
            }
            for (Future<?> future : futures) {
                future.get(); // Wait for each task to complete
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
        return results;
    }
}

