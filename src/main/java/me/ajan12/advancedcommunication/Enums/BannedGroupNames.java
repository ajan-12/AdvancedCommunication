package me.ajan12.advancedcommunication.Enums;

public enum BannedGroupNames {

    HELP(),
    LIST(),
    CREATE(),
    ACCEPT(),
    DENY();

    BannedGroupNames() {}

    /**
     * Checks if the given name is banned.
     *
     * @param name: The name to check if banned.
     * @return    : True if the name is banned, false otherwise.
     */
    public static boolean containsName(final String name) {

        //Iterating over the banned names.
        for (final BannedGroupNames value : values()) {

            //Checking if the banned name equals to the name given.
            if (value.name().equalsIgnoreCase(name)) return true;

        }
        //Returning false as no matches made.
        return false;
    }
}
