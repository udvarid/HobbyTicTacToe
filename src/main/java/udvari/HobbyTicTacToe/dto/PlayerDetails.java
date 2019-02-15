package udvari.HobbyTicTacToe.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PlayerDetails {

    @NotNull
    @Size(min = 5, max = 100, message = "{name.boundaries}")
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
