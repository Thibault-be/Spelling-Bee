package be.thibault.spellingbee.domain.game;

import be.thibault.spellingbee.adapters.repository.converters.LetterSelectionConverter;
import be.thibault.spellingbee.domain.enums.Ranking;
import be.thibault.spellingbee.domain.letterselection.LetterSelection;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

//todo: move businesslogic-y methods outside of this class

@Entity
@Table(name = "GAMESTATE")
public class GameState {

    @Id
    @GeneratedValue (generator = "ID_GENERATOR")
    private Long id;

    @Column(name = "LETTER_SELECTION")
    @Convert(converter= LetterSelectionConverter.class)
    private LetterSelection letterSelection;

    @ElementCollection
    @CollectionTable(name = "POSSIBLE_WORDS")
    private Set<String> possibleWords;

    @ElementCollection
    @CollectionTable(name = "FOUND_WORDS")
    @Column(name = "FOUND_WORD")
    private Set<String> foundWords;

    @Column(name = "SCORE")
    private int score;

    @Column(name = "MAXIMUM_SCORE")
    private int maxScore;

    @Column(name = "RANKING")
    @Enumerated(EnumType.STRING)
    private Ranking ranking;

    public GameState(){}

    public GameState(LetterSelection letterSelection, Set<String> possibleWords) {
        this.letterSelection = letterSelection;
        this.possibleWords = possibleWords;
        this.foundWords = new HashSet<>();
        this.score = 0;
        this.maxScore = determineMaxScore();
        determineRanking();
    }

    public void addGuessToFoundWords(String guess) {
        this.foundWords.add(guess);
    }

    public void updateScore(String guess) {
        this.score += determineWordScore(guess);
    }

    public int determineWordScore(String answer) {
        int length = answer.length();
        int guessScore = 0;

        if (length == 4) {
            guessScore = 1;
            return guessScore;
        }

        guessScore += answer.length();

        if (this.getLetterSelection().containsAllSevenLetters(answer)) {
            guessScore += 7;
        }

        return guessScore;
    }

    private int determineMaxScore() {
        this.possibleWords
                .forEach(word -> this.maxScore += determineWordScore(word));

        return this.maxScore;
    }

    public void determineRanking() {

        double percentage = ((double) this.score / (double) this.maxScore) * 100;
        Ranking newRank = null;

        if (percentage == 100) {
            newRank = Ranking.QUEEN_BEE;
        } else if (percentage < 2) {
            newRank = Ranking.BEGINNER;
        } else if (percentage < 5) {
            newRank = Ranking.GOOD_START;
        } else if (percentage < 8) {
            newRank = Ranking.MOVING_UP;
        } else if (percentage < 15) {
            newRank = Ranking.GOOD;
        } else if (percentage < 25) {
            newRank = Ranking.SOLID;
        } else if (percentage < 40) {
            newRank = Ranking.NICE;
        } else if (percentage < 51) {
            newRank = Ranking.GREAT;
        } else if (percentage < 71) {
            newRank = Ranking.AMAZING;
        } else {
            newRank = Ranking.GENIUS;
        }

        this.ranking = newRank;
    }

    public Long getGameId() {
        return id;
    }

    public LetterSelection getLetterSelection() {
        return letterSelection;
    }

    public Set<String> getPossibleWords() {
        return possibleWords;
    }

    public Set<String> getFoundWords() {
        return foundWords;
    }

    public Set<String> getVowelSelection() {
        char[] vowelSelection = this.getLetterSelection().getVowelSelection();
        Set<String> vowels = new HashSet<>();
        for (char c : vowelSelection) {
            vowels.add(String.valueOf(c));
        }
        return vowels;
    }

    public String getCompulsoryLetter() {
        return String.valueOf(this.getLetterSelection().getCompulsoryLetter());
    }

    public Set<String> getConsonantSelection() {
        char[] consonantSelection = this.getLetterSelection().getConsonantSelection();
        Set<String> consonants = new HashSet<>();

        for (char c : consonantSelection) {
            consonants.add(String.valueOf(c));
        }
        return consonants;
    }

    public int getScore() {
        return this.score;
    }

    public Ranking getRanking() {
        return ranking;
    }
}
