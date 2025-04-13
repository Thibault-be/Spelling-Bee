package be.thibault.spellingbee.domain.letterselection;

public record LetterSelection(char[] vowelSelection, char[] consonantSelection, char compulsoryLetter) {

    public LetterSelection {
        if (vowelSelection.length + consonantSelection.length != 7) {
            throw new IllegalArgumentException("not enough letters were provided");
        }

        //todo: guard rails for compulsory letter

    }
}
