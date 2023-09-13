package endermcx.magiccarpet;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;

public class CancelCarpetPop implements Listener {

    @EventHandler
    public void CarpetPop(BlockPhysicsEvent e) {
        if (e.getBlock().getType().equals(Material.CARPET)) {
            e.setCancelled(true);
        }
    }
}
