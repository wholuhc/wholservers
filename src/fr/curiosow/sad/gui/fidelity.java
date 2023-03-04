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
import java.util.UUID;

public class fidelity implements OpenOtherGUI.OpenNewGUI
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
        if(getIfInFidelity(p.getUniqueId()) != null)
        {

            Inventory inv = Bukkit.createInventory(null, 18, messages.getString("menu-fidelity-name").replace("&", "§"));

            int f = Integer.parseInt(getFidelity(p.getUniqueId()));
            if(f==0)
            {
                inv.setItem(4, FastItemStack.create(Material.BARRIER, messages.getString("menu-fidelity-noPoint").replace("&", "§"), 1));
            } else
            {
                int i = 0;
                while (i < f && i < 18)
                {
                    inv.setItem(i, FastItemStack.create(Material.GOLD_BLOCK, messages.getString("menu-fidelity-point-name").replace("&", "§"), 1));
                    i++;
                }

                if(f >= configuration.getInt("GiveAnHostWhenHit"))
                {
                    inv.setItem(i-1, FastItemStack.create(Material.EMERALD_BLOCK, messages.getString("menu-fidelity-pointWin-name").replace("&", "§"), 1));
                }

            }

            p.openInventory(inv);

        } else
        {
            final PreparedStatement insertInFidelity = connection.prepareStatement("INSERT INTO `sad_fidelitybyuuid` (UUID, fidelity) VALUES (\"" + p.getUniqueId().toString() + "\", \"0\")");
            insertInFidelity.executeUpdate();
            p.sendMessage(messages.getString("menu-fidelity-fistTimeTry").replace("&", "§"));
        }
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
