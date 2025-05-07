package be.thibault.spellingbee.domain.lettercombination.service;

import be.thibault.spellingbee.domain.lettercombination.model.LetterCombos;
import be.thibault.spellingbee.domain.letterselection.LetterSelection;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LetterCombosProviderImpl implements LetterCombosProvider {

    private final LetterCombinationGenerator letterCombinationGenerator;

    public LetterCombosProviderImpl(LetterCombinationGenerator letterCombinationGenerator) {
        this.letterCombinationGenerator = letterCombinationGenerator;
    }

    @Override
    public LetterCombos getLetterCombos(LetterSelection letterSelection) {

        Set<String> letterPermutations = this.letterCombinationGenerator.generateCombinations(letterSelection);

        Map<Integer, List<String>> permutationsPerLength = letterPermutations.stream()
                .collect(Collectors.groupingBy(String::length));

        LetterCombos letterCombos = new LetterCombos();
        char compulsoryLetter = letterSelection.compulsoryLetter();
        for (int wordLength = 4; wordLength < 9; wordLength++) {
            List<String> permutations = permutationsPerLength.get(wordLength);
            permutations.forEach(letterCombo -> letterCombos.addMultiLetterCombo(letterCombo, compulsoryLetter));
        }
        return letterCombos;
    }


}
