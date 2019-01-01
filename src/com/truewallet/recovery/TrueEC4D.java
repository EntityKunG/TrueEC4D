package com.truewallet.recovery;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class TrueEC4D implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String cmdLabel, String[] args) {
		if (s instanceof Player) {
			Player p = (Player)s;
			if (p.isOp()) {
				if (args.length == 0) {
					p.sendMessage(" ");
					p.sendMessage("           §6[§eTrueEC4D§6]           ");
					p.sendMessage(" ");
					p.sendMessage("§e/trueec4d reload §7- Reload Plugin");
					p.sendMessage("§e/trueec4d status [name] §7- Check status playerdata");
					p.sendMessage("§e/trueec4d transaction [transaction] §7- Show Transaction");
					p.sendMessage("§e/trueec4d cashcard [cashcard] §7- Show cashcard");
					p.sendMessage(" ");
				} else {
					if (args[0].equalsIgnoreCase("reload")) {
						Main.getPlugin().onDisable();
						Main.getPlugin().onEnable();
						p.sendMessage("§6[§eTrueEC4D§6]§7: §aReload successfully");
					} else if (args[0].equalsIgnoreCase("status")) {
						if (args.length <= 1) {
							p.sendMessage("§6[§eTrueEC4D§6]§7: §cPlease provide an Username!");
						} else if (args.length >= 1) {
							String playername = args[1];
							FileConfiguration config = YamlConfiguration.loadConfiguration(new File(Main.getPlugin().getDataFolder(), "/database/player.yml"));
							if (config.contains("Player." + playername)) {
								p.sendMessage("      §c§l===§6[§eTrueEC4D§6]§c§l===      ");
								p.sendMessage(" ");
								p.sendMessage("§fName: §a" + playername);
								p.sendMessage("§fPoint: §a" + Economy.getPoint(playername));
							} else {
								p.sendMessage("§6[§eTrueEC4D§6]§7: §cNo player in database!");
							}
						} else {
							p.sendMessage("§6[§eTrueEC4D§6]§7: §cPlease provide an Username!");
						}
					} else if (args[0].equalsIgnoreCase("transaction")) {
						if (args.length <= 1) {
							p.sendMessage("§6[§eTrueEC4D§6]§7: §cPlease provide a Transaction ID!");
						} else if (args.length == 1) {
							if (Main.getPlugin().getTransactionConfig().contains("TrueWallet." + args[0])) {
								p.sendMessage("      §c§l===§6[§eTrueEC4D§6]§c§l===      ");
								p.sendMessage(" ");
								p.sendMessage("§fTransaction: §7" + args[0]);
								p.sendMessage("§fPlayer topup: §e" + Main.getPlugin().getTransactionConfig().getString("Transaction.TrueWallet." + args[0] + ".Name"));
							    p.sendMessage("§fDate: §b" + Main.getPlugin().getTransactionConfig().getString("Transaction.TrueWallet." + args[0] + ".Date"));
							    p.sendMessage("§fAmount: §c" + Main.getPlugin().getTransactionConfig().getString("Transaction.TrueWallet." + args[0] + ".Amount"));
							} else { 
								p.sendMessage("§6[§eTrueEC4D§6]§7: §cTransaction ID not found. Please try again later.");
							}
						} else {
							p.sendMessage("§6[§eTrueEC4D§6]§7: §cPlease provide a Transaction ID!");
						}
					} else if (args[0].equalsIgnoreCase("cashcard")) {
						if (args.length <= 1) {
							p.sendMessage("§6[§eTrueEC4D§6]§7: §cPlease provide a Cashcard PIN!");
						} else if (args.length == 1) {
							if (Main.getPlugin().getTransactionConfig().contains("TrueMoney." + args[0])) {
								p.sendMessage("      §c§l===§6[§eTrueEC4D§6]§c§l===      ");
								p.sendMessage(" ");
								p.sendMessage("§fCashcard: §7" + args[0]);
								p.sendMessage("§fPlayer topup: §e" + Main.getPlugin().getTransactionConfig().getString("Transaction.TrueMoney." + args[0] + ".Name"));
							    p.sendMessage("§fDate: §b" + Main.getPlugin().getTransactionConfig().getString("Transaction.TrueMoney." + args[0] + ".Date"));
							    p.sendMessage("§fAmount: §c" + Main.getPlugin().getTransactionConfig().getString("Transaction.TrueMoney." + args[0] + ".Amount"));
							} else { 
								p.sendMessage("§6[§eTrueEC4D§6]§7: §cCashcard not found. Please try again later.");
							}
						} else {
							p.sendMessage("§6[§eTrueEC4D§6]§7: §cPlease provide a Cashcard PIN!");
						}
					} else {
						p.sendMessage(" ");
						p.sendMessage("           §6[§eTrueEC4D§6]           ");
						p.sendMessage(" ");
						p.sendMessage("§e/trueec4d reload §7- Reload Plugin");
						p.sendMessage("§e/trueec4d status [name] §7- Check status playerdata");
						p.sendMessage("§e/trueec4d transaction [transaction] §7- Show Transaction");
						p.sendMessage("§e/trueec4d cashcard [cashcard] §7- Show cashcard");
						p.sendMessage(" ");
					}
				}
			} else {
				p.sendMessage("§6[§eTrueEC4D§6]§7: §cYou don't have permissions to access this command!");
			}
		} else {
			if (args.length == 0) {
				s.sendMessage("           §6[§eTrueEC4D§6]           ");
				s.sendMessage(" ");
				s.sendMessage("§e/trueec4d reload §7- Relaod Plugin");
				s.sendMessage("§e/trueec4d status [name] §7- Check status playerdata");
				s.sendMessage("§e/trueec4d transaction [transaction] §7- Show Transaction");
				s.sendMessage("§e/trueec4d cashcard [cashcard] §7- Show cashcard");
				s.sendMessage(" ");
			} else {
				if (args[0].equalsIgnoreCase("reload")) {
					Main.getPlugin().onDisable();
					Main.getPlugin().onEnable();
					s.sendMessage("§6[§eTrueEC4D§6]§7: §aReload successfully");
				} else if (args[0].equalsIgnoreCase("status")) {
					if (args.length <= 1) {
						s.sendMessage("§6[§eTrueEC4D§6]§7: §cPlease provide an Username!");
					} else if (args.length == 1) {
						String playername = args[0];
						s.sendMessage("      §c§l===§6[§eTrueEC4D§6]§c§l===      ");
						s.sendMessage(" ");
						s.sendMessage("§fName: §a" + playername);
						s.sendMessage("§fPoint: §a" + Economy.getPoint(playername));
					} else {
						s.sendMessage("§6[§eTrueEC4D§6]§7: §cPlease provide an Username!");
					}
				} else if (args[0].equalsIgnoreCase("transaction")) {
					if (args.length <= 1) {
						for (String msg : Main.getPlugin().getTransactionConfig().getStringList("Transaction.List")) {
							s.sendMessage("§6[§eTrueEC4D§6] §c** §fAll Transaction §c**");
							s.sendMessage("§7-" + msg);
						}
					} else if (args.length == 1) {
						if (Main.getPlugin().getTransactionConfig().contains("TrueWallet." + args[0])) {
							s.sendMessage("      §c§l===§6[§eTrueEC4D§6]§c§l===      ");
							s.sendMessage(" ");
							s.sendMessage("§fTransaction: §7" + args[0]);
							s.sendMessage("§fPlayer topup: §e" + Main.getPlugin().getTransactionConfig().getString("TrueWallet." + args[0] + ".Name"));
						    s.sendMessage("§fDate: §b" + Main.getPlugin().getTransactionConfig().getString("TrueWallet." + args[0] + ".Date"));
						    s.sendMessage("§fAmount: §c" + Main.getPlugin().getTransactionConfig().getString("TrueWallet." + args[0] + ".Amount"));
						} else { 
							s.sendMessage("§6[§eTrueEC4D§6]§7: §cTransaction ID not found. Please try again later.");
						}
					} else {
						s.sendMessage("§6[§eTrueEC4D§6]§7: §cPlease provide a Transaction ID!");
					}
				} else if (args[0].equalsIgnoreCase("cashcard")) {
					if (args.length <= 1) {
						s.sendMessage("§6[§eTrueEC4D§6]§7: §cPlease provide a Cashcard PIN!");
					} else if (args.length == 1) {
						if (Main.getPlugin().getTransactionConfig().contains("TrueMoney." + args[0])) {
							s.sendMessage("      §c§l===§6[§eTrueEC4D§6]§c§l===      ");
							s.sendMessage(" ");
							s.sendMessage("§fCashcard: §7" + args[0]);
							s.sendMessage("§fslayer tosus: §e" + Main.getPlugin().getTransactionConfig().getString("Transaction.TrueMoney." + args[0] + ".Name"));
						    s.sendMessage("§fDate: §b" + Main.getPlugin().getTransactionConfig().getString("Transaction.TrueMoney." + args[0] + ".Date"));
						    s.sendMessage("§fAmount: §c" + Main.getPlugin().getTransactionConfig().getString("Transaction.TrueMoney." + args[0] + ".Amount"));
						} else { 
							s.sendMessage("§6[§eTrueEC4D§6]§7: §cCashcard not found. Please try again later.");
						}
					} else {
						s.sendMessage("§6[§eTrueEC4D§6]§7: §cPlease provide a Cashcard PIN!");
					}
				} else {
					s.sendMessage(" ");
					s.sendMessage("           §6[§eTrueEC4D§6]           ");
					s.sendMessage(" ");
					s.sendMessage("§e/trueec4d reload §7- Reload plugin");
					s.sendMessage("§e/trueec4d status [name] §7- Check status playerdata");
					s.sendMessage("§e/trueec4d transaction [transaction] §7- Show Transaction");
					s.sendMessage("§e/trueec4d cashcard [cashcard] §7- Show cashcard");
					s.sendMessage(" ");
				}
			}
		}
		return false;
	}

}
