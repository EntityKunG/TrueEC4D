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

import com.truewallet.recovery.json.JSONArray;
import com.truewallet.recovery.json.JSONObject;
import com.truewallet.recovery.walletapi.Date;
import com.truewallet.recovery.walletapi.WalletAPI;

public class TrueWallet implements CommandExecutor {

	int topup;
	
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String cmdLabel, String[] args) {
		if (s instanceof Player) {
			Player p = (Player)s;
			if (args.length == 0) {
				p.sendMessage(Main.getPlugin().getMessage(Main.getPlugin().getMessageConfig().getString("TrueWallet.help")));
				if (!Main.getPlugin().getMessageConfig().contains("TrueWallet.havepoint")) {
					Main.getPlugin().getMessageConfig().set("TrueWallet.havepoint", "&4[&cTrueWallet&4]&7: &fYou have &a{point} point");
					try {
						Main.getPlugin().getMessageConfig().save(new File(Main.getPlugin().getDataFolder(), "message.yml"));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				p.sendMessage(Main.getPlugin().getMessage(Main.getPlugin().getMessageConfig().getString("TrueWallet.havepoint").replace("{point}", String.valueOf(Economy.getPoint(p.getName())))));
			} else if (args.length == 1) {
				if (Main.getPlugin().getConfig().getString("Email").equals("your@email.com")) {
					p.sendMessage(Main.getPlugin().getMessage(Main.getPlugin().getMessageConfig().getString("TrueWallet.contactadmin")));
					if (p.isOp()) {
					    p.sendMessage("§6[§eTrueEC4D§6]§7: §cTrueMoney Account is not configurated");
					    p.sendMessage("§6[§eTrueEC4D§6]§7: §cPlease edit configurations in config.yml file, then /trueec4d reload!");
					} 
				} else {
					if (Pattern.matches("[a-zA-Z]", args[0])) {
						String message = Main.getPlugin().getMessage(Main.getPlugin().getMessageConfig().getString("TrueWallet.number"));
					    p.sendMessage(message);
					    return false;
					}
					if (args[0].length() <= 13 || args[0].length() >= 15) {
				        String message = Main.getPlugin().getMessage(Main.getPlugin().getMessageConfig().getString("TrueWallet.fillnumber"));
						p.sendMessage(message);
				        return false;   
					}
					topup = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
						  public void run() {
							  Thread thread = new Thread() {
								  public void run() {
									  try {
									    String token = WalletAPI.GetToken(Main.getPlugin().getConfig().getString("Email"), Main.getPlugin().getConfig().getString("Password"));
										String trans = WalletAPI.GetTransaction(token, Date.dateMod(-365), Date.dateMod(1), 50);
								    	JSONArray activities = new JSONObject(trans).getJSONObject("data").getJSONArray("activities");
										for (int i = 0 ; i < activities.length(); i++) {
									        JSONObject obj = activities.getJSONObject(i);
									        if (obj.getString("text3En").equals("creditor")) {
									        	String reportID = obj.getString("reportID");
									        	String detail = WalletAPI.GetReport(token, reportID);
									        	String transactionID = new JSONObject(detail).getJSONObject("data").getJSONObject("section4").getJSONObject("column2").getJSONObject("cell1").getString("value");
									        	String amount = Money(new JSONObject(detail).getJSONObject("data").getJSONObject("section3").getJSONObject("column1").getJSONObject("cell1").getString("value"));
									        	FileConfiguration data = YamlConfiguration.loadConfiguration(new File(Main.getPlugin().getDataFolder(), "/database/transaction.yml"));
									        	if (transactionID.equals(args[0])) {
									        		if (data.getString("Transaction.TrueWallet." + transactionID) != null) {
											        	String message = Main.getPlugin().getMessage(Main.getPlugin().getMessageConfig().getString("TrueWallet.already"));
											            p.sendMessage(message);
									        			break;
									        		} else {
									        			data.set("Transaction.TrueWallet." + args[0] + ".Date", Date.getDate() + " " + Date.getTime());
											        	data.set("Transaction.TrueWallet." + args[0] + ".Name", p.getName());
											        	data.set("Transaction.TrueWallet." + args[0] + ".Amount", amount);
										        		data.save(new File(Main.getPlugin().getDataFolder(), "/database/transaction.yml"));
											        	Economy.AddPoint(p.getName(), (int)Double.parseDouble(amount));
					                                    String message = Main.getPlugin().getMessage(Main.getPlugin().getMessageConfig().getString("TrueWallet.successfully").replace("{point}", amount));
					                                    p.sendMessage(message);
											        	Bukkit.getScheduler().cancelTask(topup);
											        	break;
										        	}
									        	} else if (i == activities.length() - 1) {
													String message = Main.getPlugin().getMessage(Main.getPlugin().getMessageConfig().getString("TrueWallet.notfound"));
									        		p.sendMessage(message);
													break;
												}
									        	continue;
									        }
										} 
									  } catch (IOException e) {
										  e.printStackTrace();
									}
								}
							  };
							  thread.start();
						  }
						}, 60L);
				}
			} else {
				p.sendMessage(Main.getPlugin().getMessage(Main.getPlugin().getMessageConfig().getString("TrueWallet.error")));
			}
		} else {
			s.sendMessage("§6[§eTrueEC4D§6]§7: §fThis command available for player only!");
		}
		return false;
	}
	
	public static String Money(String string) {
		return string.replaceAll(",", "");
	}

}
