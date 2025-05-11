package be.thibault.spellingbee.domain.enums;

public enum GuessResult {

    NOT_LONG_ENOUGH ("Guess not long enough"),
    COMPULSORY_LETTER_MISSING ("Required letter missing"),
    ALREADY_FOUND ("Word already found"),
    CORRECT_GUESS ("New word found"),
    NOT_IN_LIST ("Word not in liist");

    private final String description;

    GuessResult(String description) {
        this.description = description;
    }

    public String getDescription(){
        return this.description;
    }

}
