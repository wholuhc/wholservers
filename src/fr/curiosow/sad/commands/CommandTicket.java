package fr.curiosow.sad.commands;

import fr.curiosow.sad.gui.ticket;
import fr.curiosow.sad.main;
import fr.curiosow.sad.methods.OpenOtherGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class CommandTicket implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] arg)
    {
        final File fileMessages = new File(main.getInstance().getDataFolder(), "messages.yml");
        final YamlConfiguration messages = YamlConfiguration.loadConfiguration(fileMessages);

        final File fileConfig = new File(main.getInstance().getDataFolder(), "configuration.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(fileConfig);
        if(sender instanceof Player)
        {

            Player p = (Player) sender;

            if(!p.hasPermission("PermissionNeeded")) return false;

            if(configuration.getBoolean("UseTicketsSystem") == true) OpenOtherGUI.OpenAnOtherGUI(p, new ticket());
            else p.sendMessage(messages.getString("menu-ticket-disable").replace("&", "§"));


        }

        return false;
    }
}
