package be.thibault.spellingbee.config;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class DictionaryApiClient {

    private final String basePath = "https://api.dictionaryapi.dev/api/v2/entries/en/";

    private final WebClient webClient;

    public DictionaryApiClient() {
        this.webClient = WebClient.builder()
                .baseUrl(basePath)
                .build();
    }

    public boolean entryFoundInExternalDictionary(String localEntry) {
        try {
            Mono<String> responseMono = this.webClient.get()
                    .uri(basePath + localEntry)
                    .retrieve()
                    .bodyToMono(String.class);
            String entryFound = responseMono.block();
            return entryFound != null;
        } catch (Exception e) {
            return false;
        }
    }
}
