package be.thibault.spellingbee.adapters.repository.converters;

import be.thibault.spellingbee.domain.letterselection.LetterSelection;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class LetterSelectionConverter implements AttributeConverter<LetterSelection, String> {

    @Override
    public String convertToDatabaseColumn(LetterSelection letterSelection) {
        return letterSelection.toString();
    }

    @Override
    public LetterSelection convertToEntityAttribute(String s) {
        return LetterSelection.fromString(s);
    }
}
