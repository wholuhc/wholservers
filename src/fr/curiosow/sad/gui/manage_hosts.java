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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class manage_hosts implements OpenOtherGUI.OpenNewGUI
{
    @Override
    public void executeFunction(Player p) throws SQLException
    {

        final File fileMessages = new File(main.getInstance().getDataFolder(), "messages.yml");
        final YamlConfiguration messages = YamlConfiguration.loadConfiguration(fileMessages);

        Inventory inv = Bukkit.createInventory(null, 18, messages.getString("menu-manage-gamesList").replace("&", "§"));

        if(Integer.parseInt(getID()) == 0)
        {
            inv.setItem(4, FastItemStack.create(Material.BARRIER, messages.getString("menu-manage-noHostsActive").replace("&", "§"), 1));
        } else
        {
            int last = 1;
            int lDisplay = 0;
            while (last <= Integer.parseInt(getID()))
            {
                ItemStack host1;
                host1 = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                SkullMeta host1Meta = (SkullMeta) host1.getItemMeta();
                host1Meta.setOwner(getHosterByID(last + ""));
                host1Meta.setDisplayName(messages.getString("menu-host-display-id").replace("&", "§") + last);
                host1Meta.setLore(Arrays.asList(messages.getString("menu-manage-nameOfTheHost").replace("&", "§").replace("{host}", getHosterByID(last + "")), "", messages.getString("menu-manage-connectToHost").replace("&", "§"), messages.getString("menu-manage-deleteTheHost").replace("&", "§")));
                host1.setItemMeta(host1Meta);
                inv.setItem(lDisplay, host1);
                last++;
                lDisplay++;
            }
        }
        p.openInventory(inv);

    }

    public static String getHosterByID(String ID)
    {

        final DbConnexion gradeConnexion = main.getInstance().getDatabaseManager().getGradeConnexion();
        try
        {
            final Connection connection = gradeConnexion.getConnexion();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT sad_idgameplayer.hostName FROM sad_idgameplayer WHERE ID = " + ID + "");
            final ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) return resultSet.getString("hostName");

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static String getID()
    {

        final DbConnexion gradeConnexion = main.getInstance().getDatabaseManager().getGradeConnexion();
        try
        {
            final Connection connection = gradeConnexion.getConnexion();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT Count(*) FROM sad_idgameplayer");
            final ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) return resultSet.getString("Count(*)");

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

}
