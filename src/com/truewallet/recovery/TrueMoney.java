package com.truewallet.recovery;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.truewallet.recovery.json.JSONObject;
import com.truewallet.recovery.walletapi.Date;
import com.truewallet.recovery.walletapi.WalletAPI;

public class TrueMoney implements CommandExecutor {

	int topup;
	
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String cmdLabel, String[] args) {
		if (s instanceof Player) {
			Player p = (Player)s;
			if (args.length == 0) {
				p.sendMessage(Main.getPlugin().getMessage(Main.getPlugin().getMessageConfig().getString("TrueMoney.help")));
				if (!Main.getPlugin().getMessageConfig().contains("TrueMoney.havepoint")) {
				    Main.getPlugin().getMessageConfig().set("TrueMoney.havepoint", "&3[&bTrueMoney&3]&7: &fYou have &a{point} point");
				    try {
				       Main.getPlugin().getMessageConfig().save(new File(Main.getPlugin().getDataFolder(), "message.yml"));
				     } catch (IOException e) {
				       e.printStackTrace();
				     }
				}
				p.sendMessage(Main.getPlugin().getMessage(Main.getPlugin().getMessageConfig().getString("TrueMoney.havepoint").replace("{point}", String.valueOf(Economy.getPoint(p.getName())))));
			} else if (args.length == 1) {
				if (Main.getPlugin().getConfig().getString("Email").equals("your@email.com")) {
					p.sendMessage(Main.getPlugin().getMessage(Main.getPlugin().getMessageConfig().getString("TrueMoney.contactadmin")));
					if (p.isOp()) {
						p.sendMessage("§6[§eTrueEC4D§6]§7: §cNot found email, Please setup and try again");
						p.sendMessage("§6[§eTrueEC4D§6]§7: §cIf you finish you can /trueec4d reload!");
					} 
				} else {
					if (Pattern.matches("[a-zA-Z]", args[0])) {
						String message = Main.getPlugin().getMessage(Main.getPlugin().getMessageConfig().getString("TrueMoney.number"));
					    p.sendMessage(message);
					    return false;
					}
					if (args[0].length() <= 13 || args[0].length() >= 15) {
				        String message = Main.getPlugin().getMessage(Main.getPlugin().getMessageConfig().getString("TrueMoney.fillnumber"));
						p.sendMessage(message);
				        return false;   
					}
				}
				topup = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
					  public void run() {
						  Thread thread = new Thread() {
							  public void run() {
								  try {
								    String token = WalletAPI.GetToken(Main.getPlugin().getConfig().getString("Email"), Main.getPlugin().getConfig().getString("Password"));
									String topup = WalletAPI.Topup(token, args[0]);
									String amount = TrueWallet.Money(String.valueOf(new JSONObject(topup).getInt("amount")));
									FileConfiguration data = YamlConfiguration.loadConfiguration(new File(Main.getPlugin().getDataFolder(), "/database/transaction.yml"));
									if (data.getString("Transaction.TrueMoney." + args[0]) != null) {
									 	String message = Main.getPlugin().getMessage(Main.getPlugin().getMessageConfig().getString("TrueMoney.already"));
									    p.sendMessage(message);
									} else {
										data.set("Transaction.TrueMoney." + args[0] + ".Date", Date.getDate() + " " + Date.getTime());
							        	data.set("Transaction.TrueMoney." + args[0] + ".Name", p.getName());
							        	data.set("Transaction.TrueMoney." + args[0] + ".Amount", amount);
						        		data.save(new File(Main.getPlugin().getDataFolder(), "/database/transaction.yml"));
						        		Economy.AddPoint(p.getName(), Integer.parseInt(amount));
						        		p.sendMessage(Main.getPlugin().getMessage(Main.getPlugin().getMessageConfig().getString("TrueMoney.successfully").replace("{point}", amount)));
						        		Bukkit.getScheduler().cancelTask(TrueMoney.this.topup);
									}
								  } catch (IOException e) {
									  String message = Main.getPlugin().getMessage(Main.getPlugin().getMessageConfig().getString("TrueMoney.error"));
									  p.sendMessage(message);
								  }
							}
						  };
						  thread.start();
					  }
					}, 60L);
			} else if (args.length >= 1) { 
				p.sendMessage(Main.getPlugin().getMessage(Main.getPlugin().getMessageConfig().getString("TrueMoney.help")));
			}
		} else {
			s.sendMessage("§3[§bTrueMoney§3]§7: §fThis command available for player only!");
		}
		return false;
	}

}
