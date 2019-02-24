package udvari.HobbyTicTacToe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import udvari.HobbyTicTacToe.domain.Challenge;
import udvari.HobbyTicTacToe.domain.Player;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

    Challenge findChallengeByChallenged(Player player);

    Challenge findChallengeByChallenger(Player player);

}
