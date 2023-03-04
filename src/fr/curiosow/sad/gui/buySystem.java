package fr.curiosow.sad.gui;

import fr.curiosow.sad.database.DbConnexion;
import fr.curiosow.sad.main;
import fr.curiosow.sad.methods.FastItemStack;
import fr.curiosow.sad.methods.OpenOtherGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class buySystem implements OpenOtherGUI.OpenNewGUI
{
    @Override
    public void executeFunction(Player p)
    {
         final File fileMessages = new File(main.getInstance().getDataFolder(), "messages.yml");
         final YamlConfiguration messages = YamlConfiguration.loadConfiguration(fileMessages);

         final File fileConfig = new File(main.getInstance().getDataFolder(), "configuration.yml");
         final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(fileConfig);

         final File fileGamemodes = new File(main.getInstance().getDataFolder(), "gamemodes.yml");
         final YamlConfiguration gamemode = YamlConfiguration.loadConfiguration(fileGamemodes);
        Inventory inv = Bukkit.createInventory(null, 9, messages.getString("menu-buyHost-name").replace("&", "§"));
        if(configuration.getBoolean("ForceStopCreationHosts") == true)
        {
            ArrayList<String> lb = new ArrayList<>(messages.getStringList("menu-buyHost-loreDisableMessage"));
            ArrayList<String> lFninal = new ArrayList<>();
            for (int i = 0; i < lb.size(); i++)
            {
                lFninal.add(lb.get(i).replace("&", "§"));
            }
            inv.setItem(4, FastItemStack.create(Material.BARRIER, messages.getString("menu-buyHost-disableMessage").replace("&", "§"), 1, lFninal));
        } else
        {

            if(getCheckPlayerHasGame(p) != null)
            {
                ArrayList<String> lb = new ArrayList<>(messages.getStringList("menu-buyHost-loreAlreadyHaveHost"));
                ArrayList<String> lFninal = new ArrayList<>();
                for (int i = 0; i < lb.size(); i++)
                {
                    lFninal.add(lb.get(i).replace("&", "§"));
                }
                inv.setItem(4, FastItemStack.create(Material.BARRIER, messages.getString("menu-buyHost-alreadyHaveHost").replace("&", "§"), 1, lFninal));

            } else
            {
                int nbrIDINT = Integer.parseInt(getID());
                if(nbrIDINT == configuration.getInt("MaxHostsBetweens"))
                {
                    ArrayList<String> lb = new ArrayList<>(messages.getStringList("menu-buyHost-loreMaxHostsReached"));
                    ArrayList<String> lFninal = new ArrayList<>();
                    for (int i = 0; i < lb.size(); i++)
                    {
                        lFninal.add(lb.get(i).replace("&", "§"));
                    }
                    inv.setItem(4, FastItemStack.create(Material.BARRIER, messages.getString("menu-buyHost-maxHostsReached").replace("&", "§"), 1, lFninal));

                } else
                {
                    ConfigurationSection users = gamemode.getConfigurationSection("GameModes");
                    int last = 0;
                    HashMap<String, Material> mat = new HashMap<>();
                    for(Material m : Material.values())
                    {
                        mat.put(m.toString(), m);
                    }
                    for(String gm : users.getKeys(false))
                    {
                        ArrayList<String> lb = new ArrayList<>(messages.getStringList("menu-buyHost-loreGameModes"));
                        ArrayList<String> lFninal = new ArrayList<>();
                        for (int i = 0; i < lb.size(); i++)
                        {
                            lFninal.add(lb.get(i).replace("&", "§"));
                        }
                        Material materialFinal = mat.get(gamemode.getString("GameModes." + gm + ".ItemInSelector"));
                        String nameFinal = gamemode.getString("GameModes." + gm + ".CompleteName").replace("&", "§");
                        inv.setItem(last, FastItemStack.create(materialFinal, nameFinal, 1, lFninal));
                        last++;
                    }

                }

            }

        }
        p.openInventory(inv);
    }

    /*
        SQL
     */

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

    public static String getCheckPlayerHasGame(Player p)
    {

        final DbConnexion gradeConnexion = main.getInstance().getDatabaseManager().getGradeConnexion();
        try
        {
            final Connection connection = gradeConnexion.getConnexion();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT sad_idgameplayer.hostName FROM sad_idgameplayer WHERE hostName = \""+ p.getDisplayName() + "\"");
            final ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) return resultSet.getString("hostName");

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

}
