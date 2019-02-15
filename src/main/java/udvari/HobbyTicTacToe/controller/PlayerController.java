package udvari.HobbyTicTacToe.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import udvari.HobbyTicTacToe.dto.PlayerDetails;
import udvari.HobbyTicTacToe.service.PlayerService;
import udvari.HobbyTicTacToe.validation.PlayerDetailsValidator;

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
    public ResponseEntity<?> registerPlayer(@Valid @RequestBody PlayerDetails playerDetails) {
        if (playerService.registerPlayer(playerDetails)) {
            logger.info("New player is registering");
            return new ResponseEntity<>(HttpStatus.CREATED);

        }
        logger.warn("Security issue: somebody trying to take an already taken name");
        return new ResponseEntity<>("Player name is already taken", HttpStatus.UNAUTHORIZED);

    }

    @DeleteMapping("/{name}")
    public ResponseEntity<?> deletePlayer(@PathVariable String name) {
        boolean isDeleteSuccessful = playerService.deletePlayer(name);

        ResponseEntity<?> result;
        if (isDeleteSuccessful) {
            logger.info(name + " player is deleted");
            result = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            logger.info(name + " player delete attempt but he/she is not registered");
            result = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return result;
    }


}
