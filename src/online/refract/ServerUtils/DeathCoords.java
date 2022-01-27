package online.refract.ServerUtils;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathCoords implements Listener{
	
	// constructor - allows reference of main class through "plugin" 
	static Main plugin;
	public DeathCoords(Main instance) {
		plugin = instance;
	}
	
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player player = (Player) event.getEntity();
		Location loc = player.getLocation();
		player.sendMessage("You died at " + ChatColor.GOLD + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ());
	}
}



