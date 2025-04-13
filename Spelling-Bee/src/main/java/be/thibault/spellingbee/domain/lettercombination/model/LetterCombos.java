package be.thibault.spellingbee.domain.lettercombination.model;

import java.util.HashSet;
import java.util.Set;

public class LetterCombos {

    private Set<FourLetterCombo> fourLetterCombos;
    private Set<FiveLetterCombo> fiveLetterCombos ;

    public LetterCombos(){
        this.fourLetterCombos = new HashSet<>();
        this.fiveLetterCombos = new HashSet<>();
    }

    public LetterCombos(Set<FourLetterCombo> fourLetterCombos,
                        Set<FiveLetterCombo> fiveLetterCombos) {
        this.fourLetterCombos = fourLetterCombos;
        this.fiveLetterCombos = fiveLetterCombos;
    }

    public void addMultiLetterCombo(String letterCombo){

        switch (letterCombo.length()){
            case 4 -> this.fourLetterCombos.add(new FourLetterCombo(letterCombo));
            case 5 -> this.fiveLetterCombos.add(new FiveLetterCombo(letterCombo));
        }
    }

    public Set<String> getCombosAsString(){
        Set<String> allCombos = new HashSet<>();
        fourLetterCombos.forEach(combo -> allCombos.add(combo.getComboAsString()));
        fiveLetterCombos.forEach(combo -> allCombos.add(combo.getComboAsString()));
        return allCombos;
    }


}
