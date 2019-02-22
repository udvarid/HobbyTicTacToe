package udvari.HobbyTicTacToe.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import udvari.HobbyTicTacToe.dto.ChallengeDetails;
import udvari.HobbyTicTacToe.dto.PlayerDetails;
import udvari.HobbyTicTacToe.service.PlayerService;
import udvari.HobbyTicTacToe.validation.PlayerDetailsValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private final PlayerService playerService;
    private static final Logger logger = LoggerFactory.getLogger(PlayerController.class);
    private PlayerDetailsValidator playerDetailsValidator;


    @Autowired
    public PlayerController(PlayerService playerService, PlayerDetailsValidator playerDetailsValidator) {
        this.playerService = playerService;
        this.playerDetailsValidator = playerDetailsValidator;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(playerDetailsValidator);
    }


    @GetMapping
    public ResponseEntity<List<PlayerDetails>> getPlayers() {
        return new ResponseEntity<>(playerService.listPlayers(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> registerPlayer(@Valid @RequestBody PlayerDetails playerDetails, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session == null || session.getAttribute("name") == null) {
            session.setAttribute("name", playerDetails.getName());
            playerService.registerPlayer(playerDetails);
            logger.info(playerDetails.getName() + " is registering");
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            logger.info("New player is registering rejected");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }


    }

    @PostMapping("/challenge")
    public ResponseEntity<?> challengePlayer(@RequestBody ChallengeDetails challengeDetails, HttpServletRequest request) {
        HttpSession session = request.getSession();

        logger.info("Challenge is on the way");

        if (session != null && session.getAttribute("name") != null) {
            String challenger = (String) session.getAttribute("name");
            String challenged = challengeDetails.getChallenged();
            logger.info(challenger + " challenges " + challenged);
            if (playerService.createChallenge(challenger, challenged)) {
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
        }
        logger.info("New challenge is rejected");
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping
    public ResponseEntity<?> deletePlayer(HttpServletRequest request) {
        ResponseEntity<?> result;
        HttpSession session = request.getSession();
        if (session != null && session.getAttribute("name") != null
                && playerService.deletePlayer((String) session.getAttribute("name"))) {
            String name = (String) session.getAttribute("name");
            logger.info(name + " player is deleted");
            result = new ResponseEntity<>(HttpStatus.NO_CONTENT);
            session.invalidate();
        } else {
            String name = (String) session.getAttribute("name");
            logger.info(name + " player delete attempt but he/she is not registered");
            result = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return result;
    }


}
