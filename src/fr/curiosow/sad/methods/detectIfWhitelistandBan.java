package fr.curiosow.sad.methods;

import fr.curiosow.sad.database.DbConnexion;
import fr.curiosow.sad.main;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class detectIfWhitelistandBan
{

    public static void execute(Player p, String id)
    {

        final File fileMessages = new File(main.getInstance().getDataFolder(), "messages.yml");
        final YamlConfiguration messages = YamlConfiguration.loadConfiguration(fileMessages);


        // Get host by the id
        String host = getHostById(id);

        // Get state of the game
        String state = getStateById(id);

        if(state.equals("ACCESS"))
        {
            connectToHost.process(p, "UHC_" + id);
        } else if(state.equals("INGAME"))
        {
            p.sendMessage(messages.getString("jm-server-not-joinable").replace("&", "§").replace("{host}", host));
        } else if(state.equals("WHITELIST"))
        {

            // Get id of whitelsit
            String idWl = getIfWhitelistedByName(p.getDisplayName());

            if(idWl != null)
            {
                if(idWl.equals(id))
                {
                    connectToHost.process(p, "UHC_" + id);
                } else p.sendMessage(messages.getString("jm-server-whitelisted").replace("&", "§").replace("{host}", host));

            } else p.sendMessage(messages.getString("jm-server-whitelisted").replace("&", "§").replace("{host}", host));

        }


    }

    public static String getHostById(String id)
    {

        final DbConnexion gradeConnexion = main.getInstance().getDatabaseManager().getGradeConnexion();
        try
        {
            final Connection connection = gradeConnexion.getConnexion();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT sad_idgameplayer.hostName FROM sad_idgameplayer WHERE ID = \"" + id + "\"");
            final ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) return resultSet.getString("hostName");

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static String getStateById(String id)
    {

        final DbConnexion gradeConnexion = main.getInstance().getDatabaseManager().getGradeConnexion();
        try
        {
            final Connection connection = gradeConnexion.getConnexion();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT sad_gamejointype.TYPE FROM sad_gamejointype WHERE ID = \"" + id + "\"");
            final ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) return resultSet.getString("TYPE");

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static String getIfWhitelistedByName(String name)
    {

        final DbConnexion gradeConnexion = main.getInstance().getDatabaseManager().getGradeConnexion();
        try
        {
            final Connection connection = gradeConnexion.getConnexion();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT sad_whitelistbyid.ID FROM sad_whitelistbyid WHERE playerName = \"" + name + "\"");
            final ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) return resultSet.getString("ID");

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

}
