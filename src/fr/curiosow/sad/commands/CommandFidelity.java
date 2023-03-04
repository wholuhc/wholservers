package fr.curiosow.sad.commands;

import fr.curiosow.sad.gui.fidelity;
import fr.curiosow.sad.main;
import fr.curiosow.sad.methods.OpenOtherGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class CommandFidelity implements CommandExecutor
{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args)
    {
        final File fileMessages = new File(main.getInstance().getDataFolder(), "messages.yml");
        final YamlConfiguration messages = YamlConfiguration.loadConfiguration(fileMessages);

        final File fileConfig = new File(main.getInstance().getDataFolder(), "configuration.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(fileConfig);

        if(sender instanceof Player)
        {
            Player p = (Player) sender;

            if(configuration.getBoolean("FidelitySystem") == true)
            {
                OpenOtherGUI.OpenAnOtherGUI(p, new fidelity());
            } else p.sendMessage(messages.getString("menu-fidelity-disableMessage").replace("&", "§"));



        }

        return false;
    }
}
