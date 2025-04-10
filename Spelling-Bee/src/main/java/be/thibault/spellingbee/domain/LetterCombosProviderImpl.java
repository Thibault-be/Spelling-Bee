package be.thibault.spellingbee.domain;

import be.thibault.spellingbee.domain.lettercombo.FiveLetterCombo;
import be.thibault.spellingbee.domain.lettercombo.FourLetterCombo;
import be.thibault.spellingbee.domain.lettercombo.LetterCombos;

import java.util.Set;
import java.util.stream.Collectors;

//@Service
public class LetterCombosProviderImpl implements LetterCombosProvider {

    private final LetterCombinationGenerator letterCombinationGenerator;

    public LetterCombosProviderImpl(LetterCombinationGenerator letterCombinationGenerator) {
        this.letterCombinationGenerator = letterCombinationGenerator;
    }

    @Override
    public LetterCombos generateLetterCombos(LetterSelection letterSelection) {

        Set<String> fourLetterPermuations = letterCombinationGenerator.generateCombinations(letterSelection, 4);
        Set<String> fiveLetterPermutations = letterCombinationGenerator.generateCombinations(letterSelection, 5);

        Set<FourLetterCombo> fourLetterCombos = fourLetterPermuations.stream().map(FourLetterCombo::new).collect(Collectors.toSet());
        Set<FiveLetterCombo> fiveLetterCombos = fiveLetterPermutations.stream().map(FiveLetterCombo::new).collect(Collectors.toSet());

        return new LetterCombos(fourLetterCombos, fiveLetterCombos);
    }
}
