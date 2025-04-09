package be.thibault.spellingbee.domain;

import java.util.List;

public record LetterSelection(List<String> letterSelection, String compulsoryLetter) {

    public LetterSelection{
        if (letterSelection.size() != 7 || !letterSelection.contains(compulsoryLetter)){
            throw new IllegalArgumentException("not enough letters were provided");
        }
    }
}
