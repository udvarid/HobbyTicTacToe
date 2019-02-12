package udvari.HobbyTicTacToe.domain;

public enum TicTacToeType {

    RUNNING("Still running"),

    WIN_ONE("Player One won"),

    WIN_TWO("Player Two won"),

    DRAW("It was a draw");

    private String displayName;

    TicTacToeType(String displayName) {

        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
