package udvari.HobbyTicTacToe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import udvari.HobbyTicTacToe.domain.Move;

public interface MoveRepository extends JpaRepository<Move, Long> {
}
