package online.refract.ServerUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class TPA implements CommandExecutor{

	static Main plugin;
	public TPA(Main instance) {
		plugin = instance;
	}
	
	

	
	@Override 
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {


		// Return if sender is not player
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "This command must be used by a player");
			return false;
		}
		
		// Show usage and return if args are invalid
		if (args.length < 1 || Bukkit.getPlayerExact(args[0]) == null) {
			if (label.equalsIgnoreCase("tpa")) {
				sender.sendMessage(ChatColor.RED + "Usage: /tpa <player>");
			}
			else if (label.equalsIgnoreCase("tpahere")) {
				sender.sendMessage(ChatColor.RED + "Usage: /tpahere <player>");
			}
			else {
				sender.sendMessage(ChatColor.RED + "Usage: /tpaccept <player>");
			}
			return false;
		}
		
		// Vars for convinience
		Player cmdSender = (Player) sender;
		Player targetPlayer = Bukkit.getPlayerExact(args[0]);	
			

		
		
		// Command: /tpa <player>, /tpahere <player
		if (label.equalsIgnoreCase("tpa")||label.equalsIgnoreCase("tpahere")) {

			// Cooldown logic
			if (plugin.tpaList.containsKey(targetPlayer.getUniqueId())) {
				Map<UUID, Long> targetList = plugin.tpaList.get(targetPlayer.getUniqueId());
				if (targetList.containsKey(cmdSender.getUniqueId())) {
					Long coolDownTime =  targetList.get(cmdSender.getUniqueId());
					if (coolDownTime < 0) {coolDownTime *= -1;}
					if (coolDownTime > System.currentTimeMillis()) {
						cmdSender.sendMessage(ChatColor.YELLOW + "PLease wait " + ChatColor.GOLD + (coolDownTime - System.currentTimeMillis())/1000 + "s" + ChatColor.YELLOW + " to send another request to this player" );
						return true;
					}
				}
			}
				
						
			// Send message to target
			String msg = "&e" + cmdSender.getName() + (label.equalsIgnoreCase("tpahere")
					? " has requested for you to teleport to them &f[&2accept&f]"
					: " has requested to teleport to you &f[&2accept&f]");
			TextComponent formattedMsg = new TextComponent(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', msg)));
			formattedMsg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept " + cmdSender.getName()));
			targetPlayer.spigot().sendMessage(formattedMsg);
					
						
			// Send confirmation to sender
			cmdSender.sendMessage(ChatColor.YELLOW + "Request sent. They have 60 seconds to accept");
							
						
			// Add cooldown to HashMap, negative if tpahere
			if (!(plugin.tpaList.containsKey(targetPlayer.getUniqueId()))) {
				plugin.tpaList.put(targetPlayer.getUniqueId(), new HashMap<UUID,Long>());	
			}
			plugin.tpaList.get(targetPlayer.getUniqueId()).put(cmdSender.getUniqueId(), (System.currentTimeMillis() + (60 * 1000)) * (label.equalsIgnoreCase("tpahere") ? -1 :1 ));
			return true;	
		}
			
			
			
		// Command: /tpaccept <player>
		if (label.equalsIgnoreCase("tpaccept")){
			// If target has requested tpa
			if (plugin.tpaList.containsKey(cmdSender.getUniqueId())) {
				Map<UUID, Long> senderList = plugin.tpaList.get(cmdSender.getUniqueId());
				if (senderList.containsKey(targetPlayer.getUniqueId())) {
					Long coolDownTime =  senderList.get(targetPlayer.getUniqueId());
					boolean isHere = false;
					if (coolDownTime < 0) {
						isHere = true;
						coolDownTime *= -1;
					}
					if (coolDownTime > System.currentTimeMillis()) {
						if (isHere) {
							// Teleport sender to target
							cmdSender.teleport(targetPlayer);
							senderList.remove(targetPlayer.getUniqueId());
							return true;
						} 
						
						// Teleport target to sender
						targetPlayer.teleport(cmdSender);
						senderList.remove(targetPlayer.getUniqueId());
						return true;
									
					}
				}
			}
						
			// Else
			cmdSender.sendMessage(ChatColor.YELLOW + "You have no active requests from this player");
			return true;
		}
		
		
		// Else
		return false;
	}

}




