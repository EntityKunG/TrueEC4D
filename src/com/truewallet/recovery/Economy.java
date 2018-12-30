package com.truewallet.recovery;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Economy {

	public static int getPoint(String playername) {
		int value = 0;
		FileConfiguration p = YamlConfiguration.loadConfiguration(new File(Main.getPlugin().getDataFolder(), "/database/player.yml"));
		if (p.contains("Player." + playername)) {
			value = p.getInt("Player." + playername + ".Point");
		}
		return value;
	}
	
	public static void TakePoint(String playername, int amount) {
		int value = getPoint(playername)-amount;
		FileConfiguration p = YamlConfiguration.loadConfiguration(new File(Main.getPlugin().getDataFolder(), "/database/player.yml"));
		if (p.contains("Player." + playername)) {
			p.set("Player." + playername + ".Point", value);
		    try {
		        p.save(new File(Main.getPlugin().getDataFolder(), "/database/player.yml"));
		    } catch (IOException e) {
		    	e.printStackTrace();
			}
		}
	}
	
	public static void AddPoint(String playername, int amount) {
		int value = getPoint(playername)+amount;
		FileConfiguration p = YamlConfiguration.loadConfiguration(new File(Main.getPlugin().getDataFolder(), "/database/player.yml"));
		if (p.contains("Player." + playername)) {
			p.set("Player." + playername + ".Point", value);
		    try {
		        p.save(new File(Main.getPlugin().getDataFolder(), "/database/player.yml"));
		    } catch (IOException e) {
		    	e.printStackTrace();
			}
		}
	}
}
