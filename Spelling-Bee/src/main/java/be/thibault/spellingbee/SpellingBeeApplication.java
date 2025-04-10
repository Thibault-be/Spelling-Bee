package be.thibault.spellingbee;

import be.thibault.spellingbee.domain.*;
import be.thibault.spellingbee.domain.lettercombo.FourLetterCombo;
import be.thibault.spellingbee.domain.lettercombo.LetterCombos;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class SpellingBeeApplication {


    public static void main(String[] args) {
        SpringApplication.run(SpellingBeeApplication.class, args);

        LetterSelectionProviderImpl lp = new LetterSelectionProviderImpl();
        LetterSelection letters = lp.getLetterSelection();

        LetterCombosProviderImpl provider = new LetterCombosProviderImpl(new LetterCombinationGenerator());

        LetterCombos letterCombos = provider.generateLetterCombos(letters);

        Dictionary dictionary = new DictionaryReaderImpl().importDictionary();


        IsLetterComboWordChecker isLetterComboWordChecker = new IsLetterComboWordChecker(dictionary);

        Set<FourLetterCombo> fourLetterCombos = letterCombos.fourLetterCombos();

        List<String> list = fourLetterCombos.stream()
                .map(FourLetterCombo::fourLetterCombo)
                .filter(dictionary::containsLetterCombo)
                .toList();

        int x = 7;


    }


}
