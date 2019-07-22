package me.ajan12.advancedcommunication.Utilities.DatabaseUtils;

import me.ajan12.advancedcommunication.AdvancedCommunication;
import me.ajan12.advancedcommunication.Objects.Group;
import me.ajan12.advancedcommunication.Objects.User;
import me.ajan12.advancedcommunication.Utilities.DataStorage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

public class SQLiteUtils {

    //The URL containing the database file path. This is needed for JDBC library.
    private static String jdbcUrl;

    /**
     * Setups the connection to the sqlite database.
     *
     * @param path: The path to the database file.
     */
    public static void setup(final String path) {
        try {
            //Creating the database file.
            final File dbFile = new File(path);

            //Checking if the .db file exists.
            if (!dbFile.exists()) {
                //Creating the .db file.
                dbFile.createNewFile();
            }
        } catch (IOException e) { e.printStackTrace(); }

        //Initializing the JDBC URL to connect to the database.
        jdbcUrl = "jdbc:sqlite:" + path;

        try {

            //Connecting to the database.
            final Connection connection = DriverManager.getConnection(jdbcUrl);

            //Creating the groups table.
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS groups(uuid BLOB PRIMARY KEY, name VARCHAR(32), description VARCHAR(200), settings VARCHAR(3), creationTime INTEGER, lastUpdate INTEGER)").executeUpdate();

            //Creating the main users table.
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS users(userUUID BLOB)").executeUpdate();

            //Creating the users tables.
            for (int i = 1; i <= AdvancedCommunication.getInstance().getConfig().getInt("user-tables"); i++) {
                //Creating the "users + i" table.
                connection.prepareStatement("CREATE TABLE IF NOT EXISTS users" + i + "(userUUID BLOB PRIMARY KEY, groupUUID BLOB, isAdmin VARCHAR(1))").executeUpdate();
            }

            //Closing the connection.
            connection.close();

        //Catching any SQLException's that may have thrown while connecting.
        } catch (SQLException e) {

            //Informing the server console about this exception that has been thrown.
            System.out.println(e.getMessage());
        }
    }

    /**
     * Imports all the data in the database to DataStorage.
     */
    public static void importData() {
        //Importing the groups.
        importGroups();
        //Importing the users.
        importUsers();
    }

    /**
     * Imports the users in the database to DataStorage.
     */
    private static void importUsers() {
        try {

            //Getting the pluginTag to ease my work.
            final String pluginTag = DataStorage.pluginTag;

            //Connecting to the database.
            final Connection connection = DriverManager.getConnection(jdbcUrl);

            //Creating the hashset that will contain the all user's UUID's.
            final HashSet<UUID> userUUIDs = new HashSet<>();
            //Getting the user UUIDs.
            final ResultSet usersRS = connection.prepareStatement("SELECT * FROM users").executeQuery();
            //Iterating over the user UUIDs.
            while (usersRS.next()) {
                userUUIDs.add(UUID.fromString(usersRS.getBlob("uuid").toString()));
            }
            //Closing the usersRS as we're done with it.
            usersRS.close();

            //Creating the integers that we'll use to feedback console.
            int total = 0;
            int done = 0;
            //Iterating over all the user tables.
            for (int i = 1; i <= AdvancedCommunication.getInstance().getConfig().getInt("user-tables"); i++) {
                //Iterating over all the user UUIDs.
                for (final UUID userUUID : userUUIDs) {

                    //Getting the users in the "users + i" table.
                    final ResultSet resultSet = connection.prepareStatement("SELECT * FROM users" + i + " WHERE userUUID='" + userUUID.toString() + "'").executeQuery();

                    //Creating the hashmap that contains the groups this user is in.
                    final HashMap<String, UUID> groups = new HashMap<>();

                    //Iterating over the rows of resultSet
                    while (resultSet.next()) {

                        //Getting the user information that we need in the row.
                        final UUID groupUUID = UUID.fromString(resultSet.getBlob("groupUUID").toString());
                        final boolean isAdmin = resultSet.getString("isAdmin").equals("T");

                        //Getting the group we're at.
                        final Group group = DataStorage.groups.get(groupUUID);
                        //Adding this user as a member to the group.
                        group.addMember(userUUID, isAdmin);

                        //Adding this group to user's group map.
                        groups.put(group.getName(), groupUUID);
                    }

                    //Creating the User and saving it to DataStorage.
                    final User user = new User(null, userUUID, groups);
                    DataStorage.addUser(user);

                    //Increasing the variables we'll use for feedbacking the console.
                    total = resultSet.getRow();
                    done++;

                    //Closing the resultSet as we're done with it.
                    resultSet.close();
                }
            }

            //Closing the connection. This also saves the database from memory to disc.
            connection.close();

            //Feedbacking the console.
            Bukkit.getConsoleSender().sendMessage(pluginTag + ChatColor.AQUA + " Finished importing the groups.");
            Bukkit.getConsoleSender().sendMessage(pluginTag + ChatColor.AQUA + " Imported " + ChatColor.YELLOW + done + ChatColor.AQUA + " out of " + ChatColor.YELLOW + total + ChatColor.AQUA + " users.");

        //Catching any SQLException's that may have thrown while connecting.
        } catch (SQLException e) {

            //Informing the server console about this exception that has been thrown.
            System.out.println(e.getMessage());
        }
    }

