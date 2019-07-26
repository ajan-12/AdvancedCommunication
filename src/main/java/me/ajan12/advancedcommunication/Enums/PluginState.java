package me.ajan12.advancedcommunication.Enums;

public enum PluginState {

    IDLE("idling"),

    IMPORTING_GROUPS("importing groups"),
    IMPORTING_USERS("importing users"),

    SAVING_GROUPS("saving groups"),
    SAVING_USERS("saving users"),

    PURGING_GROUPS("purging groups"),
    PURGING_USERS("purging users");

    private String message;

    PluginState(final String message) {
        this.message = message;
    }

    @Override
    public String toString() { return message; }
}
