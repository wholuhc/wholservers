package fr.curiosow.sad.methods;

import fr.curiosow.sad.database.DbConnexion;
import fr.curiosow.sad.main;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class deleteHost
{

    public static void process(Player p, String id)
    {
        final File fileMessages = new File(main.getInstance().getDataFolder(), "messages.yml");
        final YamlConfiguration messages = YamlConfiguration.loadConfiguration(fileMessages);
        p.sendMessage(messages.getString("menu-manage-tryToDelete").replace("&", "§"));
        final DbConnexion gradeConnexion = main.getInstance().getDatabaseManager().getGradeConnexion();
        try
        {

            final Connection connection = gradeConnexion.getConnexion();

            final PreparedStatement delete1 = connection.prepareStatement("DELETE FROM sad_gamejointype WHERE ID = " + id);
            delete1.executeUpdate();

            final PreparedStatement delete2 = connection.prepareStatement("DELETE FROM sad_gamenamebyid WHERE ID = " + id);
            delete2.executeUpdate();

            final PreparedStatement delete3 = connection.prepareStatement("DELETE FROM sad_maxplayers WHERE ID = " + id);
            delete3.executeUpdate();

            final PreparedStatement delete4 = connection.prepareStatement("DELETE FROM sad_idgameplayer WHERE ID = " + id);
            delete4.executeUpdate();

            final PreparedStatement delete5 = connection.prepareStatement("DELETE FROM sad_whitelistbyid WHERE ID = " + id);
            delete5.executeUpdate();

            final PreparedStatement delete6 = connection.prepareStatement("DELETE FROM sad_gamemodesbyid WHERE ID = " + id);
            delete6.executeUpdate();

            final PreparedStatement delete7 = connection.prepareStatement("DELETE FROM sad_scenariosbyid WHERE ID = " + id);
            delete7.executeUpdate();

            p.sendMessage(messages.getString("menu-manage-deleteSuccess").replace("&", "§"));

        } catch (SQLException e)
        {
            p.sendMessage(messages.getString("menu-manage-deleteError").replace("&", "§"));
            e.printStackTrace();
        }
    }

}
