
package udvari.HobbyTicTacToe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import udvari.HobbyTicTacToe.domain.Challenge;
import udvari.HobbyTicTacToe.domain.GameType;
import udvari.HobbyTicTacToe.domain.Player;
import udvari.HobbyTicTacToe.domain.PlayerStatus;
import udvari.HobbyTicTacToe.dto.PlayerDetails;
import udvari.HobbyTicTacToe.repository.ChallengeRepository;
import udvari.HobbyTicTacToe.repository.PlayerRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PlayerService {

    private PlayerRepository playerRepository;
    private ChallengeRepository challengeRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository, ChallengeRepository challengeRepository) {
        this.playerRepository = playerRepository;
        this.challengeRepository = challengeRepository;
    }

    public List<PlayerDetails> listPlayers() {
        List<Player> players = playerRepository.findAll();
        List<PlayerDetails> result = new ArrayList<>();
        for (Player player : players) {
            PlayerDetails playerDetails = new PlayerDetails();
            playerDetails.setName(player.getName());
            playerDetails.setPlayerStatus(player.getType().getDisplayName());
            result.add(playerDetails);
        }
        return result;
    }


    public void registerPlayer(PlayerDetails playerDetails) {

        Player player = new Player();
        player.setName(playerDetails.getName());
        player.setType(PlayerStatus.FREE_AND_ACTIVE);
        playerRepository.save(player);

    }

    public boolean deletePlayer(String name) {

        Player player = playerRepository.findByName(name);
        if (player != null) {
            playerRepository.delete(player);
            return true;
        }

        return false;
    }

    public Player findPlayerByName(String name) {
        return playerRepository.findByName(name);
    }

    public boolean createChallenge(String challenger, String challenged) {

        Player challengerPlayer = playerRepository.findByName(challenger);
        Player challengedPlayer = playerRepository.findByName(challenged);

        if (challengedPlayer.getType() == PlayerStatus.FREE_AND_ACTIVE &&
        challengerPlayer.getType() == PlayerStatus.FREE_AND_ACTIVE) {
            Challenge challenge = new Challenge();
            challenge.setChallenger(challengerPlayer);
            challenge.setChallenged(challengedPlayer);
            //Ezt később még - ha már több típusú játék is lesz - változtatandó
            challenge.setGameType(GameType.TICTACTOE);
            challengeRepository.save(challenge);

            challengerPlayer.setType(PlayerStatus.INVITOR);
            challengedPlayer.setType(PlayerStatus.INVITED);

            return true;
        }

        return false;
    }
}
