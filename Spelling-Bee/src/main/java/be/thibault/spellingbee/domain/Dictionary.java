package be.thibault.spellingbee.domain;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public record Dictionary(List<String> entries) {

    public boolean containsLetterCombo(String letterCombo){
        return this.entries.contains(letterCombo);
    }
}
