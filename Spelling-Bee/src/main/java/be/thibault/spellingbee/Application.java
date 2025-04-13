package be.thibault.spellingbee;

import be.thibault.spellingbee.config.DictionaryApiClient;
import be.thibault.spellingbee.domain.lettercombination.externaldictionary.CommonWordChecker;
import be.thibault.spellingbee.domain.lettercombination.model.FourLetterCombo;
import be.thibault.spellingbee.domain.lettercombination.model.LetterCombos;
import be.thibault.spellingbee.domain.letterselection.LetterSelection;
import be.thibault.spellingbee.domain.localdictionary.LocalDictionary;
import be.thibault.spellingbee.domain.localdictionary.LocalDictionaryReaderImpl;
import be.thibault.spellingbee.domain.localdictionary.LocalDictionaryEntryChecker;
import be.thibault.spellingbee.domain.lettercombination.service.LetterCombinationGenerator;
import be.thibault.spellingbee.domain.lettercombination.service.LetterCombosProviderImpl;
import be.thibault.spellingbee.domain.letterselection.LetterSelectionProviderImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootApplication
public class Application {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        LetterSelectionProviderImpl lp = new LetterSelectionProviderImpl();
        LetterSelection letters = lp.getLetterSelection();

        LetterCombosProviderImpl provider = new LetterCombosProviderImpl(new LetterCombinationGenerator());

        LetterCombos letterCombos = provider.generateLetterCombos(letters);

        LocalDictionary localDictionary = new LocalDictionaryReaderImpl().importDictionary();


        LocalDictionaryEntryChecker localDictionaryEntryChecker = new LocalDictionaryEntryChecker(localDictionary);

        Set<FourLetterCombo> fourLetterCombos = letterCombos.fourLetterCombos();

        List<String> localEntries = fourLetterCombos.stream()
                .map(FourLetterCombo::fourLetterCombo)
                .filter(localDictionary::containsLetterCombo)
                .toList();

        DictionaryApiClient dictionaryApiClient = new DictionaryApiClient();

        CommonWordChecker commonWordChecker = new CommonWordChecker(dictionaryApiClient);

        Set<String> externalEntries = localEntries.stream().filter(commonWordChecker::isCommonWord)
                .collect(Collectors.toSet());

        int x = 6;


    }


}
