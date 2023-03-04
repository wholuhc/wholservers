package fr.curiosow.sad.methods;

import fr.curiosow.sad.main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class OpenOtherGUI
{

	public interface OpenNewGUI
	{
		public void executeFunction(Player p) throws SQLException;
	}

	public static void OpenAnOtherGUI(Player p, OpenNewGUI classOfFunction)
	{
		p.getOpenInventory().close();

		Bukkit.getScheduler().scheduleSyncDelayedTask(main.getInstance(), new Runnable() {

			@Override
			public void run() {
				try
				{
					classOfFunction.executeFunction(p);
				} catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
		}, 1);
	}

}
