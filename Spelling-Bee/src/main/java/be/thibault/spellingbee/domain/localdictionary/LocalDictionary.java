package be.thibault.spellingbee.domain.localdictionary;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public record LocalDictionary(List<String> entries) {

    public boolean containsLetterCombo(String letterCombo){
        return this.entries.contains(letterCombo);
    }
}
