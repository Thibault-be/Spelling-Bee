package be.thibault.spellingbee.domain.letterselection;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class LetterSelectionProviderImpl implements LetterSelectionProvider {

    private static final char[] CONSONANTS_NO_S = "bcdfghjklmnpqrtvwxyz".toCharArray();
    private static final char[] VOWELS = "aeiou".toCharArray();
    private static final int MAX_VOWELS = 4;
    private static final int MIN_VOWELS = 2;
    private static final int NUMBER_OF_LETTERS = 7;
    private static final Random RANDOM = new Random();


    @Override
    public LetterSelection getLetterSelection() {

        int numberOfVowels = RANDOM.nextInt(MIN_VOWELS, MAX_VOWELS);
        char[] vowelSelection = getLetters(numberOfVowels, VOWELS);

        int numberOfConsonants = NUMBER_OF_LETTERS - numberOfVowels;
        char[] consonantSelection = getLetters(numberOfConsonants, CONSONANTS_NO_S);
        char compulsoryLetter = determineCompulsoryLetter(vowelSelection, consonantSelection);

        return new LetterSelection(vowelSelection, consonantSelection, compulsoryLetter);
    }

    private char[] getLetters(int numberOfLetters, char[] letterArray) {

        List<Integer> randomIntegers = new ArrayList<>();

        while (randomIntegers.size() < numberOfLetters) {
            int randomInt = RANDOM.nextInt(0, letterArray.length);
            if (!randomIntegers.contains(randomInt)) {
                randomIntegers.add(randomInt);
            }
        }

        char[] letters = new char[numberOfLetters];
        int count = 0;
        for (Integer i : randomIntegers) {
            letters[count] = letterArray[i];
            count++;
        }
        return letters;
    }


    private char determineCompulsoryLetter(char[] vowelSelection, char[] consonantSelection) {

        char[] letters = ArrayUtils.addAll(vowelSelection, consonantSelection);
        int randomIndex = RANDOM.nextInt(0, NUMBER_OF_LETTERS);

        return letters[randomIndex];
    }

}
