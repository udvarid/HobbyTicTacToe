package udvari.HobbyTicTacToe.domain;

public enum PlayerType {

    PLAYER_ONE("Player One"),

    PLAYER_TWO("Player Two");


    private String displayName;

    PlayerType(String displayName) {

        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
