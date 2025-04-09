package be.thibault.spellingbee;

import be.thibault.spellingbee.domain.*;
import be.thibault.spellingbee.domain.words.LetterCombos;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpellingBeeApplication {

	//private final LetterCombosProvider letterCombosProvider;

//	public SpellingBeeApplication(LetterCombosProvider letterCombosProvider) {
//		this.letterCombosProvider = letterCombosProvider;
//	}


	public static void main(String[] args) {
		SpringApplication.run(SpellingBeeApplication.class, args);

		LetterSelectionProviderImpl lp = new LetterSelectionProviderImpl();
		LetterSelection letters = lp.getLetterSelection();

		LetterCombosProviderImpl provider = new LetterCombosProviderImpl(new LetterCombinationGenerator());

		LetterCombos letterCombos = provider.generateLetterCombos(letters);


	}



}
