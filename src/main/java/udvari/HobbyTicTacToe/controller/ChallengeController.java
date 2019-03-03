package udvari.HobbyTicTacToe.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import udvari.HobbyTicTacToe.dto.ChallengeDetails;
import udvari.HobbyTicTacToe.service.ChallengeService;
import udvari.HobbyTicTacToe.service.SendAutomaticListService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/challenge")
public class ChallengeController {

    private final ChallengeService challengeService;
    private static final Logger logger = LoggerFactory.getLogger(ChallengeController.class);
    private SendAutomaticListService sendAutomaticListService;


    @Autowired
    public ChallengeController(ChallengeService challengeService, SendAutomaticListService sendAutomaticListService) {
        this.challengeService = challengeService;
        this.sendAutomaticListService = sendAutomaticListService;
    }

    @PostMapping
    public ResponseEntity<?> challengePlayer(@RequestBody ChallengeDetails challengeDetails, HttpServletRequest request) {
        HttpSession session = request.getSession();

        logger.info("Challenge is on the way");

        if (session != null && session.getAttribute("name") != null) {
            String challenger = (String) session.getAttribute("name");
            String challenged = challengeDetails.getName();
            logger.info(challenger + " challenges " + challenged);
            if (challengeService.createChallenge(challenger, challenged)) {
                sendAutomaticListService.pushAnHello(challenger + " challenged " + challenged);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
        }
        logger.info("New challenge is rejected");
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("accept")
    public ResponseEntity<?> acceptChallenge(HttpServletRequest request) {
        HttpSession session = request.getSession();
        logger.info("Accepting a challenge");

        if (session != null && session.getAttribute("name") != null &&
        challengeService.acceptChallenge((String) session.getAttribute("name"))) {
            sendAutomaticListService.pushAnHello("a challenge is accepted, new game is starting");
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("cancel")
    public ResponseEntity<?> deleteChallenge(HttpServletRequest request) {
        HttpSession session = request.getSession();
        logger.info("Declining/Cancelling a challenge");
        if (session != null && session.getAttribute("name") != null &&
                challengeService.cancelChallenge((String) session.getAttribute("name"))) {
            sendAutomaticListService.pushAnHello("a challenge is rejected");
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
