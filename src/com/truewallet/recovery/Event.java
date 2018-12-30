package com.truewallet.recovery;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class Event implements Listener {
	
	@EventHandler
	public void InventoryClick(InventoryClickEvent e) {
		Player p = (Player)e.getWhoClicked();
		FileConfiguration config = YamlConfiguration.loadConfiguration(new File(Main.getPlugin().getDataFolder(), "/menu/gui.yml"));
		if (e.getClickedInventory() != null && e.getCurrentItem() != null && p.getOpenInventory().getTitle().equals(ChatColor.translateAlternateColorCodes('&', config.getString("menu-name")))) {
			e.setCancelled(true);
		}
		if (e.getClickedInventory() != null && e.getCurrentItem() != null && e.getClickedInventory().getTitle().equals(ChatColor.translateAlternateColorCodes('&', config.getString("menu-name")))) {
			e.setCancelled(true);
			int slot = e.getSlot();
			if (config.contains("inventory." + slot)) {
				int price = config.getInt("inventory." + slot + ".price");
				if (Economy.getPoint(p.getName()) >= price) {
					Economy.TakePoint(p.getName(), price);
					List<String> cmds = config.getStringList("inventory." + slot + ".execute-commands");
					for (String str:cmds) {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), str.replaceAll("%player%", p.getName()));
					}
					p.closeInventory();
					Sound sound;
					if (Main.ServerVersion == 1 || Main.ServerVersion == 2) {
						sound = Sound1_8_to_1_9.ORB_PICKUP.bukkitSound();
					} else {
						sound = Sound1_8_to_1_13.ORB_PICKUP.bukkitSound();
					}
					p.playSound(p.getLocation(), sound, 10, 1);
				} else {
					p.sendMessage(Main.getPlugin().getMessage(Main.getPlugin().getMessageConfig().getString("ShopGUI.donthaveenoght")));
				}
			}
		}
	}
	
	

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = (Player)e.getPlayer();
		FileConfiguration players = YamlConfiguration.loadConfiguration(new File(Main.getPlugin().getDataFolder(), "/database/player.yml"));
		if (!players.contains("Player." + p.getName())) {
			players.set("Player." + p.getName() + ".Point", Integer.valueOf(0));
			try {
				players.save(new File(Main.getPlugin().getDataFolder(), "/database/player.yml"));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
