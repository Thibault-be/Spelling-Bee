package be.thibault.spellingbee.domain.localdictionary;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class LocalDictionaryReaderImpl implements LocalDictionaryReader {

    public static final String LARGER_DICTIONARY = "D:/Projects/Spelling Bee/Spelling-Bee/src/main/resources/english.txt";
    public static final String SMALLLER_DICTIONARY = "D:/Projects/Spelling Bee/Spelling-Bee/src/main/resources/usa-english.txt";


    @Override
    public LocalDictionary getLocalDictionary(String dictionary){
        return importDictionary(dictionary);
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
