package fr.curiosow.sad.database;

import fr.curiosow.sad.main;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class tablesTester
{
    public static void process() throws SQLException
    {
        if(!main.getInstance().getDatabaseManager().getGradeConnexion().getConnexion().isClosed())
        {
            final DbConnexion gradeConnexion = main.getInstance().getDatabaseManager().getGradeConnexion();
            final Connection connection = gradeConnexion.getConnexion();

            final PreparedStatement gameJoinType = connection.prepareStatement("CREATE TABLE IF NOT EXISTS sad_gamejointype(ID TEXT, TYPE TEXT)");
            gameJoinType.executeUpdate();

            final PreparedStatement gameNameById = connection.prepareStatement("CREATE TABLE IF NOT EXISTS sad_gamenamebyid(ID TEXT, gameName TEXT)");
            gameNameById.executeUpdate();

            final PreparedStatement idGamePlayer = connection.prepareStatement("CREATE TABLE IF NOT EXISTS sad_idgameplayer(ID TEXT, hostName TEXT)");
            idGamePlayer.executeUpdate();

            final PreparedStatement maxPlayer = connection.prepareStatement("CREATE TABLE IF NOT EXISTS sad_maxplayers(ID TEXT, playerMax TEXT)");
            maxPlayer.executeUpdate();

            final PreparedStatement playerBannedByHost = connection.prepareStatement("CREATE TABLE IF NOT EXISTS sad_playersbannedbyhost(hostName TEXT, bannedName TEXT, reasonName TEXT)");
            playerBannedByHost.executeUpdate();

            final PreparedStatement whitelistById = connection.prepareStatement("CREATE TABLE IF NOT EXISTS sad_whitelistbyid(ID TEXT, playerName TEXT)");
            whitelistById.executeUpdate();

            final PreparedStatement gameModesById = connection.prepareStatement("CREATE TABLE IF NOT EXISTS sad_gamemodesbyid(ID TEXT, gameMode TEXT)");
            gameModesById.executeUpdate();


            final PreparedStatement coinsUUID = connection.prepareStatement("CREATE TABLE IF NOT EXISTS sad_coinsbyuuid(UUID TEXT, coins TEXT)");
            coinsUUID.executeUpdate();

            final PreparedStatement ticketsUUID = connection.prepareStatement("CREATE TABLE IF NOT EXISTS sad_ticketsbyuuid(UUID TEXT, tickets TEXT)");
            ticketsUUID.executeUpdate();

            final PreparedStatement fidelityUUID = connection.prepareStatement("CREATE TABLE IF NOT EXISTS sad_fidelitybyuuid(UUID TEXT, fidelity TEXT)");
            fidelityUUID.executeUpdate();

            final PreparedStatement scenariosById = connection.prepareStatement("CREATE TABLE IF NOT EXISTS sad_scenariosbyid(ID TEXT, ArrayList TEXT)");
            scenariosById.executeUpdate();

        }
    }

}
