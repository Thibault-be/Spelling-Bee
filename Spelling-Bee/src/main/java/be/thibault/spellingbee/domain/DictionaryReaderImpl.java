package be.thibault.spellingbee.domain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class DictionaryReaderImpl implements DictionaryReader {


    @Override
    public Dictionary importDictionary() {

        //todo: to be externalized
        Path path = Path.of("D:\\Projects\\Spelling Bee\\Spelling-Bee\\src\\main\\resources\\words_alpha.txt");

        if (Files.exists(path)) {
            try {
                List<String> entries = Files.lines(path).toList();
                return new Dictionary(entries);
            } catch (IOException e) {
                throw new RuntimeException("Could not find dictionary");
            }
        }
        return null;
    }
}
