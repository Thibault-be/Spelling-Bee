package be.thibault.spellingbee.domain.localdictionary;

import java.util.Set;

public record LocalDictionary(Set<String> entries) {

    public boolean contains(String entry){
        return this.entries.contains(entry);
    }
}
