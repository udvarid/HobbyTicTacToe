package udvari.HobbyTicTacToe.domain;

public enum GameType {

    TICTACTOE("Tic-Tac-Toe");

    private String displayName;

    GameType(String displayName) {

        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
