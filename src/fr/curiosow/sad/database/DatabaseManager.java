package fr.curiosow.sad.database;

import fr.curiosow.sad.main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.sql.SQLException;

public class DatabaseManager
{

    private DbConnexion gradeConnexion;

    public DatabaseManager()
    {
        final File file = new File(main.getInstance().getDataFolder(), "configuration.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        Bukkit.getConsoleSender().sendMessage("§8(§9WholServers§8) §eTrying to connect to the Database...");
        this.gradeConnexion = new DbConnexion(new DbCredentials(configuration.getString("Host"),configuration.getString("Username"),configuration.getString("Password"),configuration.getString("Database"),configuration.getInt("Port")));
    }

    public DbConnexion getGradeConnexion()
    {
        return gradeConnexion;
    }

    public void close()
    {
        try
        {
            this.gradeConnexion.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

}
