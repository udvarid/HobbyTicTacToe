package udvari.HobbyTicTacToe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import udvari.HobbyTicTacToe.domain.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    Player findByName(String name);

}
