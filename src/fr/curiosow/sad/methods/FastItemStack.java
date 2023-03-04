package fr.curiosow.sad.methods;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class FastItemStack
{
	public static ItemStack create(Material material, String name, int quantity)
	{
		ItemStack i = new ItemStack(material);
		ItemMeta iM = i.getItemMeta();
		iM.setDisplayName(name);
		i.setItemMeta(iM);
		i.setAmount(quantity);
		return i;
	}

	public static ItemStack create(Material material, String name, int quantity, List<String> lore)
	{
		ItemStack i = new ItemStack(material);
		ItemMeta iM = i.getItemMeta();
		iM.setDisplayName(name);
		iM.setLore(lore);
		i.setItemMeta(iM);
		i.setAmount(quantity);
		return i;
	}

}
