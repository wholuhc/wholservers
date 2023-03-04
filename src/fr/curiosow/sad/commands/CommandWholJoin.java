package fr.curiosow.sad.commands;

import fr.curiosow.sad.database.DbConnexion;
import fr.curiosow.sad.main;
import fr.curiosow.sad.methods.connectToHost;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class CommandWholJoin implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args)
    {

        if(sender instanceof Player)
        {
            Player p = (Player) sender;

            if(getIfInWhitelist(p) != null)
            {
                if(Integer.parseInt(getIfInWhitelist(p)) == Integer.parseInt(getIdByHoster(args[0])))
                {
                    connectToHost.process(p, "UHC_" + Integer.parseInt(getIdByHoster(args[0])));
                }
            }
        }

        return false;
    }

    public static String getIfInWhitelist(Player p)
    {

        final DbConnexion gradeConnexion = main.getInstance().getDatabaseManager().getGradeConnexion();
        try
        {
            final Connection connection = gradeConnexion.getConnexion();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT sad_whitelistbyid.ID FROM sad_whitelistbyid WHERE playerName = \"" + p.getDisplayName() + "\" ");
            final ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) return resultSet.getString("ID");

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static String getIdByHoster(String hoster)
    {

        final DbConnexion gradeConnexion = main.getInstance().getDatabaseManager().getGradeConnexion();
        try
        {
            final Connection connection = gradeConnexion.getConnexion();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT sad_idgameplayer.ID FROM sad_idgameplayer WHERE hostName = \"" + hoster + "\"");
            final ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) return resultSet.getString("ID");

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

}
