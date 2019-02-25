package udvari.HobbyTicTacToe.domain;

public enum PlayerStatus {

    FREE_AND_ACTIVE("Free and active player"),

    FREE_BUT_SLEEPING("Free but inactive player"),

    INVITOR("Invited"),

    INVITED("Invited by"),

    PLAYING("Playing a game with");


    private String displayName;

    PlayerStatus(String displayName) {

        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }


}
