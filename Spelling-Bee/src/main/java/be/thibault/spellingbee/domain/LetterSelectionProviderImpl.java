package be.thibault.spellingbee.domain;

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
    private static final int MIN_VOWELS = 1;
    private static final int NUMBER_OF_LETTERS = 7;
    private static final Random RANDOM = new Random();
    private final List<String> sevenLetters = new ArrayList<>();


    @Override
    public LetterSelection getLetterSelection() {

        List<String> sevenUniqueLetters = getSevenUniqueLetters();
        int compulsoryLetterIndex = determineCompulsoryLetter();

        return new LetterSelection(sevenUniqueLetters, sevenUniqueLetters.get(compulsoryLetterIndex));
    }


    private List<String> getSevenUniqueLetters() {

        int numberOfVowels = RANDOM.nextInt(MIN_VOWELS, MAX_VOWELS);
        List<String> vowels = getLetters(numberOfVowels, VOWELS);

        int numberOfConsonants = NUMBER_OF_LETTERS - numberOfVowels;
        List<String> consonants = getLetters(numberOfConsonants, CONSONANTS_NO_S);

        return Stream.concat(vowels.stream(), consonants.stream()).toList();
    }

    private List<String> getLetters(int numberOfLetters, char[] letterArray) {

        List<Integer> randomIntegers = new ArrayList<>();

        while (randomIntegers.size() < numberOfLetters) {
            int randomInt = RANDOM.nextInt(0, letterArray.length);
            if (randomIntegers.contains(randomInt) == false) {
                randomIntegers.add(randomInt);
            }
        }

        List<String> letters = new ArrayList<>();
        for (Integer i : randomIntegers){
            letters.add(String.valueOf(letterArray[i]));
        }
        return letters;
    }


    private int determineCompulsoryLetter() {
        return RANDOM.nextInt(0, NUMBER_OF_LETTERS);
    }

}
