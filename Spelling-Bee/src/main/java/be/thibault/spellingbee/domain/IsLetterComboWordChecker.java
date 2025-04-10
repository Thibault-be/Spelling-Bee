package be.thibault.spellingbee.domain;

import be.thibault.spellingbee.domain.lettercombo.FiveLetterCombo;
import be.thibault.spellingbee.domain.lettercombo.LetterCombos;
import be.thibault.spellingbee.domain.lettercombo.MultiLetterCombo;
import be.thibault.spellingbee.domain.wordsolution.MultiLetterSolution;
import be.thibault.spellingbee.domain.wordsolution.Solution;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class IsLetterComboWordChecker {

    //todo: should this also be an interface?

    private final Dictionary dictionary;

    public IsLetterComboWordChecker(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public String checkComboForWord(String letterCombo){
        List<String> entries = dictionary.entries();
        boolean contains = entries.contains(letterCombo);

        return entries.contains(letterCombo) ? letterCombo : null;
    }

}