    /**
     * Saves the groups from memory to database.
     *
     * @param users: The groups to save.
     */
    public static void saveUsers(final HashSet<User> users) {
        try {

            //Connecting to the database.
            final Connection connection = DriverManager.getConnection(jdbcUrl);

            //Clearing the main users table.
            connection.prepareStatement("DELETE FROM users").executeUpdate();

            //Iterating over all the user tables.
            for (int i = 1; i <= AdvancedCommunication.getInstance().getConfig().getInt("user-tables"); i++) {
                //Clearing the "users + i" table.
                connection.prepareStatement("DELETE FROM users" + i).executeUpdate();
            }

            //The amount of users saved/rows affected.
            int savedUsers = 0;

            //The table to save the user to.
            int table = 0;
            //Iterating over all the users that will be saved.
            for (final User user : users) {

                //Saving the user to the main users table.
                connection.prepareStatement("INSERT INTO users (uuid) VALUES ('" + user.getPlayer() + "')").executeUpdate();

                //Increasing the table.
                table++;

                //Iterating over the player's groups.
                for (final Map.Entry<String, UUID> entry :  user.getGroups().entrySet()) {
                    //Getting the Group we're at.
                    final Group group = DataStorage.groups.get(entry.getValue());

                    //Getting if the user is an admin of the group and saving this boolean to a String.
                    final String isAdmin = group.getMembers().get(user.getUUID()) ? "T" : "F";

                    //Saving this entry.
                    savedUsers += connection.prepareStatement("INSERT INTO users" + table + " (userUUID,groupUUID,isAdmin) VALUES ('" + user.getPlayer().toString() + "','" + group.getUUID().toString() + "','" + isAdmin + "')").executeUpdate();
                }

                //Checking if this is the max value table can get, if so we reset it to 0 for cycle to continue.
                if (table == AdvancedCommunication.getInstance().getConfig().getInt("user-tables")) {
                    table = 0;
                }
            }

            //Closing the connection. This also saves the database from memory to disc.
            connection.close();

            //Informing the server console.
            Bukkit.getConsoleSender().sendMessage(DataStorage.pluginTag + ChatColor.GREEN + " Successfully saved " + ChatColor.YELLOW + savedUsers + ChatColor.GREEN + " out of " + ChatColor.YELLOW + users.size() + ChatColor.GREEN + " queued.");

        //Catching any SQLException's that may have thrown while connecting.
        } catch (SQLException e) {

            //Informing the server console about this exception that has been thrown.
            System.out.println(e.getMessage());
        }
    }

