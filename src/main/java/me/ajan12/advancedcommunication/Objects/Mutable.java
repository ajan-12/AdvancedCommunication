package me.ajan12.advancedcommunication.Objects;

interface Mutable {

    byte getMuteState();

    boolean isSoftMuted();
    boolean isHardMuted();

    void softMute(final boolean softMuted);
    void hardMute(final boolean hardMuted);

    long getMuteEnd();
    void setMuteEnd(final long muteEnd);

    String getMuteReason();
    void setMuteReason(final String reason);
}
