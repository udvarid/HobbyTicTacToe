package udvari.HobbyTicTacToe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import udvari.HobbyTicTacToe.domain.TicTacToeMove;

public interface TicTacToeMoveRepository extends JpaRepository<TicTacToeMove, Long> {
}
