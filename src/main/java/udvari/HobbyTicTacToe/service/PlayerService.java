
package udvari.HobbyTicTacToe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import udvari.HobbyTicTacToe.domain.Challenge;
import udvari.HobbyTicTacToe.domain.Player;
import udvari.HobbyTicTacToe.domain.PlayerStatus;
import udvari.HobbyTicTacToe.dto.PlayerDetails;
import udvari.HobbyTicTacToe.repository.PlayerRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PlayerService {

    private PlayerRepository playerRepository;
    private ChallengeService challengeService;


    @Autowired
    public PlayerService(PlayerRepository playerRepository, ChallengeService challengeService) {
        this.playerRepository = playerRepository;
        this.challengeService = challengeService;
    }

    public List<PlayerDetails> listPlayers() {
        List<Player> players = playerRepository.findAll();
        List<PlayerDetails> result = new ArrayList<>();
        for (Player player : players) {
            PlayerDetails playerDetails = new PlayerDetails();
            playerDetails.setName(player.getName());
            playerDetails.setPlayerStatus(player.getType().getDisplayName());
            playerDetails.setPartnerName(challengeService.giveMePartnerName(player));
            playerDetails.setGameTypeName(challengeService.giveMeGameName(player));
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
        challengeService.deleteChallenge(player);

        if (player != null) {
            playerRepository.delete(player);
            return true;
        }

        return false;
    }

    public Player findPlayerByName(String name) {
        return playerRepository.findByName(name);
    }



}
