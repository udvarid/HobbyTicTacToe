package udvari.HobbyTicTacToe.domain;

public enum PlayerStatus {

    FREE_AND_ACTIVE("Free and active player"),

    FREE_BUT_SLEEPING("Free but inactive player"),

    INVITOR("Invited somebody"),

    INVITED("Invited by somebody"),

    PLAYING("Playing in a game");


    private String displayName;

    PlayerStatus(String displayName) {

        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }


}
