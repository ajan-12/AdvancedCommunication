package me.ajan12.advancedcommunication.Utilities.DatabaseUtils;

import me.ajan12.advancedcommunication.Objects.Group;
import me.ajan12.advancedcommunication.Utilities.DataStorage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;

public class SQLiteUtils {

    //The URL containing the database file path. This is needed for JDBC library.
    private static String jdbcUrl;

    /**
     * Setups the connection to the sqlite database.
     *
     * @param path: The path to the database file.
     */
    public static void setup(final String path) {

        //Initializing the JDBC URL to connect to the database.
        jdbcUrl = "jdbc:sqlite:" + path;

        try {

            //Connecting to the database.
            final Connection connection = connect();

            //Creating the groups table.
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS groups(uuid BLOB, name VARCHAR(32), description VARCHAR(256), settings VARCHAR(3), creationTime INTEGER, lastUpdate INTEGER) PRIMARY KEY uuid").execute();

            //Closing the connection.
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
            final Connection connection = connect();

            //Clearing the groups table.
            connection.prepareStatement("DELETE FROM groups").executeUpdate();

            //The amount of groups purged/rows affected.
            int purgedGroups = 0;

            //Iterating over all the groups that will be purged.
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
            Bukkit.getConsoleSender().sendMessage(DataStorage.pluginTag + ChatColor.GREEN + " Successfully purged " + ChatColor.YELLOW + purgedGroups + ChatColor.GREEN + " out of " + ChatColor.YELLOW + groups.size() + ChatColor.GREEN + " queued.");

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
            final Connection connection = connect();

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

    /** Connects to the sqlite database.
     *
     * @return : The Connection that has been made.
     * @throws SQLException : If the Connection wasn't made.
     */
    private static Connection connect() throws SQLException {

        //Initializing the connection.
        return DriverManager.getConnection(jdbcUrl);

    }
}