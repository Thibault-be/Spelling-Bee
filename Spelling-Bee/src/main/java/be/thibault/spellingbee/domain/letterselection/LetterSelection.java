package be.thibault.spellingbee.domain.letterselection;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//todo: move businesslogic-y methods outside of this record

@Embeddable
public class LetterSelection implements Serializable {

    private char[] vowelSelection;
    private char[] consonantSelection;
    private char compulsoryLetter;

    public LetterSelection(char[] vowelSelection,
                           char[] consonantSelection,
                           char compulsoryLetter) {
        if (vowelSelection.length + consonantSelection.length != 7) {
            throw new IllegalArgumentException("not enough letters were provided");
        }
        //todo: guard rails for compulsory letter
    }

    public char[] getVowelSelection() {
        return vowelSelection;
    }

    public char[] getConsonantSelection() {
        return consonantSelection;
    }

    public char getCompulsoryLetter() {
        return compulsoryLetter;
    }

    public List<List<String>> getFrontendLetterLayout() {

        List<String> allLetters = getAllLettersAsList();
        String compulsoryLetter = String.valueOf(this.compulsoryLetter);

        List<String> topRow = List.of(allLetters.get(0), allLetters.get(1));
        List<String> middleRow = List.of(allLetters.get(2), compulsoryLetter, allLetters.get(3));
        List<String> bottomRow = List.of(allLetters.get(4), allLetters.get(5));

        return List.of(topRow, middleRow, bottomRow);
    }

    private List<String> convertToStringList(char[] array) {
        List<String> strings = new ArrayList<>();
        for (char c : array) {
            strings.add(String.valueOf(c));
        }
        return strings;
    }

    private List<String> getAllLettersAsList() {

        List<String> allLetters = new ArrayList<>();
        String compulsoryLetter = String.valueOf(this.compulsoryLetter);
        List<String> vowels = convertToStringList(vowelSelection);
        List<String> consonants = convertToStringList(consonantSelection);

        vowels.stream()
                .filter(vowel -> !vowel.equals(compulsoryLetter))
                .forEach(allLetters::add);
        consonants.stream()
                .filter(consonant -> !consonant.equals(compulsoryLetter))
                .forEach(allLetters::add);

        return allLetters;
    }

    public boolean containsAllSevenLetters(String guess) {

        if (guess.length() < 7) {
            return false;
        }

        List<String> guessAsList = Arrays.asList(guess.split(""));
        List<String> allLetters = getAllLettersAsList();

        return guessAsList.containsAll(allLetters);
    }
}
