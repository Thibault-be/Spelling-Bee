package be.thibault.spellingbee.domain.localdictionary;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SmallerDictionaryReader implements LocalDictionaryReader {

    @Value("${spelling-bollie.local-dictionary-path.smaller}")
    public String smallerDictionary;

    @Override
    public LocalDictionary getLocalDictionary(){
        return importDictionary(this.smallerDictionary);
    }

    private LocalDictionary importDictionary(String dictionary) {

        Path path = Path.of(dictionary);

        if (Files.exists(path)) {
            try (Stream<String> lines = Files.lines(path)) {
                Set<String> entries = lines.collect(Collectors.toSet());
                return new LocalDictionary(entries);
            } catch (IOException e) {
                throw new RuntimeException("Could not find dictionary", e);
            }
        }

        return null;
    }
}
