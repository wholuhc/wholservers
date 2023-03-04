package fr.curiosow.sad;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import fr.curiosow.sad.commands.*;
import fr.curiosow.sad.database.DatabaseManager;
import fr.curiosow.sad.database.tablesTester;
import fr.curiosow.sad.events.onInventoryClick;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.sql.SQLException;

public class main extends JavaPlugin
{

    private static main instance;
    private DatabaseManager databaseManager;

    @Override
    public void onEnable()
    {
        instance = this;
        databaseManager = new DatabaseManager();
        try
        {
            tablesTester.process();
        } catch (SQLException e)
        {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage("§8(§9WholServers§8) §c§lConnexion to the Database failed.");
        }
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getPluginManager().registerEvents(new onInventoryClick(), this);
        getCommand("host").setExecutor(new CommandHost());
        getCommand("manageHosts").setExecutor(new CommandManageHosts());
        getCommand("fidelity").setExecutor(new CommandFidelity());
        getCommand("whol").setExecutor(new CommandWhol());
        getCommand("ticket").setExecutor(new CommandTicket());
        getCommand("wholjoin").setExecutor(new CommandWholJoin());
        Bukkit.getConsoleSender().sendMessage("§8(§9WholServers§8) §2Plugin is ready!");
    }

    @Override
    public void onDisable()
    {
        this.databaseManager.close();
        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this);
        this.getServer().getMessenger().unregisterIncomingPluginChannel(this);
    }

    public DatabaseManager getDatabaseManager() { return databaseManager; }
    public static main getInstance()
    {
        return instance;
    }

}
