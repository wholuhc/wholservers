package fr.curiosow.sad.commands;

import fr.curiosow.sad.gui.hosts;
import fr.curiosow.sad.main;
import fr.curiosow.sad.methods.OpenOtherGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.sql.SQLException;

public class CommandHost implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args)
    {

        if(sender instanceof Player)
        {
            Player p = (Player) sender;

            final File file = new File(main.getInstance().getDataFolder(), "messages.yml");
            final YamlConfiguration messages = YamlConfiguration.loadConfiguration(file);
            try
            {
                if(!main.getInstance().getDatabaseManager().getGradeConnexion().getConnexion().isClosed())
                {
                    OpenOtherGUI.OpenAnOtherGUI(p, new hosts());
                } else p.sendMessage(messages.getString("menu-not-connected").replace("&", "§"));

            } catch (SQLException e)
            {
                p.sendMessage(messages.getString("menu-not-connected").replace("&", "§"));
                e.printStackTrace();
            }


        }

        return false;
    }
}
