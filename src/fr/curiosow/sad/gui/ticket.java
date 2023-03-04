package fr.curiosow.sad.gui;

import fr.curiosow.sad.database.DbConnexion;
import fr.curiosow.sad.main;
import fr.curiosow.sad.methods.FastItemStack;
import fr.curiosow.sad.methods.OpenOtherGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class ticket implements OpenOtherGUI.OpenNewGUI
{
    @Override
    public void executeFunction(Player p) throws SQLException
    {

        final File fileMessages = new File(main.getInstance().getDataFolder(), "messages.yml");
        final YamlConfiguration messages = YamlConfiguration.loadConfiguration(fileMessages);

        final File fileConfiguration = new File(main.getInstance().getDataFolder(), "configuration.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(fileConfiguration);

        final DbConnexion gradeConnexion = main.getInstance().getDatabaseManager().getGradeConnexion();
        final Connection connection = gradeConnexion.getConnexion();

        if(getIfInTicket(p.getUniqueId()) != null)
        {
            Inventory inv = Bukkit.createInventory(null, 9, messages.getString("menu-ticket-name").replace("&", "§"));
            ArrayList<String> lb = new ArrayList<>(messages.getStringList("menu-ticket-buyItemLore"));
            ArrayList<String> lFninal = new ArrayList<>();
            for (int i = 0; i < lb.size(); i++)
            {
                lFninal.add(lb.get(i).replace("&", "§").replace("{ticket}", getTickets(p.getUniqueId())).replace("{price}", configuration.getInt("PriceOfAnHost") + ""));
            }
            inv.setItem(4, FastItemStack.create(Material.PAPER, messages.getString("menu-ticket-buyItemName").replace("&", "§"), 1, lFninal));

            p.openInventory(inv);

        } else
        {
            final PreparedStatement insertInFidelity = connection.prepareStatement("INSERT INTO `sad_ticketsbyuuid` (UUID, tickets) VALUES (\"" + p.getUniqueId().toString() + "\", \"0\")");
            insertInFidelity.executeUpdate();
            p.sendMessage(messages.getString("menu-ticket-fistTimeTry").replace("&", "§"));
        }


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

}
