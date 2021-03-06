package udvari.HobbyTicTacToe.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import udvari.HobbyTicTacToe.service.ChallengeService;
import udvari.HobbyTicTacToe.service.GameService;
import udvari.HobbyTicTacToe.service.SendAutomaticListService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private final GameService gameService;
    private static final Logger logger = LoggerFactory.getLogger(GameController.class);
    private SendAutomaticListService sendAutomaticListService;


    @Autowired
    public GameController(GameService gameService, SendAutomaticListService sendAutomaticListService) {
        this.gameService = gameService;
        this.sendAutomaticListService = sendAutomaticListService;
    }

    @GetMapping("move")
    public ResponseEntity<?> makeMove(HttpServletRequest request) {
        HttpSession session = request.getSession();
        logger.info("Making a move");

        if (session != null && session.getAttribute("name") != null &&
                gameService.makeMove((String) session.getAttribute("name"))) {
            sendAutomaticListService.pushAnHello("a move in a game");
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("giveUp")
    public ResponseEntity<?> giveUpGame(HttpServletRequest request) {
        HttpSession session = request.getSession();
        logger.info("Giving up a game");

        if (session != null && session.getAttribute("name") != null &&
                gameService.giveUpGame((String) session.getAttribute("name"))) {
            sendAutomaticListService.pushAnHello("end of a game");
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
