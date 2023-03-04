package fr.curiosow.sad.gui;

import fr.curiosow.sad.main;
import fr.curiosow.sad.methods.FastItemStack;
import fr.curiosow.sad.methods.OpenOtherGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;

public class manage_main implements OpenOtherGUI.OpenNewGUI
{

    @Override
    public void executeFunction(Player p)
    {
        final File fileMessages = new File(main.getInstance().getDataFolder(), "messages.yml");
        final YamlConfiguration messages = YamlConfiguration.loadConfiguration(fileMessages);

        final File fileConfig = new File(main.getInstance().getDataFolder(), "configuration.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(fileConfig);
        Inventory inv = Bukkit.createInventory(null, 45, messages.getString("menu-manage-name").replace("&", "§"));

        /*
         *       ORANGE GLASSES
         */

        ItemStack ivoid = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta vMeta = ivoid.getItemMeta();
        vMeta.setDisplayName("§8");
        ivoid.setDurability((short) (15));
        ivoid.setItemMeta(vMeta);
        inv.setItem(0, ivoid);
        inv.setItem(1, ivoid);
        inv.setItem(2, ivoid);
        inv.setItem(3, ivoid);
        inv.setItem(4, ivoid);
        inv.setItem(5, ivoid);
        inv.setItem(6, ivoid);
        inv.setItem(7, ivoid);
        inv.setItem(8, ivoid);
        inv.setItem(9, ivoid);
        inv.setItem(17, ivoid);
        inv.setItem(18, ivoid);
        inv.setItem(27, ivoid);
        inv.setItem(26, ivoid);
        inv.setItem(35, ivoid);
        inv.setItem(36, ivoid);
        inv.setItem(37, ivoid);
        inv.setItem(38, ivoid);
        inv.setItem(39, ivoid);
        inv.setItem(40, ivoid);
        inv.setItem(41, ivoid);
        inv.setItem(42, ivoid);
        inv.setItem(43, ivoid);
        inv.setItem(44, ivoid);

        if(configuration.getBoolean("ForceStopCreationHosts") == false) inv.setItem(21, FastItemStack.create(Material.REDSTONE_BLOCK, messages.getString("menu-manage-desactivated").replace("&", "§"), 0));
        else inv.setItem(21, FastItemStack.create(Material.EMERALD_BLOCK, messages.getString("menu-manage-reactivated").replace("&", "§"), 1));

        inv.setItem(23, FastItemStack.create(Material.CHEST, messages.getString("menu-manage-gamesList").replace("&", "§"), 1));

        p.openInventory(inv);

    }
}
