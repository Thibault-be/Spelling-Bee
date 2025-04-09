package be.thibault.spellingbee.domain;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Component
public class LetterSelectionProviderImpl implements LetterSelectionProvider {

    private static final char[] CONSONANTS_NO_S = "bcdfghjklmnpqrtvwxyz".toCharArray();
    private static final char[] VOWELS = "aeiou".toCharArray();
    private static final int MAX_VOWELS = 3;
    private static final int MIN_VOWELS = 2;
    private static final int NUMBER_OF_LETTERS = 7;
    private static final Random RANDOM = new Random();


    @Override
    public LetterSelection getLetterSelection() {

        char[] sevenUniqueLetters = getSevenUniqueLetters();
        int compulsoryLetterIndex = determineCompulsoryLetter();

        return new LetterSelection(sevenUniqueLetters, sevenUniqueLetters[compulsoryLetterIndex]);
    }


    private char[] getSevenUniqueLetters() {

        int numberOfVowels = RANDOM.nextInt(MIN_VOWELS, MAX_VOWELS);
        char[] vowels = getLetters(numberOfVowels, VOWELS);

        int numberOfConsonants = NUMBER_OF_LETTERS - numberOfVowels;
        char[] consonants = getLetters(numberOfConsonants, CONSONANTS_NO_S);

        return ArrayUtils.addAll(vowels, consonants);
    }

    private char[] getLetters(int numberOfLetters, char[] letterArray) {

        List<Integer> randomIntegers = new ArrayList<>();

        while (randomIntegers.size() < numberOfLetters) {
            int randomInt = RANDOM.nextInt(0, letterArray.length);
            if (randomIntegers.contains(randomInt) == false) {
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


    private int determineCompulsoryLetter() {
        return RANDOM.nextInt(0, NUMBER_OF_LETTERS);
    }

}
