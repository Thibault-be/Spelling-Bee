package be.thibault.spellingbee.domain;

import java.util.List;

public record LetterSelection(char[] letterSelection, char compulsoryLetter) {

    public LetterSelection {
        if (letterSelection.length != 7) {
            throw new IllegalArgumentException("not enough letters were provided");
        }

        //todo: guard rails for compulsory letter

    }
}
