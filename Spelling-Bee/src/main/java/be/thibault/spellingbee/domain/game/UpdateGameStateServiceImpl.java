package be.thibault.spellingbee.domain.game;

import be.thibault.spellingbee.adapters.repository.GameStateRepository;
import be.thibault.spellingbee.domain.enums.GuessResult;
import be.thibault.spellingbee.domain.enums.Ranking;
import org.springframework.stereotype.Service;

import java.util.Set;

import static be.thibault.spellingbee.domain.enums.GuessResult.*;

@Service
public class UpdateGameStateServiceImpl implements UpdateGameStateService {

    private final GameStateRepository gameStateRepository;

    public UpdateGameStateServiceImpl(GameStateRepository gameStateRepository) {
        this.gameStateRepository = gameStateRepository;
    }

    @Override
    public GuessResult updateGameState(GameState gameState, String guess) {

        GuessResult guessResult = validGuess(guess, gameState);

        if (guessResult.equals(CORRECT_GUESS)) {
            setNewGameState(gameState, guess);
            this.gameStateRepository.save(gameState);
        }
        return guessResult;
    }

    @Override
    public int determineMaxScore(Set<String> possibleWords){
        return possibleWords.stream()
                .reduce(0, (id, word) -> id + determineGuessScore(word), (a, b) -> a + b);
    }

    private GuessResult validGuess(String guess, GameState gameState) {

        Set<String> foundWords = gameState.getFoundWords();
        Set<String> possibleWords = gameState.getPossibleWords();
        String compulsoryLetter = gameState.getCompulsoryLetter();

        if (guess.length() < 4) return NOT_LONG_ENOUGH;
        if (!guess.contains(compulsoryLetter)) return COMPULSORY_LETTER_MISSING;
        if (foundWords.contains(guess)) return ALREADY_FOUND;
        if (possibleWords.contains(guess)) return CORRECT_GUESS;

        return NOT_IN_LIST;
    }

    private void setNewGameState(GameState gameState, String guess){
        int newScore = determineGuessScore(guess) + gameState.getScore();
        gameState.setScore(newScore);

        Set<String> foundWords = gameState.getFoundWords();
        foundWords.add(guess);

        gameState.setRanking(determineRanking(gameState));
    }

    // 4-letter words = 1 point.
    // more than 4 letters = 1 point per letter.
    // All 7 different letters used = 7 bonus points
    public int determineGuessScore(String guess) {
        int length = guess.length();
        int guessScore = 0;

        if (length == 4) {
            guessScore = 1;
            return guessScore;
        }

        guessScore += guess.length();

        if (guess.chars().distinct().count() == 7) {
            guessScore += 7;
        }

        return guessScore;
    }

    private Ranking determineRanking(GameState gameState) {

        double percentage = (gameState.getScore() / (double) gameState.getMaxScore()) * 100;
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

        return newRank;
    }

}
