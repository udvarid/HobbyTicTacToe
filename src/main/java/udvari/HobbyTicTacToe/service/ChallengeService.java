package udvari.HobbyTicTacToe.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import udvari.HobbyTicTacToe.controller.PlayerController;
import udvari.HobbyTicTacToe.domain.*;
import udvari.HobbyTicTacToe.repository.ChallengeRepository;
import udvari.HobbyTicTacToe.repository.GameRepository;
import udvari.HobbyTicTacToe.repository.PlayerRepository;

import java.time.LocalDateTime;

@Service
@Transactional
public class ChallengeService {

    private PlayerRepository playerRepository;
    private ChallengeRepository challengeRepository;
    private GameRepository gameRepository;
    private static final Logger logger = LoggerFactory.getLogger(PlayerController.class);


    @Autowired
    public ChallengeService(PlayerRepository playerRepository, GameRepository gameRepository, ChallengeRepository challengeRepository) {
        this.playerRepository = playerRepository;
        this.challengeRepository = challengeRepository;
        this.gameRepository = gameRepository;
    }

    public boolean createChallenge(String challenger, String challenged) {

        Player challengerPlayer = playerRepository.findByName(challenger);
        Player challengedPlayer = playerRepository.findByName(challenged);

        if (challengedPlayer.getType() == PlayerStatus.FREE_AND_ACTIVE &&
                challengerPlayer.getType() == PlayerStatus.FREE_AND_ACTIVE) {
            Challenge challenge = new Challenge();
            challenge.setChallenger(challengerPlayer);
            challenge.setChallenged(challengedPlayer);
            challenge.setTimeStamp(LocalDateTime.now());
            //Ezt később még - ha már több típusú játék is lesz - változtatandó
            challenge.setGameType(GameType.TICTACTOE);
            challengeRepository.save(challenge);

            challengerPlayer.setType(PlayerStatus.INVITOR);
            challengedPlayer.setType(PlayerStatus.INVITED);

            return true;
        }

        return false;
    }

    public void deleteChallenge(Player player) {
        Challenge challengeOne = challengeRepository.findChallengeByChallenged(player);
        Challenge challengeTwo = challengeRepository.findChallengeByChallenger(player);

        if (challengeOne != null) {
            Player otherPlayer = playerRepository.findByName(challengeOne.getChallenger().getName());
            otherPlayer.setType(PlayerStatus.FREE_AND_ACTIVE);

            challengeRepository.delete(challengeOne);
            logChallengeDelete(challengeOne);
        }

        if (challengeTwo != null) {
            Player otherPlayer = playerRepository.findByName(challengeTwo.getChallenged().getName());
            otherPlayer.setType(PlayerStatus.FREE_AND_ACTIVE);

            challengeRepository.delete(challengeTwo);
            logChallengeDelete(challengeTwo);
        }

    }

    private void logChallengeDelete(Challenge challengeOne) {
        logger.info(challengeOne.toString() + " challenge is deleted!");
    }

    public String giveMePartnerName(Player player) {
        Challenge challengeOne = challengeRepository.findChallengeByChallenged(player);
        Challenge challengeTwo = challengeRepository.findChallengeByChallenger(player);

        String result = "";

        if (challengeOne != null) {
            result = challengeOne.getChallenger().getName();
        }

        if (challengeTwo != null) {
            result = challengeTwo.getChallenged().getName();
        }

        return result;
    }

    public String giveMeGameName(Player player) {
        Challenge challengeOne = challengeRepository.findChallengeByChallenged(player);
        Challenge challengeTwo = challengeRepository.findChallengeByChallenger(player);

        String result = "";

        if (challengeOne != null) {
            result = challengeOne.getGameType().getDisplayName();
        }

        if (challengeTwo != null) {
            result = challengeTwo.getGameType().getDisplayName();
        }

        return result;
    }


    public boolean acceptChallenge(String name) {
        Player player = playerRepository.findByName(name);
        Player playerOther = null;
        GameType gameType = null;


        Challenge challengeOne = challengeRepository.findChallengeByChallenged(player);

        boolean result = false;

        if (challengeOne != null) {
            playerOther = playerRepository.findByName(challengeOne.getChallenger().getName());
            player.setType(PlayerStatus.PLAYING);
            playerOther.setType(PlayerStatus.PLAYING);
            gameType = challengeOne.getGameType();
            challengeRepository.delete(challengeOne);

            Game game = new Game(name, playerOther.getName());
            game.setGameType(gameType);
            gameRepository.save(game);
            logger.warn("New game was created!");

        }


        return result;
    }

    public boolean cancelChallenge(String name) {
        Player player = playerRepository.findByName(name);

        Challenge challengeOne = challengeRepository.findChallengeByChallenged(player);
        Challenge challengeTwo = challengeRepository.findChallengeByChallenger(player);

        boolean result = false;

        if (challengeOne != null) {
            Player playerOther = playerRepository.findByName(challengeOne.getChallenger().getName());
            player.setType(PlayerStatus.FREE_AND_ACTIVE);
            playerOther.setType(PlayerStatus.FREE_AND_ACTIVE);
            challengeRepository.delete(challengeOne);
            result = true;
        }

        if (challengeTwo != null) {
            Player playerOther = playerRepository.findByName(challengeTwo.getChallenged().getName());
            player.setType(PlayerStatus.FREE_AND_ACTIVE);
            playerOther.setType(PlayerStatus.FREE_AND_ACTIVE);
            challengeRepository.delete(challengeTwo);
            result = true;
        }


        return result;
    }
}
