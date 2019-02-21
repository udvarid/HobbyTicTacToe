package udvari.HobbyTicTacToe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import udvari.HobbyTicTacToe.domain.Challenge;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
}
