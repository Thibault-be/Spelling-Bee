package be.thibault.spellingbee.adapter.repository;

import be.thibault.spellingbee.domain.game.GameState;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameStateRepository extends JpaRepository<GameState, String> {
}
