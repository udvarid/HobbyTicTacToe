
package udvari.HobbyTicTacToe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import udvari.HobbyTicTacToe.domain.Player;
import udvari.HobbyTicTacToe.dto.PlayerDetails;
import udvari.HobbyTicTacToe.repository.PlayerRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PlayerService {

    private PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<PlayerDetails> listPlayers() {
        List<Player> players = playerRepository.findAll();
        List<PlayerDetails> result = new ArrayList<>();
        for (Player player : players) {
            result.add(new PlayerDetails(player.getName()));
        }
        return result;
    }

    public boolean registerPlayer(PlayerDetails playerDetails) {

        if (playerRepository.findByName(playerDetails.getName()) == null) {
            Player player = new Player();
            player.setName(playerDetails.getName());
            playerRepository.save(player);
            return true;
        }
        return false;


    }

}
