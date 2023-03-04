package fr.curiosow.sad.database;

import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnexion
{

    private DbCredentials dbCredentials;
    private Connection connection;

    public DbConnexion(DbCredentials dbCredentials)
    {
        this.dbCredentials = dbCredentials;
        this.connect();
    }

    private void connect()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection(this.dbCredentials.toURL(), this.dbCredentials.getUser(), this.dbCredentials.getPass());

            Bukkit.getConsoleSender().sendMessage("§8(§9WholServers§8) §a§lConnexion to the Database success.");

        } catch(SQLException | ClassNotFoundException e)
        {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage("§8(§9WholServers§8) §c§lConnexion to the Database failed.");
        }
    }


    public void close() throws SQLException
    {
        if(this.connection != null)
        {
            if(!this.connection.isClosed())
            {
                this.connection.close();
            }
        }
    }

    public Connection getConnexion() throws SQLException
    {
        if(this.connection != null)
        {
            if(!this.connection.isClosed())
            {
                return this.connection;
            }
        }
        connect();
        return this.connection;
    }

}
