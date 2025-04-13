package be.thibault.spellingbee.domain.localdictionary;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class LocalDictionaryReaderImpl implements LocalDictionaryReader {


    @Override
    public LocalDictionary importDictionary() {

        //todo: to be externalized
        Path path = Path.of("D:\\Projects\\Spelling Bee\\Spelling-Bee\\src\\main\\resources\\words_alpha.txt");

        if (Files.exists(path)) {
            try {
                List<String> entries = Files.lines(path).toList();
                return new LocalDictionary(entries);
            } catch (IOException e) {
                throw new RuntimeException("Could not find dictionary");
            }
        }
        return null;
    }
}
