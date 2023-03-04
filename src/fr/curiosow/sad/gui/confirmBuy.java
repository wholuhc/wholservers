package fr.curiosow.sad.gui;

import fr.curiosow.sad.main;
import fr.curiosow.sad.methods.FastItemStack;
import fr.curiosow.sad.methods.OpenOtherGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class confirmBuy
{

    public static HashMap<UUID, String> gameWanted = new HashMap<>();

    public static void executeFunction(Player p, String gamemode) throws SQLException
    {
         final File fileMessages = new File(main.getInstance().getDataFolder(), "messages.yml");
         final YamlConfiguration messages = YamlConfiguration.loadConfiguration(fileMessages);

         final File fileConfig = new File(main.getInstance().getDataFolder(), "configuration.yml");
         final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(fileConfig);
        Bukkit.getScheduler().scheduleSyncDelayedTask(main.getInstance(), new Runnable()
        {

            @Override
            public void run()
            {
                gameWanted.put(p.getUniqueId(), gamemode);
                Inventory inv = Bukkit.createInventory(null, 9, messages.getString("menu-confirmBuy-name").replace("&", "§"));

                ArrayList<String> lb = new ArrayList<>(messages.getStringList("menu-confirmBuy-loreItemConfirm"));
                ArrayList<String> lFninal = new ArrayList<>();
                for (int i = 0; i < lb.size(); i++)
                {
                    lFninal.add(lb.get(i).replace("&", "§"));
                }
                inv.setItem(4, FastItemStack.create(Material.EMERALD, messages.getString("menu-confirmBuy-itemConfirm").replace("&", "§"), 1, lFninal));

                p.openInventory(inv);
            }
        }, 1);
    }
}
