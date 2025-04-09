package be.thibault.spellingbee.domain;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class LetterCombinationGenerator {


    public Set<String> generateCombinations(LetterSelection letterSelection, int lengthWord){
        Set<String> result = new HashSet<>();

        char[] letters = ArrayUtils.addAll(letterSelection.vowelSelection(), letterSelection.consonantSelection());

        generateCombinationsHelper(letters, "", lengthWord, result);
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
