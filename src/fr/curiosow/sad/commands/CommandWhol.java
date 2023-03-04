package fr.curiosow.sad.commands;

import fr.curiosow.sad.database.DbConnexion;
import fr.curiosow.sad.main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class CommandWhol implements CommandExecutor
{

    static final File fileMessages = new File(main.getInstance().getDataFolder(), "messages.yml");
    static final YamlConfiguration messages = YamlConfiguration.loadConfiguration(fileMessages);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args)
    {

        if(sender instanceof Player)
        {
            Player p = (Player) sender;

            if(args.length == 0 || args[0].equalsIgnoreCase("help"))
            {
                ArrayList<String> lb = new ArrayList<>(messages.getStringList("cmd-whol-help"));
                for (int i = 0; i < lb.size(); i++) p.sendMessage(lb.get(i).replace("&", "§"));
            } else
            {
                final DbConnexion gradeConnexion = main.getInstance().getDatabaseManager().getGradeConnexion();
                if(args[0].equalsIgnoreCase("coins"))
                {
                    if(args[1].equalsIgnoreCase("add"))
                    {
                        if(args.length == 4)
                        {
                            Player pl = Bukkit.getPlayer(args[2]);
                        if(getIfInCoins(pl.getUniqueId()) != null)
                        {
                            int c = Integer.parseInt(getCoins(pl.getUniqueId())) + Integer.parseInt(args[3]);
                            try
                            {
                                final Connection connection = gradeConnexion.getConnexion();
                                final PreparedStatement deleteTicketsPlayer = connection.prepareStatement("DELETE FROM `sad_coinsbyuuid` WHERE UUID = \"" + pl.getUniqueId() + "\"");
                                deleteTicketsPlayer.executeUpdate();

                                final PreparedStatement TicketsPlayer = connection.prepareStatement("INSERT INTO `sad_coinsbyuuid` (UUID, coins) VALUES (" + "\"" + pl.getUniqueId() + "\", \"" + c + "\")");
                                TicketsPlayer.executeUpdate();

                                p.sendMessage(messages.getString("cmd-whol-successfullyEdit").replace("&","§"));

                            } catch (SQLException e)
                            {
                                e.printStackTrace();
                            }
                        } else
                        {
                            int c = Integer.parseInt(getCoins(pl.getUniqueId())) + Integer.parseInt(args[3]);
                            try
                            {
                                final Connection connection = gradeConnexion.getConnexion();

                                final PreparedStatement TicketsPlayer = connection.prepareStatement("INSERT INTO `sad_coinsbyuuid` (UUID, coins) VALUES (" + "\"" + pl.getUniqueId() + "\", \"" + c + "\")");
                                TicketsPlayer.executeUpdate();

                                p.sendMessage(messages.getString("cmd-whol-successfullyEdit").replace("&","§"));

                            } catch (SQLException e)
                            {
                                e.printStackTrace();
                            }
                        }

                        } else p.sendMessage(messages.getString("cmd-whol-errorSyntax").replace("&", "§"));
                    }

                    if(args[1].equalsIgnoreCase("remove"))
                    {
                        if(args.length == 4)
                        {
                            Player pl = Bukkit.getPlayer(args[2]);
                            if(getIfInCoins(pl.getUniqueId()) != null)
                            {
                                int c = Integer.parseInt(getCoins(pl.getUniqueId())) - Integer.parseInt(args[3]);
                                try
                                {
                                    final Connection connection = gradeConnexion.getConnexion();
                                    final PreparedStatement deleteTicketsPlayer = connection.prepareStatement("DELETE FROM `sad_coinsbyuuid` WHERE UUID = \"" + pl.getUniqueId() + "\"");
                                    deleteTicketsPlayer.executeUpdate();

                                    final PreparedStatement TicketsPlayer = connection.prepareStatement("INSERT INTO `sad_coinsbyuuid` (UUID, coins) VALUES (" + "\"" + pl.getUniqueId() + "\", \"" + c + "\")");
                                    TicketsPlayer.executeUpdate();

                                    p.sendMessage(messages.getString("cmd-whol-successfullyEdit").replace("&","§"));

                                } catch (SQLException e)
                                {
                                    e.printStackTrace();
                                }
                            } else
                            {
                                int c = Integer.parseInt(getCoins(pl.getUniqueId())) - Integer.parseInt(args[3]);
                                try
                                {
                                    final Connection connection = gradeConnexion.getConnexion();

                                    final PreparedStatement TicketsPlayer = connection.prepareStatement("INSERT INTO `sad_coinsbyuuid` (UUID, coins) VALUES (" + "\"" + pl.getUniqueId() + "\", \"" + c + "\")");
                                    TicketsPlayer.executeUpdate();

                                    p.sendMessage(messages.getString("cmd-whol-successfullyEdit").replace("&","§"));

                                } catch (SQLException e)
                                {
                                    e.printStackTrace();
                                }
                            }

                        } else p.sendMessage(messages.getString("cmd-whol-errorSyntax").replace("&", "§"));
                    }

                    if(args[1].equalsIgnoreCase("set"))
                    {
                        if(args.length == 4)
                        {
                            Player pl = Bukkit.getPlayer(args[2]);
                            if(getIfInCoins(pl.getUniqueId()) != null)
                            {
                                int c = Integer.parseInt(args[3]);
                                try
                                {
                                    final Connection connection = gradeConnexion.getConnexion();
                                    final PreparedStatement deleteTicketsPlayer = connection.prepareStatement("DELETE FROM `sad_coinsbyuuid` WHERE UUID = \"" + pl.getUniqueId() + "\"");
                                    deleteTicketsPlayer.executeUpdate();

                                    final PreparedStatement TicketsPlayer = connection.prepareStatement("INSERT INTO `sad_coinsbyuuid` (UUID, coins) VALUES (" + "\"" + pl.getUniqueId() + "\", \"" + c + "\")");
                                    TicketsPlayer.executeUpdate();

                                    p.sendMessage(messages.getString("cmd-whol-successfullyEdit").replace("&","§"));

                                } catch (SQLException e)
                                {
                                    e.printStackTrace();
                                }
                            } else
                            {
                                int c = Integer.parseInt(args[3]);
                                try
                                {
                                    final Connection connection = gradeConnexion.getConnexion();

                                    final PreparedStatement TicketsPlayer = connection.prepareStatement("INSERT INTO `sad_coinsbyuuid` (UUID, coins) VALUES (" + "\"" + pl.getUniqueId() + "\", \"" + c + "\")");
                                    TicketsPlayer.executeUpdate();

                                    p.sendMessage(messages.getString("cmd-whol-successfullyEdit").replace("&","§"));

                                } catch (SQLException e)
                                {
                                    e.printStackTrace();
                                }
                            }

                        } else p.sendMessage(messages.getString("cmd-whol-errorSyntax").replace("&", "§"));
                    }

                    if(args[1].equalsIgnoreCase("reset"))
                    {
                        if(args.length == 3)
                        {
                            Player pl = Bukkit.getPlayer(args[2]);
                            if(getIfInCoins(pl.getUniqueId()) != null)
                            {
                                try
                                {
                                    final Connection connection = gradeConnexion.getConnexion();
                                    final PreparedStatement deleteTicketsPlayer = connection.prepareStatement("DELETE FROM `sad_coinsbyuuid` WHERE UUID = \"" + pl.getUniqueId() + "\"");
                                    deleteTicketsPlayer.executeUpdate();

                                    p.sendMessage(messages.getString("cmd-whol-successfullyEdit").replace("&","§"));

                                } catch (SQLException e)
                                {
                                    e.printStackTrace();
                                }
                            } else
                            {
                                 p.sendMessage(messages.getString("cmd-whol-successfullyEdit").replace("&","§"));
                            }

                        } else p.sendMessage(messages.getString("cmd-whol-errorSyntax").replace("&", "§"));
                    }

                    if(args[1].equalsIgnoreCase("info"))
                    {
                        if(args.length == 3)
                        {
                            Player pl = Bukkit.getPlayer(args[2]);
                            if(getIfInCoins(pl.getUniqueId()) != null)
                            {
                                p.sendMessage(messages.getString("cmd-whol-value-give").replace("&", "§") + getCoins(pl.getUniqueId()));
                            } else
                            {
                                p.sendMessage(messages.getString("cmd-whol-value-give").replace("&", "§") + "0");
                            }

                        } else p.sendMessage(messages.getString("cmd-whol-errorSyntax").replace("&", "§"));
                    }

                }



                if(args[0].equalsIgnoreCase("tickets"))
                {
                    if(args[1].equalsIgnoreCase("add"))
                    {
                        if(args.length == 4)
                        {
                            Player pl = Bukkit.getPlayer(args[2]);
                            if(getIfInTickets(pl.getUniqueId()) != null)
                            {
                                int c = Integer.parseInt(getTickets(pl.getUniqueId())) + Integer.parseInt(args[3]);
                                try
                                {
                                    final Connection connection = gradeConnexion.getConnexion();
                                    final PreparedStatement deleteTicketsPlayer = connection.prepareStatement("DELETE FROM `sad_ticketsbyuuid` WHERE UUID = \"" + pl.getUniqueId() + "\"");
                                    deleteTicketsPlayer.executeUpdate();

                                    final PreparedStatement TicketsPlayer = connection.prepareStatement("INSERT INTO `sad_ticketsbyuuid` (UUID, tickets) VALUES (" + "\"" + pl.getUniqueId() + "\", \"" + c + "\")");
                                    TicketsPlayer.executeUpdate();

                                    p.sendMessage(messages.getString("cmd-whol-successfullyEdit").replace("&","§"));

                                } catch (SQLException e)
                                {
                                    e.printStackTrace();
                                }
                            } else
                            {
                                int c = Integer.parseInt(getTickets(pl.getUniqueId())) + Integer.parseInt(args[3]);
                                try
                                {
                                    final Connection connection = gradeConnexion.getConnexion();

                                    final PreparedStatement TicketsPlayer = connection.prepareStatement("INSERT INTO `sad_ticketsbyuuid` (UUID, tickets) VALUES (" + "\"" + pl.getUniqueId() + "\", \"" + c + "\")");
                                    TicketsPlayer.executeUpdate();

                                    p.sendMessage(messages.getString("cmd-whol-successfullyEdit").replace("&","§"));

                                } catch (SQLException e)
                                {
                                    e.printStackTrace();
                                }
                            }

                        } else p.sendMessage(messages.getString("cmd-whol-errorSyntax").replace("&", "§"));
                    }

                    if(args[1].equalsIgnoreCase("remove"))
                    {
                        if(args.length == 4)
                        {
                            Player pl = Bukkit.getPlayer(args[2]);
                            if(getIfInTickets(pl.getUniqueId()) != null)
                            {
                                int c = Integer.parseInt(getTickets(pl.getUniqueId())) - Integer.parseInt(args[3]);
                                try
                                {
                                    final Connection connection = gradeConnexion.getConnexion();
                                    final PreparedStatement deleteTicketsPlayer = connection.prepareStatement("DELETE FROM `sad_ticketsbyuuid` WHERE UUID = \"" + pl.getUniqueId() + "\"");
                                    deleteTicketsPlayer.executeUpdate();

                                    final PreparedStatement TicketsPlayer = connection.prepareStatement("INSERT INTO `sad_ticketsbyuuid` (UUID, tickets) VALUES (" + "\"" + pl.getUniqueId() + "\", \"" + c + "\")");
                                    TicketsPlayer.executeUpdate();

                                    p.sendMessage(messages.getString("cmd-whol-successfullyEdit").replace("&","§"));

                                } catch (SQLException e)
                                {
                                    e.printStackTrace();
                                }
                            } else
                            {
                                int c = Integer.parseInt(getTickets(pl.getUniqueId())) - Integer.parseInt(args[3]);
                                try
                                {
                                    final Connection connection = gradeConnexion.getConnexion();

                                    final PreparedStatement TicketsPlayer = connection.prepareStatement("INSERT INTO `sad_ticketsbyuuid` (UUID, tickets) VALUES (" + "\"" + pl.getUniqueId() + "\", \"" + c + "\")");
                                    TicketsPlayer.executeUpdate();

                                    p.sendMessage(messages.getString("cmd-whol-successfullyEdit").replace("&","§"));

                                } catch (SQLException e)
                                {
                                    e.printStackTrace();
                                }
                            }

                        } else p.sendMessage(messages.getString("cmd-whol-errorSyntax").replace("&", "§"));
                    }

                    if(args[1].equalsIgnoreCase("set"))
                    {
                        if(args.length == 4)
                        {
                            Player pl = Bukkit.getPlayer(args[2]);
                            if(getIfInTickets(pl.getUniqueId()) != null)
                            {
                                int c = Integer.parseInt(args[3]);
                                try
                                {
                                    final Connection connection = gradeConnexion.getConnexion();
                                    final PreparedStatement deleteTicketsPlayer = connection.prepareStatement("DELETE FROM `sad_ticketsbyuuid` WHERE UUID = \"" + pl.getUniqueId() + "\"");
                                    deleteTicketsPlayer.executeUpdate();

                                    final PreparedStatement TicketsPlayer = connection.prepareStatement("INSERT INTO `sad_ticketsbyuuid` (UUID, tickets) VALUES (" + "\"" + pl.getUniqueId() + "\", \"" + c + "\")");
                                    TicketsPlayer.executeUpdate();

                                    p.sendMessage(messages.getString("cmd-whol-successfullyEdit").replace("&","§"));

                                } catch (SQLException e)
                                {
                                    e.printStackTrace();
                                }
                            } else
                            {
                                int c = Integer.parseInt(args[3]);
                                try
                                {
                                    final Connection connection = gradeConnexion.getConnexion();

                                    final PreparedStatement TicketsPlayer = connection.prepareStatement("INSERT INTO `sad_ticketsbyuuid` (UUID, tickets) VALUES (" + "\"" + pl.getUniqueId() + "\", \"" + c + "\")");
                                    TicketsPlayer.executeUpdate();

                                    p.sendMessage(messages.getString("cmd-whol-successfullyEdit").replace("&","§"));

                                } catch (SQLException e)
                                {
                                    e.printStackTrace();
                                }
                            }

                        } else p.sendMessage(messages.getString("cmd-whol-errorSyntax").replace("&", "§"));
                    }

                    if(args[1].equalsIgnoreCase("reset"))
                    {
                        if(args.length == 3)
                        {
                            Player pl = Bukkit.getPlayer(args[2]);
                            if(getIfInTickets(pl.getUniqueId()) != null)
                            {
                                try
                                {
                                    final Connection connection = gradeConnexion.getConnexion();
                                    final PreparedStatement deleteTicketsPlayer = connection.prepareStatement("DELETE FROM `sad_ticketsbyuuid` WHERE UUID = \"" + pl.getUniqueId() + "\"");
                                    deleteTicketsPlayer.executeUpdate();

                                    p.sendMessage(messages.getString("cmd-whol-successfullyEdit").replace("&","§"));

                                } catch (SQLException e)
                                {
                                    e.printStackTrace();
                                }
                            } else
                            {
                                p.sendMessage(messages.getString("cmd-whol-successfullyEdit").replace("&","§"));
                            }

                        } else p.sendMessage(messages.getString("cmd-whol-errorSyntax").replace("&", "§"));
                    }

                    if(args[1].equalsIgnoreCase("info"))
                    {
                        if(args.length == 3)
                        {
                            Player pl = Bukkit.getPlayer(args[2]);
                            if(getIfInTickets(pl.getUniqueId()) != null)
                            {
                                p.sendMessage(messages.getString("cmd-whol-value-give").replace("&", "§") + getTickets(pl.getUniqueId()));
                            } else
                            {
                                p.sendMessage(messages.getString("cmd-whol-value-give").replace("&", "§") + "0");
                            }

                        } else p.sendMessage(messages.getString("cmd-whol-errorSyntax").replace("&", "§"));
                    }

                }



                if(args[0].equalsIgnoreCase("fidelity"))
                {
                    if(args[1].equalsIgnoreCase("add"))
                    {
                        if(args.length == 4)
                        {
                            Player pl = Bukkit.getPlayer(args[2]);
                            if(getIfInFidelity(pl.getUniqueId()) != null)
                            {
                                int c = Integer.parseInt(getFidelity(pl.getUniqueId())) + Integer.parseInt(args[3]);
                                try
                                {
                                    final Connection connection = gradeConnexion.getConnexion();
                                    final PreparedStatement deleteTicketsPlayer = connection.prepareStatement("DELETE FROM `sad_fidelitybyuuid` WHERE UUID = \"" + pl.getUniqueId() + "\"");
                                    deleteTicketsPlayer.executeUpdate();

                                    final PreparedStatement TicketsPlayer = connection.prepareStatement("INSERT INTO `sad_fidelitybyuuid` (UUID, fidelity) VALUES (" + "\"" + pl.getUniqueId() + "\", \"" + c + "\")");
                                    TicketsPlayer.executeUpdate();

                                    p.sendMessage(messages.getString("cmd-whol-successfullyEdit").replace("&","§"));

                                } catch (SQLException e)
                                {
                                    e.printStackTrace();
                                }
                            } else
                            {
                                int c = Integer.parseInt(getFidelity(pl.getUniqueId())) + Integer.parseInt(args[3]);
                                try
                                {
                                    final Connection connection = gradeConnexion.getConnexion();

                                    final PreparedStatement TicketsPlayer = connection.prepareStatement("INSERT INTO `sad_fidelitybyuuid` (UUID, fidelity) VALUES (" + "\"" + pl.getUniqueId() + "\", \"" + c + "\")");
                                    TicketsPlayer.executeUpdate();

                                    p.sendMessage(messages.getString("cmd-whol-successfullyEdit").replace("&","§"));

                                } catch (SQLException e)
                                {
                                    e.printStackTrace();
                                }
                            }

                        } else p.sendMessage(messages.getString("cmd-whol-errorSyntax").replace("&", "§"));
                    }

                    if(args[1].equalsIgnoreCase("remove"))
                    {
                        if(args.length == 4)
                        {
                            Player pl = Bukkit.getPlayer(args[2]);
                            if(getIfInFidelity(pl.getUniqueId()) != null)
                            {
                                int c = Integer.parseInt(getFidelity(pl.getUniqueId())) - Integer.parseInt(args[3]);
                                try
                                {
                                    final Connection connection = gradeConnexion.getConnexion();
                                    final PreparedStatement deleteTicketsPlayer = connection.prepareStatement("DELETE FROM `sad_fidelitybyuuid` WHERE UUID = \"" + pl.getUniqueId() + "\"");
                                    deleteTicketsPlayer.executeUpdate();

                                    final PreparedStatement TicketsPlayer = connection.prepareStatement("INSERT INTO `sad_fidelitybyuuid` (UUID, fidelity) VALUES (" + "\"" + pl.getUniqueId() + "\", \"" + c + "\")");
                                    TicketsPlayer.executeUpdate();

                                    p.sendMessage(messages.getString("cmd-whol-successfullyEdit").replace("&","§"));

                                } catch (SQLException e)
                                {
                                    e.printStackTrace();
                                }
                            } else
                            {
                                int c = Integer.parseInt(getFidelity(pl.getUniqueId())) - Integer.parseInt(args[3]);
                                try
                                {
                                    final Connection connection = gradeConnexion.getConnexion();

                                    final PreparedStatement TicketsPlayer = connection.prepareStatement("INSERT INTO `sad_fidelitybyuuid` (UUID, fidelity) VALUES (" + "\"" + pl.getUniqueId() + "\", \"" + c + "\")");
                                    TicketsPlayer.executeUpdate();

                                    p.sendMessage(messages.getString("cmd-whol-successfullyEdit").replace("&","§"));

                                } catch (SQLException e)
                                {
                                    e.printStackTrace();
                                }
                            }

                        } else p.sendMessage(messages.getString("cmd-whol-errorSyntax").replace("&", "§"));
                    }

                    if(args[1].equalsIgnoreCase("set"))
                    {
                        if(args.length == 4)
                        {
                            Player pl = Bukkit.getPlayer(args[2]);
                            if(getIfInFidelity(pl.getUniqueId()) != null)
                            {
                                int c = Integer.parseInt(args[3]);
                                try
                                {
                                    final Connection connection = gradeConnexion.getConnexion();
                                    final PreparedStatement deleteTicketsPlayer = connection.prepareStatement("DELETE FROM `sad_fidelitybyuuid` WHERE UUID = \"" + pl.getUniqueId() + "\"");
                                    deleteTicketsPlayer.executeUpdate();

                                    final PreparedStatement TicketsPlayer = connection.prepareStatement("INSERT INTO `sad_fidelitybyuuid` (UUID, fidelity) VALUES (" + "\"" + pl.getUniqueId() + "\", \"" + c + "\")");
                                    TicketsPlayer.executeUpdate();

                                    p.sendMessage(messages.getString("cmd-whol-successfullyEdit").replace("&","§"));

                                } catch (SQLException e)
                                {
                                    e.printStackTrace();
                                }
                            } else
                            {
                                int c = Integer.parseInt(args[3]);
                                try
                                {
                                    final Connection connection = gradeConnexion.getConnexion();

                                    final PreparedStatement TicketsPlayer = connection.prepareStatement("INSERT INTO `sad_fidelitybyuuid` (UUID, fidelity) VALUES (" + "\"" + pl.getUniqueId() + "\", \"" + c + "\")");
                                    TicketsPlayer.executeUpdate();

                                    p.sendMessage(messages.getString("cmd-whol-successfullyEdit").replace("&","§"));

                                } catch (SQLException e)
                                {
                                    e.printStackTrace();
                                }
                            }

                        } else p.sendMessage(messages.getString("cmd-whol-errorSyntax").replace("&", "§"));
                    }

                    if(args[1].equalsIgnoreCase("reset"))
                    {
                        if(args.length == 3)
                        {
                            Player pl = Bukkit.getPlayer(args[2]);
                            if(getIfInFidelity(pl.getUniqueId()) != null)
                            {
                                try
                                {
                                    final Connection connection = gradeConnexion.getConnexion();
                                    final PreparedStatement deleteTicketsPlayer = connection.prepareStatement("DELETE FROM `sad_fidelitybyuuid` WHERE UUID = \"" + pl.getUniqueId() + "\"");
                                    deleteTicketsPlayer.executeUpdate();

                                    p.sendMessage(messages.getString("cmd-whol-successfullyEdit").replace("&","§"));

                                } catch (SQLException e)
                                {
                                    e.printStackTrace();
                                }
                            } else
                            {
                                p.sendMessage(messages.getString("cmd-whol-successfullyEdit").replace("&","§"));
                            }

                        } else p.sendMessage(messages.getString("cmd-whol-errorSyntax").replace("&", "§"));
                    }

                    if(args[1].equalsIgnoreCase("info"))
                    {
                        if(args.length == 3)
                        {
                            Player pl = Bukkit.getPlayer(args[2]);
                            if(getIfInFidelity(pl.getUniqueId()) != null)
                            {
                                p.sendMessage(messages.getString("cmd-whol-value-give").replace("&", "§") + getFidelity(pl.getUniqueId()));
                            } else
                            {
                                p.sendMessage(messages.getString("cmd-whol-value-give").replace("&", "§") + "0");
                            }

                        } else p.sendMessage(messages.getString("cmd-whol-errorSyntax").replace("&", "§"));
                    }

                }




            }

        }

            return false;
    }

    /*
        SQL
     */


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

    public static String getIfInTickets(UUID uuid)
    {

        final DbConnexion gradeConnexion = main.getInstance().getDatabaseManager().getGradeConnexion();
        try
        {
            final Connection connection = gradeConnexion.getConnexion();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT sad_ticketsbyuuid.UUID FROM sad_ticketsbyuuid WHERE UUID = \"" + uuid.toString() + "\" ");
            final ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) return resultSet.getString("UUID");

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }


    public static String getFidelity(UUID uuid)
    {

        final DbConnexion gradeConnexion = main.getInstance().getDatabaseManager().getGradeConnexion();
        try
        {
            final Connection connection = gradeConnexion.getConnexion();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT sad_fidelitybyuuid.fidelity FROM sad_fidelitybyuuid WHERE UUID = \"" + uuid.toString() + "\" ");
            final ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) return resultSet.getString("fidelity");

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static String getIfInFidelity(UUID uuid)
    {

        final DbConnexion gradeConnexion = main.getInstance().getDatabaseManager().getGradeConnexion();
        try
        {
            final Connection connection = gradeConnexion.getConnexion();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT sad_fidelitybyuuid.UUID FROM sad_fidelitybyuuid WHERE UUID = \"" + uuid.toString() + "\" ");
            final ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) return resultSet.getString("UUID");

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }


    public static String getCoins(UUID uuid)
    {

        final DbConnexion gradeConnexion = main.getInstance().getDatabaseManager().getGradeConnexion();
        try
        {
            final Connection connection = gradeConnexion.getConnexion();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT sad_coinsbyuuid.coins FROM sad_coinsbyuuid WHERE UUID = \"" + uuid.toString() + "\" ");
            final ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) return resultSet.getString("coins");

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static String getIfInCoins(UUID uuid)
    {

        final DbConnexion gradeConnexion = main.getInstance().getDatabaseManager().getGradeConnexion();
        try
        {
            final Connection connection = gradeConnexion.getConnexion();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT sad_coinsbyuuid.UUID FROM sad_coinsbyuuid WHERE UUID = \"" + uuid.toString() + "\" ");
            final ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) return resultSet.getString("UUID");

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

}
