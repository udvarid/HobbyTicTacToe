package udvari.HobbyTicTacToe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import udvari.HobbyTicTacToe.domain.TicTacToeGame;

public interface TicTacToeRepository extends JpaRepository<TicTacToeGame, Long> {
}
