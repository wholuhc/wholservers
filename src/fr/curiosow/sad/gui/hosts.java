package fr.curiosow.sad.gui;

import com.google.common.collect.Lists;
import fr.curiosow.sad.database.DbConnexion;
import fr.curiosow.sad.main;
import fr.curiosow.sad.methods.FastItemStack;
import fr.curiosow.sad.methods.OpenOtherGUI;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.File;
import java.sql.*;
import java.util.*;

public class hosts implements OpenOtherGUI.OpenNewGUI
{

    public static String Type;

    @Override
    public void executeFunction(Player p)
    {
        final File fileMessages = new File(main.getInstance().getDataFolder(), "messages.yml");
        final YamlConfiguration messages = YamlConfiguration.loadConfiguration(fileMessages);

        final File fileConfig = new File(main.getInstance().getDataFolder(), "configuration.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(fileConfig);

        int nbrIDINT = Integer.parseInt(getID());

        Bukkit.getScheduler().scheduleSyncDelayedTask(main.getInstance(), new Runnable()
        {

            @Override
            public void run()
            {

                Inventory inv = Bukkit.createInventory(null, 45, messages.getString("menu-host-name").replace("&", "§"));

                /*
                 *       ORANGE GLASSES
                 */

                ItemStack ivoid = new ItemStack(Material.STAINED_GLASS_PANE);
                ItemMeta vMeta = ivoid.getItemMeta();
                vMeta.setDisplayName("§8");
                ivoid.setDurability((short) (15));
                ivoid.setItemMeta(vMeta);
                inv.setItem(0, ivoid);
                inv.setItem(1, ivoid);
                inv.setItem(2, ivoid);
                inv.setItem(3, ivoid);
                inv.setItem(4, ivoid);
                inv.setItem(5, ivoid);
                inv.setItem(6, ivoid);
                inv.setItem(7, ivoid);
                inv.setItem(8, ivoid);
                inv.setItem(9, ivoid);
                inv.setItem(17, ivoid);
                inv.setItem(18, ivoid);
                inv.setItem(27, ivoid);
                inv.setItem(26, ivoid);
                inv.setItem(35, ivoid);
                inv.setItem(37, ivoid);
                inv.setItem(38, ivoid);
                inv.setItem(39, ivoid);
                inv.setItem(41, ivoid);
                inv.setItem(42, ivoid);
                inv.setItem(43, ivoid);
                inv.setItem(44, ivoid);

                ItemStack refresh = new ItemStack(Material.INK_SACK);
                ItemMeta rMeta = refresh.getItemMeta();
                rMeta.setDisplayName(messages.getString("menu-host-update").replace("&", "§"));
                refresh.setDurability((short) (15));
                refresh.setItemMeta(rMeta);
                inv.setItem(40, refresh);

                if(p.hasPermission(configuration.getString("PermissionNeeded")))
                {

                    ItemStack createuhc = new ItemStack(Material.BANNER);
                    BannerMeta cuMeta = (BannerMeta)createuhc.getItemMeta();
                    cuMeta.setDisplayName(messages.getString("menu-host-createHost").replace("&", "§"));
                    cuMeta.setBaseColor(DyeColor.LIME);
                    cuMeta.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRAIGHT_CROSS));
                    cuMeta.addPattern(new Pattern(DyeColor.LIME, PatternType.BORDER));
                    cuMeta.addPattern(new Pattern(DyeColor.LIME, PatternType.STRIPE_BOTTOM));
                    cuMeta.addPattern(new Pattern(DyeColor.LIME, PatternType.STRIPE_TOP));
                    cuMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
                    ArrayList<String> lb = new ArrayList<>(messages.getStringList("menu-host-loreCreateHost"));
                    ArrayList<String> lFninal = new ArrayList<>();
                    for (int i = 0; i < lb.size(); i++)
                    {
                        lFninal.add(lb.get(i).replace("&", "§"));
                    }
                    cuMeta.setLore(lFninal);
                    createuhc.setItemMeta(cuMeta);
                    inv.setItem(36, createuhc);
                } else {

                    inv.setItem(36, ivoid);

                }

                if(nbrIDINT == 0)
                {
                    inv.setItem(22, FastItemStack.create(Material.BARRIER, messages.getString("menu-host-anyGameFound").replace("&", "§"), 1));
                } else
                {
                    if(nbrIDINT == 1)
                    {
                        try
                        {
                            inv.setItem(22, returnGame("1", p));
                        } catch (SQLException e)
                        {
                            e.printStackTrace();
                        }
                    } else  if(nbrIDINT == 2)
                    {
                        try
                        {
                            inv.setItem(21, returnGame("1", p));
                            inv.setItem(23, returnGame("2", p));
                        } catch (SQLException e)
                        {
                            e.printStackTrace();
                        }
                    } else  if(nbrIDINT == 3)
                    {
                        try
                        {
                            inv.setItem(21, returnGame("1", p));
                            inv.setItem(22, returnGame("2", p));
                            inv.setItem(23, returnGame("3", p));
                        } catch (SQLException e)
                        {
                            e.printStackTrace();
                        }
                    } else  if(nbrIDINT == 4)
                    {
                        try
                        {
                            inv.setItem(20, returnGame("1", p));
                            inv.setItem(21, returnGame("2", p));
                            inv.setItem(23, returnGame("3", p));
                            inv.setItem(23, returnGame("4", p));
                        } catch (SQLException e)
                        {
                            e.printStackTrace();
                        }
                    } else  if(nbrIDINT == 5)
                    {
                        try
                        {
                            inv.setItem(20, returnGame("1", p));
                            inv.setItem(21, returnGame("2", p));
                            inv.setItem(22, returnGame("3", p));
                            inv.setItem(23, returnGame("4", p));
                            inv.setItem(24, returnGame("5", p));
                        } catch (SQLException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
                p.openInventory(inv);

            }
        }, 2);
    }

    public static ItemStack returnGame(String id, Player p) throws SQLException
    {
        final File fileMessages = new File(main.getInstance().getDataFolder(), "messages.yml");
        final YamlConfiguration messages = YamlConfiguration.loadConfiguration(fileMessages);

        final File fileConfig = new File(main.getInstance().getDataFolder(), "configuration.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(fileConfig);
        String playeringame1 = PlaceholderAPI.setPlaceholders(p, "%bungee_UHC_" + id + "%");

        Type = null;
        getAccessByID(id);
        String TypeVisual = null;
        if(Type.toString().equalsIgnoreCase("WHITELIST")) TypeVisual = messages.getString("menu-host-status-whitelist").replace("&", "§");
        if(Type.toString().equalsIgnoreCase("ACCESS")) TypeVisual = messages.getString("menu-host-status-open").replace("&", "§");
        if(Type.toString().equalsIgnoreCase("INGAME")) TypeVisual = messages.getString("menu-host-status-ingame").replace("&", "§");
        ItemStack host1;
        if(configuration.getString("ItemOfGames").equalsIgnoreCase("HeadPlayer"))
        {
            host1 = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            SkullMeta host1Meta = (SkullMeta) host1.getItemMeta();
            host1Meta.setOwner(getHosterByID(id));

            host1Meta.setDisplayName(messages.getString("menu-host-display-id").replace("&", "§") + id);
            ArrayList<String> lb = new ArrayList<>(messages.getStringList("menu-host-loresOfGame"));
            ArrayList<String> lFinalWithoutEt = new ArrayList<>();
            ArrayList<String> lFinalState = new ArrayList<>();
            for (int i = 0; i < lb.size(); i++)
            {
                lFinalWithoutEt.add(lb.get(i).replace("&", "§").replace("{gameName}", getGameNameByID(id)).replace("{host}", getHosterByID(id)).replace("{hostGameMode}", getGameModeByID(id)).replace("&","§").replace("{players}", playeringame1).replace("{slots}", getMaxPlayerByID(id)).replace("{gameState}", TypeVisual));
            }

            if(getScenariosByID(id).equalsIgnoreCase("null") || getScenariosByID(id).equalsIgnoreCase(" "))
            {
                for (int i = 0; i < lFinalWithoutEt.size(); i++)
                {
                    lFinalState.add(lFinalWithoutEt.get(i).replace("{scenarios}", messages.getString("menu-host-noHasScenarios").replace("&", "§")));
                }
            } else
            {
                String scenarios = getScenariosByID(id);
                for (int i = 0; i < lFinalWithoutEt.size(); i++)
                {
                    lFinalState.add(lFinalWithoutEt.get(i).replace("{scenarios}", messages.getString("menu-host-display-scenario").replace("&", "§").replace("{scenario}", scenarios)));
                }

            }

            host1Meta.setLore(lFinalState);
            host1.setItemMeta(host1Meta);

        } else
        {
            HashMap<String, Material> mat = new HashMap<>();
            for(Material m : Material.values())
            {
                mat.put(m.toString(), m);
            }
            host1 = new ItemStack(mat.get(configuration.getString("ItemOfGames")), 1);
            ItemMeta host1Meta = host1.getItemMeta();

            host1Meta.setDisplayName(messages.getString("menu-host-display-id").replace("&", "§") + id);
            ArrayList<String> lb = new ArrayList<>(messages.getStringList("menu-host-loresOfGame"));
            ArrayList<String> lFinalWithoutEt = new ArrayList<>();
            ArrayList<String> lFinalState = new ArrayList<>();


            for (int i = 0; i < lb.size(); i++)
            {
                lFinalWithoutEt.add(lb.get(i).replace("&", "§").replace("{gameName}", getGameNameByID(id)).replace("{host}", getHosterByID(id)).replace("{hostGameMode}", getGameModeByID(id)).replace("&","§").replace("{players}", playeringame1).replace("{slots}", getMaxPlayerByID(id)).replace("{gameState}", TypeVisual));
            }


            if(getScenariosByID(id).equalsIgnoreCase("null") || getScenariosByID(id).equalsIgnoreCase(" "))
            {
                for (int i = 0; i < lFinalWithoutEt.size(); i++)
                {
                    lFinalState.add(lFinalWithoutEt.get(i).replace("{scenarios}", messages.getString("menu-host-noHasScenarios").replace("&", "§")));
                }
            } else
            {
                String scenarios = getScenariosByID(id);
                for (int i = 0; i < lFinalWithoutEt.size(); i++)
                {
                    lFinalState.add(lFinalWithoutEt.get(i).replace("{scenarios}", messages.getString("menu-host-display-scenario").replace("&", "§").replace("{scenario}", scenarios)));
                }

            }

            host1Meta.setLore(lFinalState);
            host1.setItemMeta(host1Meta);

        }

        return host1;
    }

    /*
    SQL METHODS
    */

    public static String getScenariosByID(String ID)
    {
        final DbConnexion gradeConnexion = main.getInstance().getDatabaseManager().getGradeConnexion();
        try
        {
            final Connection connection = gradeConnexion.getConnexion();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT sad_scenariosbyid.ArrayList FROM sad_scenariosbyid WHERE ID = " + ID + "");
            final ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) return resultSet.getString("ArrayList");

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
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


    public static String getHosterByID(String ID)
    {

        final DbConnexion gradeConnexion = main.getInstance().getDatabaseManager().getGradeConnexion();
        try
        {
            final Connection connection = gradeConnexion.getConnexion();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT sad_idgameplayer.hostName FROM sad_idgameplayer WHERE ID = " + ID + "");
            final ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) return resultSet.getString("hostName");

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static String getGameModeByID(String ID)
    {

        final DbConnexion gradeConnexion = main.getInstance().getDatabaseManager().getGradeConnexion();
        try
        {
            final Connection connection = gradeConnexion.getConnexion();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT sad_gamemodesbyid.gameMode FROM sad_gamemodesbyid WHERE ID = " + ID + "");
            final ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) return resultSet.getString("gameMode");

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static String getMaxPlayerByID(String ID)
    {

        final DbConnexion gradeConnexion = main.getInstance().getDatabaseManager().getGradeConnexion();
        try
        {
            final Connection connection = gradeConnexion.getConnexion();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT sad_maxplayers.playerMax FROM sad_maxplayers WHERE ID = " + ID + "");
            final ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) return resultSet.getString("playerMax");

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static String getGameNameByID(String ID)
    {

        final DbConnexion gradeConnexion = main.getInstance().getDatabaseManager().getGradeConnexion();
        try
        {
            final Connection connection = gradeConnexion.getConnexion();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT sad_gamenamebyid.gameName FROM sad_gamenamebyid WHERE ID = \"" + ID + "\"");
            final ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) return resultSet.getString("gameName");

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static void getAccessByID(String ID) throws SQLException
    {

        final DbConnexion gradeConnexion = main.getInstance().getDatabaseManager().getGradeConnexion();
        try
        {
            final Connection connection = gradeConnexion.getConnexion();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT sad_gamejointype.TYPE FROM sad_gamejointype WHERE ID = \"" + ID + "\"");
            final ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) Type = resultSet.getString("TYPE");

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

}
