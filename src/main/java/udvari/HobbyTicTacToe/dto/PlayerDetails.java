package udvari.HobbyTicTacToe.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PlayerDetails {

    @NotNull
    @Size(min = 5, max = 100, message = "{name.boundaries}")
    private String name;

    private String playerStatus;

    private String partnerName;

    private String gameTypeName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlayerStatus() {
        return playerStatus;
    }

    public void setPlayerStatus(String playerStatus) {
        this.playerStatus = playerStatus;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getGameTypeName() {
        return gameTypeName;
    }

    public void setGameTypeName(String gameTypeName) {
        this.gameTypeName = gameTypeName;
    }
}