    /**
     * Purges/Deletes users from the database.
     *
     * @param users: The users to delete.
     */
    public static void purgeUsers(final HashSet<User> users) {
        try {

            //Connecting to the database.
            final Connection connection = DriverManager.getConnection(jdbcUrl);

            //The amount of users purged/rows affected.
            int purgedUsers = 0;

            //Iterating over all the users that will be purged.
            for (final User user : users) {

                //Deleting the user from the main users table.
                purgedUsers += connection.prepareStatement("DELETE FROM users WHERE userUUID='" + user.getPlayer().toString() + "'").executeUpdate();

                //Deleting the user from the user tables.
                for (int i = 1; i <= AdvancedCommunication.getInstance().getConfig().getInt("user-tables"); i++) {
                    //Deleting this user from this users table.
                    connection.prepareStatement("DELETE FROM users" + i + " WHERE userUUID='" + user.getPlayer().toString() + "'").executeUpdate();
                }
            }

            //Closing the connection. This also saves the database from memory to disc.
            connection.close();

            //Informing the server console.
            Bukkit.getConsoleSender().sendMessage(DataStorage.pluginTag + ChatColor.GREEN + " Successfully purged " + ChatColor.YELLOW + purgedUsers + ChatColor.GREEN + " out of " + ChatColor.YELLOW + users.size() + ChatColor.GREEN + " queued.");

        //Catching any SQLException's that may have thrown while connecting.
        } catch (SQLException e) {

            //Informing the server console about this exception that has been thrown.
            System.out.println(e.getMessage());
        }
    }

    /**
     * Imports the groups in the database to DataStorage.
     */
    private static void importGroups() {
        try {
            //Getting the pluginTag to ease my work.
            final String pluginTag = DataStorage.pluginTag;

            //Feedbacking the console.
            Bukkit.getConsoleSender().sendMessage(pluginTag + ChatColor.AQUA + " Starting to import groups from database.");

            //Connecting to the database.
            final Connection connection = DriverManager.getConnection(jdbcUrl);

            //Querying the groups from the database
            final ResultSet resultSet = connection.prepareStatement("SELECT * FROM groups").executeQuery();

            //Creating the integers that we'll use to feedback console.
            int total = 0;
            int done = 0;
            //Iterating over the groups got from the database.
            while (resultSet.next()) {

                //Getting the group information in the row of the group.
                final UUID uuid = UUID.fromString(resultSet.getBlob("uuid").toString());

                final String name = resultSet.getString("name");
                final String description = resultSet.getString("description");

                boolean sendMessage = false;
                boolean editInfo = false;
                boolean slowdown = false;

                final char[] settings = resultSet.getString("settings").toCharArray();

                //Getting the settings.
                for (int i = 0; i < 3; i++) {
                    switch (i) {
                        case 0:
                            if (settings[i] == 'T' || settings[i] == 't') sendMessage = true;
                            break;
                        case 1:
                            if (settings[i] == 'T' || settings[i] == 't') editInfo = true;
                            break;
                        case 2:
                            if (settings[i] == 'T' || settings[i] == 't') slowdown = true;
                            break;
                    }
                }

                //Getting the members
                final HashMap<UUID, Boolean> members = new HashMap<>();

                final long createdTime = resultSet.getLong("creationTime");
                final long lastUpdate = resultSet.getLong("lastUpdate");

                //Creating the Group from the values imported.
                final Group group = new Group(uuid, name, description, sendMessage, editInfo, slowdown, members, createdTime, lastUpdate);
                //Adding the group to DataStorage.
                DataStorage.addGroup(group);

                total = resultSet.getRow();
                done++;
            }

            //Feedbacking the console.
            Bukkit.getConsoleSender().sendMessage(pluginTag + ChatColor.AQUA + " Finished importing the groups.");
            Bukkit.getConsoleSender().sendMessage(pluginTag + ChatColor.AQUA + " Imported " + ChatColor.YELLOW + done + ChatColor.AQUA + " out of " + ChatColor.YELLOW + total + ChatColor.AQUA + " groups.");

            //We're done with importing, now tidying up.
            resultSet.close();
            connection.close();

        //Catching any SQLException's that may have thrown while connecting.
        } catch (SQLException e) {

            //Informing the server console about this exception that has been thrown.
            System.out.println(e.getMessage());
        }
    }

