package be.thibault.spellingbee;

import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class DictionaryCaller {


    public String callDictionary(){

        String url = "https://api.dictionaryapi.dev/api/v2/entries/en/hello";

        WebClient webClient = WebClient.builder()
                .baseUrl(url)
                .build();

        Mono<String> stringMono = webClient.get().uri(url).retrieve().bodyToMono(String.class);

        return stringMono.block();

    }
}
