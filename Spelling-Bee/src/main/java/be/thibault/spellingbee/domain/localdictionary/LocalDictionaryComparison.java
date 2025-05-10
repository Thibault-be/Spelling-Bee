package be.thibault.spellingbee.domain.localdictionary;

import java.util.Set;

public record LocalDictionaryComparison (Set<String> matchingEntries,
                                         Set<String> mismatchingEntries) {
}
