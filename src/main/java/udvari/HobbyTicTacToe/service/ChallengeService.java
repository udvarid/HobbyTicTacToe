package udvari.HobbyTicTacToe.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import udvari.HobbyTicTacToe.controller.PlayerController;
import udvari.HobbyTicTacToe.domain.Challenge;
import udvari.HobbyTicTacToe.domain.GameType;
import udvari.HobbyTicTacToe.domain.Player;
import udvari.HobbyTicTacToe.domain.PlayerStatus;
import udvari.HobbyTicTacToe.repository.ChallengeRepository;
import udvari.HobbyTicTacToe.repository.PlayerRepository;

import java.time.LocalDateTime;

@Service
@Transactional
public class ChallengeService {

    private PlayerRepository playerRepository;
    private ChallengeRepository challengeRepository;
    private static final Logger logger = LoggerFactory.getLogger(PlayerController.class);


    @Autowired
    public ChallengeService(PlayerRepository playerRepository, ChallengeRepository challengeRepository) {
        this.playerRepository = playerRepository;
        this.challengeRepository = challengeRepository;
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
}
