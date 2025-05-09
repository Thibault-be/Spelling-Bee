package be.thibault.spellingbee.adapters.repository;

import be.thibault.spellingbee.domain.game.GameState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameStateRepository extends JpaRepository<GameState, String> {
}
