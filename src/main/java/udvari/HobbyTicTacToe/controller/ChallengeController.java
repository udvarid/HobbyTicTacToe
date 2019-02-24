package udvari.HobbyTicTacToe.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import udvari.HobbyTicTacToe.dto.ChallengeDetails;
import udvari.HobbyTicTacToe.service.PlayerService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/challenge")
public class ChallengeController {

    private final PlayerService playerService;
    private static final Logger logger = LoggerFactory.getLogger(PlayerController.class);


    @Autowired
    public ChallengeController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping
    public ResponseEntity<?> challengePlayer(@RequestBody ChallengeDetails challengeDetails, HttpServletRequest request) {
        HttpSession session = request.getSession();

        logger.info("Challenge is on the way");

        if (session != null && session.getAttribute("name") != null) {
            String challenger = (String) session.getAttribute("name");
            String challenged = challengeDetails.getName();
            logger.info(challenger + " challenges " + challenged);
            if (playerService.createChallenge(challenger, challenged)) {
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
        }
        logger.info("New challenge is rejected");
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
