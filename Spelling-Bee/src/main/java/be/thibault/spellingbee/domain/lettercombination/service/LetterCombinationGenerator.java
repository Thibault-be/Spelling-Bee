package be.thibault.spellingbee.domain.lettercombination.service;

import be.thibault.spellingbee.domain.letterselection.LetterSelection;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class LetterCombinationGenerator {


    public Set<String> generateCombinations(LetterSelection letterSelection){
        Set<String> result = new HashSet<>();

        char[] letters = ArrayUtils.addAll(letterSelection.vowelSelection(), letterSelection.consonantSelection());

        for(int lengthWord = 4; lengthWord < 9; lengthWord++) {
            generateCombinationsHelper(letters, "", lengthWord, result);
        }
        return result;
    }


    private void generateCombinationsHelper(char[] letters, String runningLetterCombo, int lettersNeeded, Set<String> result ){
        if (lettersNeeded == 0 ){
            result.add(runningLetterCombo);
            return;
        }

        for (char letter : letters) {
            generateCombinationsHelper(letters, runningLetterCombo + letter, lettersNeeded - 1, result);
        }
    }
}
