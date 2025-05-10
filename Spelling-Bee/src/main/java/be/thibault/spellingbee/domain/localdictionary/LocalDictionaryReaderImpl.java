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
    public static final String LARGER_DICTIONARY = "D:/Projects/Spelling Bee/Spelling-Bee/src/main/resources/english.txt";
    public static final String SMALLLER_DICTIONARY = "D:/Projects/Spelling Bee/Spelling-Bee/src/main/resources/usa-english.txt";

    public LocalDictionaryReaderImpl(){
    }

    @Override
    public LocalDictionary getLocalDictionary(String dictionary){
        return importDictionary(dictionary);
    }

    private LocalDictionary importDictionary(String dictionary) {

        Path path = Path.of(dictionary);

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
