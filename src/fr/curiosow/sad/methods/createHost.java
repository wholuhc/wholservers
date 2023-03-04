package fr.curiosow.sad.methods;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.curiosow.sad.database.DbConnexion;
import fr.curiosow.sad.discordWebhooks.DiscordWebhookGames;
import fr.curiosow.sad.gui.confirmBuy;
import fr.curiosow.sad.main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class createHost
{

    public static void processHost(Player p, String gamemode, int ID)
    {
         final File fileMessages = new File(main.getInstance().getDataFolder(), "messages.yml");
         final YamlConfiguration messages = YamlConfiguration.loadConfiguration(fileMessages);

         final File fileConfig = new File(main.getInstance().getDataFolder(), "configuration.yml");
         final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(fileConfig);

         final File fileGamemodes = new File(main.getInstance().getDataFolder(), "gamemodes.yml");
         final YamlConfiguration gamemodes = YamlConfiguration.loadConfiguration(fileGamemodes);
        confirmBuy.gameWanted.remove(p.getUniqueId());
        ConfigurationSection users = gamemodes.getConfigurationSection("GameModes");

        final DbConnexion gradeConnexion = main.getInstance().getDatabaseManager().getGradeConnexion();
        try
        {
            final Connection connection = gradeConnexion.getConnexion();

            /*
                STABLE MONEY SQL
             */

            if(configuration.getBoolean("UseTicketsSystem") == true)
            {

                if(configuration.getBoolean("FidelitySystem") == true)
                {
                    if(getIfInFidelity(p.getUniqueId()) != null)
                    {
                        int f = Integer.parseInt(getFidelity(p.getUniqueId())) + 1;
                        final PreparedStatement deleteTicketsPlayer = connection.prepareStatement("DELETE FROM `sad_fidelitybyuuid` WHERE UUID = \"" + p.getUniqueId() + "\"");
                        deleteTicketsPlayer.executeUpdate();

                        final PreparedStatement TicketsPlayer = connection.prepareStatement("INSERT INTO `sad_fidelitybyuuid` (UUID, fidelity) VALUES (" + "\"" + p.getUniqueId().toString() + "\", \"" + f + "\")");
                        TicketsPlayer.executeUpdate();
                    } else
                    {
                        final PreparedStatement TicketsPlayer = connection.prepareStatement("INSERT INTO `sad_fidelitybyuuid` (UUID, fidelity) VALUES (" + "\"" + p.getUniqueId().toString() + "\", \"" + 1 + "\")");
                        TicketsPlayer.executeUpdate();
                    }
                }

                int t = Integer.parseInt(getTickets(p.getUniqueId())) - 1;
                final PreparedStatement deleteTicketsPlayer = connection.prepareStatement("DELETE FROM `sad_ticketsbyuuid` WHERE UUID = \"" + p.getUniqueId() + "\"");
                deleteTicketsPlayer.executeUpdate();

                final PreparedStatement TicketsPlayer = connection.prepareStatement("INSERT INTO `sad_ticketsbyuuid` (UUID, tickets) VALUES (" + "\"" + p.getUniqueId() + "\", \"" + t + "\")");
                TicketsPlayer.executeUpdate();
            }

            /*
                CREATION HOST IN SQL
             */

            final PreparedStatement idGamePlayer = connection.prepareStatement("INSERT INTO `sad_idgameplayer` (ID, hostName) VALUES (" + "\"" + ID + "\", \"" + p.getDisplayName() + "\")");
            idGamePlayer.executeUpdate();

            final PreparedStatement maxPlayers = connection.prepareStatement("INSERT INTO `sad_maxplayers` (ID, playerMax) VALUES (" + "\"" + ID + "\", \"20\")");
            maxPlayers.executeUpdate();

            final PreparedStatement whitelistById = connection.prepareStatement("INSERT INTO `sad_whitelistbyid` (ID, playerName) VALUES (" + "\"" + ID + "\", \"" + p.getDisplayName() + "\")");
            whitelistById.executeUpdate();

            final PreparedStatement gameJoinType = connection.prepareStatement("INSERT INTO `sad_gamejointype` (ID, TYPE) VALUES (" + "\"" + ID + "\", \"WHITELIST\")");
            gameJoinType.executeUpdate();

            final PreparedStatement gameMode = connection.prepareStatement("INSERT INTO `sad_gamemodesbyid` (ID, gameMode) VALUES (" + "\"" + ID + "\", \"" + users.getString(gamemode + ".CompleteName") + "\")");
            gameMode.executeUpdate();

            final PreparedStatement gameNameById = connection.prepareStatement("INSERT INTO `sad_gamenamebyid` (ID, gameName) VALUES (" + "\"" + ID + "\", \""+ messages.getString("menu-host-nameOfAnHost").replace("&", "§").replace("{host}", p.getDisplayName()) + "\")");
            gameNameById.executeUpdate();

            final PreparedStatement scenariosById = connection.prepareStatement("INSERT INTO `sad_scenariosbyid` (ID, ArrayList) VALUES (" + "\"" + ID + "\", \"null\")");
            scenariosById.executeUpdate();

            if(configuration.getString("OSVersion").equals("windows")) processHostSecondlyWindows(p, gamemode, ID);
            else if (configuration.getString("OSVersion").equals("debian")) processHostSecondlyWindows(p, gamemode, ID);
            else p.sendMessage("&cSomething went wrong. Please contact support (Error code: 'OSVERSION').");

            p.sendMessage(messages.getString("menu-confirmBuy-paymentConfirmated").replace("&", "§"));
            p.sendMessage(messages.getString("menu-confirmBuy-giveAnFidelity").replace("&", "§"));

            /*if(configuration.getBoolean("sendMessageToDiscordWebhook") == true)
            {
                DiscordWebhookGames.execute("3997440", messages.getString("dw-host-title").replace("&", "§").replace("{host}", p.getDisplayName()), messages.getString("dw-host-textUnderTitle").replace("&", "§").replace("{hostGameMode}", users.getString(gamemode + ".DiscordWebhookName")));
            }*/

        } catch (SQLException e)
        {
            p.sendMessage(messages.getString("menu-confirmBuy-errorWhenStartingTheServer").replace("&", "§"));
            e.printStackTrace();
        }



    }

    private static void processHostSecondlyWindows(Player p, String gamemode, int ID)
    {
        final File fileMessages = new File(main.getInstance().getDataFolder(), "messages.yml");
        final YamlConfiguration messages = YamlConfiguration.loadConfiguration(fileMessages);

        final File fileConfig = new File(main.getInstance().getDataFolder(), "configuration.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(fileConfig);

        final File fileGamemodes = new File(main.getInstance().getDataFolder(), "gamemodes.yml");
        final YamlConfiguration gamemodes = YamlConfiguration.loadConfiguration(fileGamemodes);
        ConfigurationSection users = gamemodes.getConfigurationSection("GameModes");
        try
        {
            Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"robocopy " + configuration.getString("folderAtBase") + "GETTERJAR\\" + users.getString(gamemode + ".NameOfTheCopyFolder") + " " + configuration.getString("folderAtBase") + "UHC_" + ID + " /e & cd " + configuration.getString("folderAtBase") + "UHC_" + ID + "\\ & start.bat \"");
        }
        catch (Exception e)
        {
            System.out.println("Erreur au lancement du serveur UHC_" + ID + "");
            e.printStackTrace();
        }
        HostConnect(p, "UHC_"+ID);
    }

    private static void HostConnect(Player p, String server)
    {
        final File fileMessages = new File(main.getInstance().getDataFolder(), "messages.yml");
        final YamlConfiguration messages = YamlConfiguration.loadConfiguration(fileMessages);

        final File fileConfig = new File(main.getInstance().getDataFolder(), "configuration.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(fileConfig);

        final File fileGamemodes = new File(main.getInstance().getDataFolder(), "gamemodes.yml");
        final YamlConfiguration gamemodes = YamlConfiguration.loadConfiguration(fileGamemodes);
        Bukkit.getScheduler().scheduleSyncDelayedTask(main.getInstance(), new Runnable()
        {

            @Override
            public void run() {

                p.sendMessage(messages.getString("menu-confirmBuy-teleportingMessage").replace("&", "§"));
                ByteArrayDataOutput out = ByteStreams.newDataOutput();
                out.writeUTF("Connect");
                out.writeUTF(server);
                p.sendPluginMessage(main.getInstance(), "BungeeCord", out.toByteArray());

            }
        }, 20*configuration.getInt("TimeToStart"));

    }

    public static String getTickets(UUID uuid)
    {

        final DbConnexion gradeConnexion = main.getInstance().getDatabaseManager().getGradeConnexion();
        try
        {
            final Connection connection = gradeConnexion.getConnexion();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT sad_ticketsbyuuid.tickets FROM sad_ticketsbyuuid WHERE UUID = \"" + uuid.toString() + "\" ");
            final ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) return resultSet.getString("tickets");

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static String getFidelity(UUID uuid)
    {

        final DbConnexion gradeConnexion = main.getInstance().getDatabaseManager().getGradeConnexion();
        try
        {
            final Connection connection = gradeConnexion.getConnexion();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT sad_fidelitybyuuid.fidelity FROM sad_fidelitybyuuid WHERE UUID = \"" + uuid.toString() + "\" ");
            final ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) return resultSet.getString("fidelity");

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static String getIfInFidelity(UUID uuid)
    {

        final DbConnexion gradeConnexion = main.getInstance().getDatabaseManager().getGradeConnexion();
        try
        {
            final Connection connection = gradeConnexion.getConnexion();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT sad_fidelitybyuuid.UUID FROM sad_fidelitybyuuid WHERE UUID = \"" + uuid.toString() + "\" ");
            final ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) return resultSet.getString("UUID");

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

}
