package be.thibault.spellingbee.adapters.repository;

import be.thibault.spellingbee.domain.game.GameState;
import be.thibault.spellingbee.domain.letterselection.LetterSelection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GameStateRepository extends JpaRepository<GameState, String> {

    @Query("SELECT g.letterSelection as letterSelection FROM GameState g")
    List<LetterSelection> findUsedLetterSelections();

}
