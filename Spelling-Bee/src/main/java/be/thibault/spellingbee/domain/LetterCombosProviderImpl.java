package be.thibault.spellingbee.domain;

import be.thibault.spellingbee.domain.words.FourLetterCombo;
import be.thibault.spellingbee.domain.words.LetterCombos;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

//@Service
public class LetterCombosProviderImpl implements LetterCombosProvider {

    private final LetterCombinationGenerator letterCombinationGenerator;

    public LetterCombosProviderImpl(LetterCombinationGenerator letterCombinationGenerator) {
        this.letterCombinationGenerator = letterCombinationGenerator;
    }

    @Override
    public LetterCombos generateLetterCombos(LetterSelection letterSelection) {

        Set<String> fourLetterWords = letterCombinationGenerator.generateCombinations(letterSelection, 4);



        return null;
    }

    public List<FourLetterCombo> getFourLetterCombos(LetterSelection letterSelection){



        return null;
    }


}
