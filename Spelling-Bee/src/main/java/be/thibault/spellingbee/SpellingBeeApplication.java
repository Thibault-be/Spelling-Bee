package be.thibault.spellingbee;

import be.thibault.spellingbee.domain.LetterSelection;
import be.thibault.spellingbee.domain.LetterSelectionProviderImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpellingBeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpellingBeeApplication.class, args);

		LetterSelectionProviderImpl lp = new LetterSelectionProviderImpl();
		LetterSelection letters = lp.getLetterSelection();

		System.out.println(letters.letterSelection());
		System.out.println(letters.compulsoryLetter());

		String s = new DictionaryCaller().callDictionary();

		System.out.println(s);

	}



}
