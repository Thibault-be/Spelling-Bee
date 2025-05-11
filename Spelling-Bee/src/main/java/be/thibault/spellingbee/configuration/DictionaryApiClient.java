package be.thibault.spellingbee.configuration;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class DictionaryApiClient {

    private static final Logger log = LoggerFactory.getLogger(DictionaryApiClient.class);
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

            if (e instanceof WebClientResponseException wcre){
                HttpStatusCode statusCode = wcre.getStatusCode();
                if (statusCode != HttpStatus.NOT_FOUND){
                    log.info("Could not find {}", localEntry);
                }
            }
            return false;
        }
    }
}
