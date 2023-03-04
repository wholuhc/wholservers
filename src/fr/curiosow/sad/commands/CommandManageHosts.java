package fr.curiosow.sad.commands;

import fr.curiosow.sad.gui.hosts;
import fr.curiosow.sad.gui.manage_main;
import fr.curiosow.sad.methods.OpenOtherGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandManageHosts implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args)
    {

        if(sender instanceof Player)
        {
            Player p = (Player) sender;

            OpenOtherGUI.OpenAnOtherGUI(p, new manage_main());

        }

        return false;
    }
}
