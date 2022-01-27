


package online.refract.ServerUtils;

import org.bukkit.Material;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

public class FrameInvisible implements Listener{
	
	// constructor - allows reference of main class through "plugin" 
	static Main plugin;
	public FrameInvisible(Main instance) {
		plugin = instance;
	}
	
	// allows right click on nonempty item frame to toggle visibility 
	@EventHandler
	public void onShearItemFrame(PlayerInteractEntityEvent event) {
		if (event.getRightClicked() instanceof ItemFrame) {
			if(event.getPlayer().getInventory().getItemInMainHand().getType() == Material.SHEARS) {
				ItemFrame frame = (ItemFrame) event.getRightClicked();
				// if item frame is not empty
				if (!(frame.getItem().getType() == Material.AIR)) {
					event.setCancelled(true);
					
					// Toggles frame visibility
					frame.setVisible(!frame.isVisible());

						
					// Reduces shears durability by 1
					ItemStack item = event.getPlayer().getInventory().getItemInMainHand(); 
					Damageable meta = (Damageable) item.getItemMeta();
					meta.setDamage(meta.getDamage() + 1);	
					if (meta.getDamage() > 237) {
						event.getPlayer().getInventory().getItemInMainHand().setAmount(0);
					}
					item.setItemMeta(meta);
					
								
				}
				
			}
		}
		
	}
	
	// sets item frame to visible when item is removed
	@EventHandler
	public void onEmptyItemFrame(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof ItemFrame) {
			ItemFrame frame = (ItemFrame) event.getEntity();
			frame.setVisible(true);

		}
			
	}

}
