package udvari.HobbyTicTacToe.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import javax.servlet.http.HttpServletRequest;
import udvari.HobbyTicTacToe.dto.PlayerDetails;
import udvari.HobbyTicTacToe.service.PlayerService;


@Component
public class PlayerDetailsValidator implements Validator {

    private PlayerService playerService;

    private HttpServletRequest request;

    public PlayerDetailsValidator(PlayerService playerService, HttpServletRequest request) {
        this.playerService = playerService;
        this.request = request;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return PlayerDetails.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PlayerDetails playerDetails = (PlayerDetails) target;

        if (playerService.findPlayerByName(playerDetails.getName()) != null) {
            errors.rejectValue("name", "playerName.already.used");
        }
    }
}
