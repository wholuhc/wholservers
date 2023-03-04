package fr.curiosow.sad.events;

import fr.curiosow.sad.database.DbConnexion;
import fr.curiosow.sad.gui.*;
import fr.curiosow.sad.main;
import fr.curiosow.sad.methods.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class onInventoryClick implements Listener
{

    static final File fileMessages = new File(main.getInstance().getDataFolder(), "messages.yml");
    static final YamlConfiguration messages = YamlConfiguration.loadConfiguration(fileMessages);

    static final File fileConfig = new File(main.getInstance().getDataFolder(), "configuration.yml");
    static final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(fileConfig);

    static final File fileGamemodes = new File(main.getInstance().getDataFolder(), "gamemodes.yml");
    static final YamlConfiguration gamemode = YamlConfiguration.loadConfiguration(fileGamemodes);

    @EventHandler
    public void onPlayerInventoryClick(InventoryClickEvent e) throws IOException, SQLException
    {
        Player p = (Player) e.getWhoClicked();
        if (e.getCurrentItem() == null)
            return;
        if (e.getCurrentItem().getItemMeta() == null)
        {
            return;
        }

        if(e.getInventory().getTitle().equals(messages.getString("menu-host-name").replace("&", "§")))
        {
            e.setCancelled(true);
            if (e.getCurrentItem().getItemMeta() != null)
            {
                if(e.getCurrentItem().getItemMeta().getDisplayName().equals(messages.getString("menu-host-createHost").replace("&", "§")))
                {
                    OpenOtherGUI.OpenAnOtherGUI(p, new buySystem());
                }
                if(e.getCurrentItem().getItemMeta().getDisplayName().equals(messages.getString("menu-host-update").replace("&", "§")))
                {
                    OpenOtherGUI.OpenAnOtherGUI(p, new hosts());
                }
                if(e.getCurrentItem().getItemMeta().getDisplayName().equals(messages.getString("menu-host-display-id").replace("&", "§") + "1"))
                {
                    p.closeInventory();
                    detectIfWhitelistandBan.execute(p, "1");
                }
                if(e.getCurrentItem().getItemMeta().getDisplayName().equals(messages.getString("menu-host-display-id").replace("&", "§") + "2"))
                {
                    p.closeInventory();
                    detectIfWhitelistandBan.execute(p, "2");
                }
                if(e.getCurrentItem().getItemMeta().getDisplayName().equals(messages.getString("menu-host-display-id").replace("&", "§") + "3"))
                {
                    p.closeInventory();
                    detectIfWhitelistandBan.execute(p, "3");
                }
                if(e.getCurrentItem().getItemMeta().getDisplayName().equals(messages.getString("menu-host-display-id").replace("&", "§") + "4"))
                {
                    p.closeInventory();
                    detectIfWhitelistandBan.execute(p, "4");
                }
                if(e.getCurrentItem().getItemMeta().getDisplayName().equals(messages.getString("menu-host-display-id").replace("&", "§") + "5"))
                {
                    p.closeInventory();
                    detectIfWhitelistandBan.execute(p, "5");
                }
            }
        }

        if(e.getInventory().getTitle().equals(messages.getString("menu-manage-gamesList").replace("&", "§")))
        {
            e.setCancelled(true);
            if (e.getCurrentItem().getItemMeta() != null)
            {
                if(e.getCurrentItem().getItemMeta().getDisplayName().equals(messages.getString("menu-host-display-id").replace("&", "§") + "1"))
                {
                    p.closeInventory();
                   if(e.isLeftClick()) connectToHost.process(p, "UHC_1");
                   else deleteHost.process(p,"1");
                }

                if(e.getCurrentItem().getItemMeta().getDisplayName().equals(messages.getString("menu-host-display-id").replace("&", "§") + "2"))
                {
                    p.closeInventory();
                    if(e.isLeftClick()) connectToHost.process(p, "UHC_2");
                    else deleteHost.process(p,"2");
                }

                if(e.getCurrentItem().getItemMeta().getDisplayName().equals(messages.getString("menu-host-display-id").replace("&", "§") + "3"))
                {
                    p.closeInventory();
                    if(e.isLeftClick()) connectToHost.process(p, "UHC_3");
                    else deleteHost.process(p,"3");
                }

                if(e.getCurrentItem().getItemMeta().getDisplayName().equals(messages.getString("menu-host-display-id").replace("&", "§") + "4"))
                {
                    p.closeInventory();
                    if(e.isLeftClick()) connectToHost.process(p, "UHC_4");
                    else deleteHost.process(p,"4");
                }

                if(e.getCurrentItem().getItemMeta().getDisplayName().equals(messages.getString("menu-host-display-id").replace("&", "§") + "5"))
                {
                    p.closeInventory();
                    if(e.isLeftClick()) connectToHost.process(p, "UHC_5");
                    else deleteHost.process(p,"5");
                }

            }
        }

        if(e.getInventory().getTitle().equals(messages.getString("menu-fidelity-name").replace("&", "§")))
        {
            e.setCancelled(true);
            if (e.getCurrentItem().getItemMeta() != null)
            {
                if (e.getCurrentItem().getItemMeta().getDisplayName().equals(messages.getString("menu-fidelity-pointWin-name").replace("&", "§")))
                {
                    p.closeInventory();

                    final DbConnexion gradeConnexion = main.getInstance().getDatabaseManager().getGradeConnexion();
                    final Connection connection = gradeConnexion.getConnexion();

                    final PreparedStatement removeFidelity = connection.prepareStatement("DELETE FROM sad_fidelitybyuuid WHERE UUID = \"" + p.getUniqueId() + "\"");
                    removeFidelity.executeUpdate();

                    final PreparedStatement addFidelity = connection.prepareStatement("INSERT INTO `sad_fidelitybyuuid` (UUID, fidelity) VALUES (\"" + p.getUniqueId().toString() + "\", \"0\")");
                    addFidelity.executeUpdate();

                    if(getIfInTickets(p.getUniqueId()) != null)
                    {
                        int i = Integer.parseInt(getTickets(p.getUniqueId())) + 1;

                        final PreparedStatement removeTicket = connection.prepareStatement("DELETE FROM sad_ticketsbyuuid WHERE UUID = \"" + p.getUniqueId() + "\"");
                        removeTicket.executeUpdate();

                        final PreparedStatement addTicket = connection.prepareStatement("INSERT INTO `sad_ticketsbyuuid` (UUID, tickets) VALUES (\"" + p.getUniqueId().toString() + "\", \"" + i + "\")");
                        addTicket.executeUpdate();
                    } else
                    {
                        final PreparedStatement addTicket = connection.prepareStatement("INSERT INTO `sad_ticketsbyuuid` (UUID, tickets) VALUES (\"" + p.getUniqueId().toString() + "\", \"1\")");
                        addTicket.executeUpdate();
                    }


                    p.sendMessage(messages.getString("menu-fidelity-getATicket").replace("&", "§"));
                }

            }
        }

        if(e.getInventory().getTitle().equals(messages.getString("menu-buyHost-name").replace("&", "§")))
        {
            e.setCancelled(true);

            if (e.getCurrentItem().getItemMeta() != null)
            {
                if (e.getCurrentItem().getItemMeta().getDisplayName().equals(messages.getString("menu-buyHost-disableMessage").replace("&", "§")))
                {
                    OpenOtherGUI.OpenAnOtherGUI(p, new hosts());
                } else
                {
                    HashMap<String, String> test = new HashMap<>();
                    ConfigurationSection users = gamemode.getConfigurationSection("GameModes");
                    for(String gm : users.getKeys(false)) test.put(users.getString(gm + ".CompleteName").replace("&", "§"), gm);
                    confirmBuy.executeFunction(p, test.get(e.getCurrentItem().getItemMeta().getDisplayName()));
                }
            }
        }

        if(e.getInventory().getTitle().equals(messages.getString("menu-confirmBuy-name").replace("&", "§")))
        {
            e.setCancelled(true);
            if (e.getCurrentItem().getItemMeta() != null)
            {
                if (e.getCurrentItem().getItemMeta().getDisplayName().equals(messages.getString("menu-confirmBuy-itemConfirm").replace("&", "§")))
                {
                    p.closeInventory();
                    if(configuration.getBoolean("UseTicketsSystem") == true)
                    {
                        if(getIfInTickets(p.getUniqueId()) != null)
                        {
                            if(Integer.parseInt(getTickets(p.getUniqueId())) != 0)
                            {
                                p.sendMessage(messages.getString("menu-confirmBuy-paymentConfirmation").replace("&", "§"));
                                createHost.processHost(p, confirmBuy.gameWanted.get(p.getUniqueId()), Integer.parseInt(getID()) + 1);

                            } else p.sendMessage(messages.getString("menu-confirmBuy-notEnough").replace("&", "§"));

                        } else p.sendMessage(messages.getString("menu-confirmBuy-notEnough").replace("&", "§"));
                    }

                     else
                     {
                         p.sendMessage(messages.getString("menu-confirmBuy-paymentConfirmation").replace("&", "§"));
                         createHost.processHost(p, confirmBuy.gameWanted.get(p.getUniqueId()), Integer.parseInt(getID()) + 1);
                     }

                }
            }
        }

        if(e.getInventory().getTitle().equals(messages.getString("menu-ticket-name").replace("&", "§")))
        {
            e.setCancelled(true);
            if (e.getCurrentItem().getItemMeta() != null)
            {
                if (e.getCurrentItem().getItemMeta().getDisplayName().equals(messages.getString("menu-ticket-buyItemName").replace("&", "§")))
                {
                    p.closeInventory();
                    buyTicket.trying(p);
                }
            }
        }

        if(e.getInventory().getTitle().equals(messages.getString("menu-manage-name").replace("&", "§")))
        {
            e.setCancelled(true);
            if (e.getCurrentItem().getItemMeta() != null)
            {
                if (e.getCurrentItem().getItemMeta().getDisplayName().equals(messages.getString("menu-manage-reactivated").replace("&", "§")))
                {
                    configuration.set("ForceStopCreationHosts", false);
                    configuration.save(fileConfig);
                    OpenOtherGUI.OpenAnOtherGUI(p, new manage_main());
                }



                if (e.getCurrentItem().getItemMeta().getDisplayName().equals(messages.getString("menu-manage-desactivated").replace("&", "§")))
                {
                    configuration.set("ForceStopCreationHosts", true);
                    configuration.save(fileConfig);
                    OpenOtherGUI.OpenAnOtherGUI(p, new manage_main());
                }

                if (e.getCurrentItem().getItemMeta().getDisplayName().equals(messages.getString("menu-manage-gamesList").replace("&", "§")))
                {
                    OpenOtherGUI.OpenAnOtherGUI(p, new manage_hosts());
                }

            }
        }

    }

    public static String getID()
    {

        final DbConnexion gradeConnexion = main.getInstance().getDatabaseManager().getGradeConnexion();
        try
        {
            final Connection connection = gradeConnexion.getConnexion();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT Count(*) FROM sad_idgameplayer");
            final ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) return resultSet.getString("Count(*)");

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static String getIfInTickets(UUID uuid)
    {

        final DbConnexion gradeConnexion = main.getInstance().getDatabaseManager().getGradeConnexion();
        try
        {
            final Connection connection = gradeConnexion.getConnexion();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT sad_ticketsbyuuid.UUID FROM sad_ticketsbyuuid WHERE UUID = \"" + uuid.toString() + "\"");
            final ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) return resultSet.getString("UUID");

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static String getTickets(UUID uuid)
    {

        final DbConnexion gradeConnexion = main.getInstance().getDatabaseManager().getGradeConnexion();
        try
        {
            final Connection connection = gradeConnexion.getConnexion();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT sad_ticketsbyuuid.tickets FROM sad_ticketsbyuuid WHERE UUID = \"" + uuid.toString() + "\" ");
            final ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) return resultSet.getString("tickets");

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

}
