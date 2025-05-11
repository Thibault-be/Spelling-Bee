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
        this.ranking = Ranking.BEGINNER;
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

    //todo - move to game serve and then set the max score
    private int determineMaxScore() {
        this.possibleWords
                .forEach(word -> this.maxScore += determineWordScore(word));

        return this.maxScore;
    }

    public void setMaxScore(int newMaxScore){
        this.maxScore = newMaxScore;
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

    public void setScore(int score){
        this.score = score;
    }

    public Ranking getRanking() {
        return ranking;
    }

    public int getMaxScore(){
        return this.maxScore;
    }

    public void setRanking(Ranking ranking){
        this.ranking = ranking;
    }
}
