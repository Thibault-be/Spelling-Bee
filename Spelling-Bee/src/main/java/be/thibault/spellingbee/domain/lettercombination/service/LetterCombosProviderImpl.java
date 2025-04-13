package be.thibault.spellingbee.domain.lettercombination.service;

import be.thibault.spellingbee.domain.lettercombination.model.MultiLetterCombo;
import be.thibault.spellingbee.domain.letterselection.LetterSelection;
import be.thibault.spellingbee.domain.lettercombination.model.FiveLetterCombo;
import be.thibault.spellingbee.domain.lettercombination.model.FourLetterCombo;
import be.thibault.spellingbee.domain.lettercombination.model.LetterCombos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

//@Service
public class LetterCombosProviderImpl implements LetterCombosProvider {

    private final LetterCombinationGenerator letterCombinationGenerator;

    public LetterCombosProviderImpl(LetterCombinationGenerator letterCombinationGenerator) {
        this.letterCombinationGenerator = letterCombinationGenerator;
    }

    @Override
    public LetterCombos getLetterCombos(LetterSelection letterSelection) {

        Set<String> letterPermutations = this.letterCombinationGenerator.generateCombinations(letterSelection);

        Map<Integer, List<String>> permutationsPerLength = letterPermutations.stream().collect(Collectors.groupingBy(String::length));

        LetterCombos letterCombos = new LetterCombos();
        for (int wordLength = 4; wordLength < 9; wordLength++){
            List<String> permutations = permutationsPerLength.get(wordLength);
            permutations.stream().forEach(letterCombos::addMultiLetterCombo);
        }
        return letterCombos;
    }


}
