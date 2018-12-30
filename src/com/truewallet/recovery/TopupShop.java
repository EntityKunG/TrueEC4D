package com.truewallet.recovery;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class TopupShop implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String cmdLabel, String[] args) {
		
		if (s instanceof Player) {
			Player p = (Player)s;
			FileConfiguration config = YamlConfiguration.loadConfiguration(new File(Main.getPlugin().getDataFolder() , "/menu/gui.yml"));
			if (!config.contains("inventory")) {
				String message = Main.getPlugin().getMessage(Main.getPlugin().getMessageConfig().getString("ShopGUI.error"));
				p.sendMessage(message);
			} else {
				int size = config.getInt("menu-size");
				if (size > 64) {
					size = 64;
				} else if (size < 9) {
					size = 9;
				}
				if (size%9 != 0) {
					size = ((int)(size/9)+1)*9;
				}
				Inventory inventory = Bukkit.createInventory(null, size, ChatColor.translateAlternateColorCodes('&', config.getString("menu-name")));
				for (int all = 0; all < size; all++) {
					if (config.contains("inventory." + all)) {
						Material material = getMaterial(config.getString("inventory." + all + ".display-item"));
						int data = config.getInt("inventory." + all + ".display-item-data");
						int amount = config.getInt("inventory." + all + ".display-item-amount");
						String display = ChatColor.translateAlternateColorCodes('&', config.getString("inventory." + all + ".display-name"));
						List<String> lore1 = config.getStringList("inventory." + all + ".display-item-lore");
						int price = config.getInt("inventory." + all + ".price");
						
						ItemStack is = new ItemStack(material, amount, (byte)data);
						ItemMeta im = is.getItemMeta();
						im.setDisplayName(display.replaceAll("%player%", p.getName()));
						List<String> lore = new ArrayList<String>();
						for (String str:lore1) {
							lore.add(ChatColor.translateAlternateColorCodes('&', str).replaceAll("%player%", p.getName()));
						}
						lore.add(" ");
						lore.add(Main.getPlugin().getMessage(Main.getPlugin().getMessageConfig().getString("ShopGUI.price").replace("{point}", String.valueOf(price))));
						if (Economy.getPoint(p.getName()) >= price) {
							lore.add(" ");
							lore.add(Main.getPlugin().getMessage(Main.getPlugin().getMessageConfig().getString("ShopGUI.canbuy")));
						} else {
							lore.add(" ");
							lore.add(Main.getPlugin().getMessage(Main.getPlugin().getMessageConfig().getString("ShopGUI.cantbuy")));
						}
						im.setLore(lore);
						is.setItemMeta(im);
						inventory.setItem(all, is);
					}
				}
				p.openInventory(inventory);
				return true;
			}
			return true;
		}
		s.sendMessage("§2[§aShopGUI§2]§7: §fThis command available for player only!");
		return false;
	}
	
	private Material getMaterial(String string) {
		Material material = Material.BEDROCK;
		if (isOnlyNumber(string)) {
			if (Material.getMaterial(string) == null) {
				material = Material.DIRT;
			} else {
				material = Material.getMaterial(string);
			}
		} else {
			if (Material.getMaterial(string.toUpperCase()) == null) {
				material = Material.DIRT;
			} else {
				material = Material.getMaterial(string.toUpperCase());
			}
		}
		return material;
	}

	public static boolean isBlank(String value) {
	    return (value == null || value.equals("") || value.equals("null") || value.trim().equals(""));
	}

	
	public static boolean isOnlyNumber(String value) {
	    boolean ret = false;
	    if (!isBlank(value)) {
	        ret = value.matches("^[0-9]+$");
	    }
	    return ret;
	}

}
