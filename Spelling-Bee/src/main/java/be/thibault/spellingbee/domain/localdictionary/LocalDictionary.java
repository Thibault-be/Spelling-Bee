package be.thibault.spellingbee.domain.localdictionary;

import java.util.Set;

public record LocalDictionary(Set<String> entries) {

    public boolean containsLetterCombo(String letterCombo){

        return this.entries.contains(letterCombo);
    }
}
