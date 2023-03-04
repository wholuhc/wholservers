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
import java.util.UUID;

public class buyTicket
{

    public static void trying(Player p) throws SQLException
    {

        final File fileMessages = new File(main.getInstance().getDataFolder(), "messages.yml");
        final YamlConfiguration messages = YamlConfiguration.loadConfiguration(fileMessages);

        final File fileConfiguration = new File(main.getInstance().getDataFolder(), "configuration.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(fileConfiguration);

        final DbConnexion gradeConnexion = main.getInstance().getDatabaseManager().getGradeConnexion();
        final Connection connection = gradeConnexion.getConnexion();

        p.sendMessage(messages.getString("menu-ticket-checkAccountBalance").replace("&", "§"));

        int price = configuration.getInt("PriceOfAnHost");
        if(getIfInCoins(p.getUniqueId()) != null)
        {
            int balance = Integer.parseInt(getCoins(p.getUniqueId()));

            if(balance >= price)
            {

                int coinsFinal = balance - price;

                final PreparedStatement deleteCoinsPlayer = connection.prepareStatement("DELETE FROM `sad_coinsbyuuid` WHERE UUID = \"" + p.getUniqueId() + "\"");
                deleteCoinsPlayer.executeUpdate();

                final PreparedStatement CoinsPlayer = connection.prepareStatement("INSERT INTO `sad_coinsbyuuid` (UUID, coins) VALUES (" + "\"" + p.getUniqueId() + "\", \"" + coinsFinal + "\")");
                CoinsPlayer.executeUpdate();

                if(getIfInTicket(p.getUniqueId()) != null)
                {

                    int ticketFinal = Integer.parseInt(getTickets(p.getUniqueId())) + 1;

                    final PreparedStatement deleteTicketsPlayer = connection.prepareStatement("DELETE FROM `sad_ticketsbyuuid` WHERE UUID = \"" + p.getUniqueId() + "\"");
                    deleteTicketsPlayer.executeUpdate();

                    final PreparedStatement TicketsPlayer = connection.prepareStatement("INSERT INTO `sad_ticketsbyuuid` (UUID, tickets) VALUES (" + "\"" + p.getUniqueId() + "\", \"" + ticketFinal + "\")");
                    TicketsPlayer.executeUpdate();
                } else
                {
                    final PreparedStatement TicketsPlayer = connection.prepareStatement("INSERT INTO `sad_ticketsbyuuid` (UUID, tickets) VALUES (" + "\"" + p.getUniqueId() + "\", \"" + 1 + "\")");
                    TicketsPlayer.executeUpdate();
                }

                p.sendMessage(messages.getString("menu-ticket-buySuccesful").replace("&","§"));

            } else p.sendMessage(messages.getString("menu-ticket-notEnoughCoins").replace("&", "§"));

        } else p.sendMessage(messages.getString("menu-ticket-notEnoughCoins").replace("&", "§"));

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

    public static String getIfInTicket(UUID uuid)
    {

        final DbConnexion gradeConnexion = main.getInstance().getDatabaseManager().getGradeConnexion();
        try
        {
            final Connection connection = gradeConnexion.getConnexion();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT sad_ticketsbyuuid.UUID FROM sad_ticketsbyuuid WHERE UUID = \"" + uuid.toString() + "\" ");
            final ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) return resultSet.getString("UUID");

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static String getCoins(UUID uuid)
    {

        final DbConnexion gradeConnexion = main.getInstance().getDatabaseManager().getGradeConnexion();
        try
        {
            final Connection connection = gradeConnexion.getConnexion();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT sad_coinsbyuuid.coins FROM sad_coinsbyuuid WHERE UUID = \"" + uuid.toString() + "\" ");
            final ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) return resultSet.getString("coins");

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static String getIfInCoins(UUID uuid)
    {

        final DbConnexion gradeConnexion = main.getInstance().getDatabaseManager().getGradeConnexion();
        try
        {
            final Connection connection = gradeConnexion.getConnexion();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT sad_coinsbyuuid.UUID FROM sad_coinsbyuuid WHERE UUID = \"" + uuid.toString() + "\" ");
            final ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) return resultSet.getString("UUID");

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

}