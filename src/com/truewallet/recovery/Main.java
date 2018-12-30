package com.truewallet.recovery;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	public static Main Plugin;
	public static String authors = "§fSpringCraft§e, §fEditCraft§e, §fDemza";
	private FileConfiguration customConfig;
	public static String IP = "10.148.0.2";
    public static int ServerVersion = 0;

	@Override
	public void onEnable() {
		Main.main();
		if (Plugin != null) {
			Bukkit.getConsoleSender().sendMessage("§6[§eTrueEC4D§6] §fPlease do not use /reload or plugin reloaders. Do §a/trueec4d reload §finstead.");
			return;
		}
		if (ServerVersion == 0) {
			Bukkit.getConsoleSender().sendMessage("          §c======§6[§eTrueEC4D§6]§c======          ");
			Bukkit.getConsoleSender().sendMessage("§fThis server isn't match with minimum requirement");
			Bukkit.getConsoleSender().sendMessage("§fRequire: Minecraft Server 1.8 and newer.");
			Main.getPlugin().onDisable();
			return;
		}
		Plugin = this;
		Bukkit.getConsoleSender().sendMessage("§6[§eTrueEC4D§6] §fversion available: §a1.0");
		Bukkit.getConsoleSender().sendMessage("§fDeveloped by " + authors);
	    Bukkit.getConsoleSender().sendMessage("§fTrueEC4D has been §aEnabled§f!");
	    createCustomConfig();
	    PluginManager pm = Bukkit.getPluginManager();
	    pm.registerEvents(new Event(), this);
	    getCommand("trueec4d").setExecutor(new TrueEC4D());
	    getCommand("wallet").setExecutor(new TrueWallet());
	    getCommand("topupshop").setExecutor(new TopupShop());
	    getCommand("refill").setExecutor(new TrueMoney());
	}
	
	public static void main() {
		String ver = Bukkit.getServer().getVersion();
		if (ver.contains("1.8")) {
			ServerVersion = 1;
		} else if (ver.contains("1.9") || ver.contains("1.10") || ver.contains("1.11") || ver.contains("1.12")) {
			ServerVersion = 2;
		} else if (ver.contains("1.13")) {
			ServerVersion = 3;
		} else {
			ServerVersion = 0;
		}
	}
	
	
	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage("§6[§eTrueEC4D§6] §fversion available: §a1.0");
		Bukkit.getConsoleSender().sendMessage("§fDeveloped by " + authors);
	    Bukkit.getConsoleSender().sendMessage("§fTrueEC4D has been §cDisabled§f!");
	}
	
	public static boolean isEnable() {
		boolean allow = false;
		InetAddress IP = null;
		try {
			IP = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} 
		if (IP.getHostAddress().equals(IP)) {
			allow = true;
			Bukkit.getConsoleSender().sendMessage("§6[§eTrueEC4D§6]§7: §fIP correct!");
		} else {
			allow = false;
			Bukkit.getConsoleSender().sendMessage("§6[§eTrueEC4D§6]§7: §fIP incorrect!, Shutdown Plugin");
			Bukkit.getConsoleSender().sendMessage("§6[§eTrueEC4D§6]§7: §fPlease contain §c@SpringCraft Studio");
		}
		return allow;
	}
	
	public String getMessage(String message) {
		return message.replaceAll("&", "§");
	}
	
	public YamlConfiguration getTransactionConfig() {
		File config = new File(Main.getPlugin().getDataFolder(), "/database/transaction.yml");
	    YamlConfiguration cfg = YamlConfiguration.loadConfiguration(config);
		return cfg;
	}
	
	public YamlConfiguration getMenuConfig() {
		File config = new File(Main.getPlugin().getDataFolder(), "/menu/gui.yml");
	    YamlConfiguration cfg = YamlConfiguration.loadConfiguration(config);
		return cfg;
	}
	
	public YamlConfiguration getMessageConfig() {
		File config = new File(Main.getPlugin().getDataFolder(), "message.yml");
	    YamlConfiguration cfg = YamlConfiguration.loadConfiguration(config);
		return cfg;
	}
	
	public YamlConfiguration getConfig() {
		File config = new File(Main.getPlugin().getDataFolder(), "config.yml");
	    YamlConfiguration cfg = YamlConfiguration.loadConfiguration(config);
		return cfg;
	}
	
	public static Main getPlugin() {
		return Plugin;
	}
	
	public void createCustomConfig() {
	    File customConfigFile = new File(getDataFolder(), "config.yml");
	    File message = new File(getDataFolder(), "message.yml");
	    File gui = new File(getDataFolder(), "/menu/gui.yml");
	    File transaction = new File(getDataFolder(), "/database/transaction.yml");
	    File player = new File(getDataFolder(), "/database/player.yml");
	    if (!gui.exists()) {
	    	gui.getParentFile().mkdirs();
	    	saveResource("menu/gui.yml", false);
            customConfig= new YamlConfiguration();
	        try {
	        	gui.createNewFile();
	            customConfig.load(gui);
	        } catch (IOException | InvalidConfigurationException e) {
	            e.printStackTrace();
	        }
        }
	    if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            saveResource("config.yml", false);
            customConfig= new YamlConfiguration();
	        try {
	            customConfig.load(customConfigFile);
	        } catch (IOException | InvalidConfigurationException e) {
	            e.printStackTrace();
	        }
        }
	    if (!message.exists()) {
	    	message.getParentFile().mkdirs();
	    	saveResource("message.yml", false);
            customConfig= new YamlConfiguration();
	        try {
	            customConfig.load(message);
	        } catch (IOException | InvalidConfigurationException e) {
	            e.printStackTrace();
	        }
	    }
	    if (!transaction.exists()) {
	    	transaction.getParentFile().mkdirs();
	    	saveResource("database/transaction.yml", false);
	    	customConfig= new YamlConfiguration();
	        try {
	        	transaction.createNewFile();
	            customConfig.load(transaction);
	        } catch (IOException | InvalidConfigurationException e) {
	            e.printStackTrace();
	        }
	    }
	    if (!player.exists()) {
	    	player.getParentFile().mkdirs();
	    	saveResource("database/player.yml", false);
	    	customConfig = new YamlConfiguration();
	    	try {
	    		player.createNewFile();
	    		customConfig.load(player);
	    	} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
	    }
    }
 	
}
