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

    public LetterSelection(){}

    public LetterSelection(char[] vowelSelection,
                           char[] consonantSelection,
                           char compulsoryLetter) {
        if (vowelSelection.length + consonantSelection.length != 7) {
            throw new IllegalArgumentException("not enough letters were provided");
        }
        this.vowelSelection = vowelSelection;
        this.consonantSelection = consonantSelection;
        this.compulsoryLetter = compulsoryLetter;
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

    @Override
    public String toString() {
        List<String> allLetters = getAllLettersAsList();
        StringBuilder stringBuilder = new StringBuilder();
        String compulsory = String.valueOf(this.compulsoryLetter);

        stringBuilder.append(compulsory);

        allLetters.forEach(letter -> {
            if (!letter.equals(compulsory)) {
                stringBuilder.append(letter);
            }
        });

        return stringBuilder.toString();
    }

    public static LetterSelection fromString(String dbString) {

        char[] charArray = dbString.toCharArray();
        char compulsoryLetter = charArray[0];
        String compulsoryLetterString = String.valueOf(compulsoryLetter);

        List<String> vowels = List.of("a", "e", "i", "o", "u");

        List<String> vowelSelection = new ArrayList<>();
        List<String> consonantSelection = new ArrayList<>();

        if (vowels.contains(compulsoryLetterString)){
            vowelSelection.add(compulsoryLetterString);
        } else {
            consonantSelection.add(compulsoryLetterString);
        }

        for (int i = 1; i < charArray.length; i++) {
            String letter = String.valueOf(charArray[i]);
            if (vowels.contains(letter)) {
                vowelSelection.add(letter);
            } else {
                consonantSelection.add(letter);
            }
        }

        char[] vowelArray = new char[vowelSelection.size()];
        char[] consonantArray = new char[consonantSelection.size()];

        for (int i = 0; i < vowelSelection.size(); i++) {
            vowelArray[i] = vowelSelection.get(i).charAt(0);
        }

        for (int i = 0; i < consonantSelection.size(); i++) {
            consonantArray[i] = consonantSelection.get(i).charAt(0);
        }

        return new LetterSelection(vowelArray, consonantArray, compulsoryLetter);
    }
}
