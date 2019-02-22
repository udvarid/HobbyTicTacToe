package udvari.HobbyTicTacToe.domain;

public enum GameStatus {

    RUNNING("Still running"),

    BROKEN("It was broken"),

    WIN_ONE("Player One won"),

    WIN_TWO("Player Two won"),

    DRAW("It was a draw");

    private String displayName;

    GameStatus(String displayName) {

        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
