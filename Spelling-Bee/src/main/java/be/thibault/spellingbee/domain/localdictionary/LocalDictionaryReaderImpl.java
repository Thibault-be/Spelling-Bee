package be.thibault.spellingbee.domain.localdictionary;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LocalDictionaryReaderImpl implements LocalDictionaryReader {

    //todo: to be externalised
    private static final String DICTIONARY_LOCATION = "D:/Projects/Spelling Bee/Spelling-Bee/src/main/resources/words_alpha.txt";
    private final LocalDictionary localDictionary;

    public LocalDictionaryReaderImpl(){
        this.localDictionary= importDictionary();
    }

    @Override
    public LocalDictionary getLocalDictionary(){
        return this.localDictionary;
    }

    private LocalDictionary importDictionary() {

        Path path = Path.of(DICTIONARY_LOCATION);

        //todo: convert to try with resources
        if (Files.exists(path)) {
            try {
                Set<String> entries = Files.lines(path).collect(Collectors.toSet());
                return new LocalDictionary(entries);
            } catch (IOException e) {
                throw new RuntimeException("Could not find dictionary");
            }
        }
        return null;
    }
}
