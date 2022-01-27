package online.refract.ServerUtils;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	
	Map<UUID, Map<UUID, Long>> tpaList = new HashMap<UUID, Map<UUID, Long>>();
	Map<UUID, Map<UUID, Long>> tpaHereList = new HashMap<UUID, Map<UUID, Long>>();

	@Override
	public void onEnable() {
		

		
		// Register EventListeners
		this.getServer().getPluginManager().registerEvents(new DeathCoords(this), this);
		this.getServer().getPluginManager().registerEvents(new FrameInvisible(this), this);
		
		// Register CommandExecutors
		this.getCommand("tpa").setExecutor(new TPA(this));
		this.getCommand("tpaccept").setExecutor(new TPA(this));
		this.getCommand("tpahere").setExecutor(new TPA(this));
		

	}
	
	@Override
	public void onDisable() {
			
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		// For testing
		if (label.equalsIgnoreCase("dev")) {
			if (!(sender instanceof Player)) {
				try {
					//Player player = Bukkit.getPlayer(args[0]);
					// code goes here
					return true;
					
				} catch (Exception e) {
					sender.sendMessage(ChatColor.RED + "Usage: /dev <player>");
					return false;
				}
			} 
			sender.sendMessage(ChatColor.RED + "You do not have permission to execute this command");
		}
		return false;
		
	}
	
	
	

	
	
	
}

