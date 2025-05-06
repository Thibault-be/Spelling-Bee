package be.thibault.spellingbee.domain.letterselection;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public record LetterSelection(char[] vowelSelection,
                              char[] consonantSelection,
                              char compulsoryLetter) {

    public LetterSelection {
        if (vowelSelection.length + consonantSelection.length != 7) {
            throw new IllegalArgumentException("not enough letters were provided");
        }
        //todo: guard rails for compulsory letter
    }

    public List<List<String>> getFrontendLetterLayout(){

        List<List<String>> letterLayout = new ArrayList<>();

        String compulsoryLetter = String.valueOf(this.compulsoryLetter);

        List<String> vowels = convertToStringList(vowelSelection);
        List<String> consonants = convertToStringList(consonantSelection);
        List<String> allLetters = new ArrayList<>();

        vowels.stream()
                .filter(vowel -> !vowel.equals(compulsoryLetter))
                .forEach(allLetters::add);
        consonants.stream()
                .filter(consonant -> !consonant.equals(compulsoryLetter))
                .forEach(allLetters::add);


        List<String> topRow = List.of(allLetters.get(0), allLetters.get(1));
        List<String> middleRow = List.of(allLetters.get(2), compulsoryLetter, allLetters.get(3));
        List<String> bottomRow = List.of(allLetters.get(4), allLetters.get(5));

        return List.of(topRow, middleRow, bottomRow);

    }

    private List<String> convertToStringList(char[] array){
        List<String> strings = new ArrayList<>();
        for (char c : array){
            strings.add(String.valueOf(c));
        }
        return strings;
    }


}