    /**
     * Saves the groups from memory to database.
     *
     * @param groups: The groups to save.
     */
    public static void saveGroups(final HashSet<Group> groups) {
        try {

            //Connecting to the database.
            final Connection connection = DriverManager.getConnection(jdbcUrl);

            //Clearing the groups table.
            connection.prepareStatement("DELETE FROM groups").executeUpdate();

            //The amount of groups purged/rows affected.
            int purgedGroups = 0;

            //Iterating over all the groups that will be saved.
            for (final Group group : groups) {

                //Starting the sql.
                final StringBuilder sql = new StringBuilder("INSERT INTO groups (uuid,name,description,settings,creationTime,lastUpdate) VALUES ('" + group.getUUID().toString() + "','" + group.getName() + "','" + group.getDescription() + "','");

                //Adding the sendMessages setting to the sql.
                if (group.isSendMessages()) sql.append("T");
                else sql.append("F");

                //Adding the editInfo setting to the sql.
                if (group.isEditInfo()) sql.append("T");
                else sql.append("F");

                //Adding the slowdown setting to the sql.
                if (group.isInSlowdown()) sql.append("T',");
                else sql.append("F',");

                //Adding the timestamps to the sql.
                sql.append(group.getCreatedTime())
                        .append(",")
                        .append(group.getLastUpdate())
                        .append(")");

                //Saving the group to the database.
                purgedGroups += connection.prepareStatement(sql.toString()).executeUpdate();
            }

            //Closing the connection. This also saves the database from memory to disc.
            connection.close();

            //Informing the server console.
            Bukkit.getConsoleSender().sendMessage(DataStorage.pluginTag + ChatColor.GREEN + " Successfully saved " + ChatColor.YELLOW + purgedGroups + ChatColor.GREEN + " out of " + ChatColor.YELLOW + groups.size() + ChatColor.GREEN + " queued.");

        //Catching any SQLException's that may have thrown while connecting.
        } catch (SQLException e) {

            //Informing the server console about this exception that has been thrown.
            System.out.println(e.getMessage());
        }
    }

    /**
     * Purges/Deletes groups from the database.
     *
     * @param groups: Groups to delete.
     */
    public static void purgeGroups(final HashSet<Group> groups) {
        try {

            //Connecting to the database.
            final Connection connection = DriverManager.getConnection(jdbcUrl);

            //The amount of groups purged/rows affected.
            int purgedGroups = 0;

            //Iterating over all the groups that will be purged.
            for (final Group group : groups) {

                //Deleting the group from database.
                purgedGroups += connection.prepareStatement("DELETE FROM groups WHERE uuid='" + group.getUUID().toString() + "'").executeUpdate();
            }

            //Closing the connection. This also saves the database from memory to disc.
            connection.close();

            //Informing the server console.
            Bukkit.getConsoleSender().sendMessage(DataStorage.pluginTag + ChatColor.GREEN + " Successfully purged " + ChatColor.YELLOW + purgedGroups + ChatColor.GREEN + " out of " + ChatColor.YELLOW + groups.size() + ChatColor.GREEN + " queued.");

        //Catching any SQLException's that may have thrown while connecting.
        } catch (SQLException e) {

            //Informing the server console about this exception that has been thrown.
            System.out.println(e.getMessage());
        }
    }

    /**
     * Purges the local cache.
     */
    public static void purgeLocalCache() {

        jdbcUrl = null;

    }
}