package endermcx.magiccarpet;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class CarpetTest implements Listener {

    HashMap<UUID, CarpetMovement> playerMovementTasks = new HashMap<>();

    @EventHandler
    public void RightClick(PlayerInteractEvent event) {

        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            ItemStack itemStack = event.getItem();
            Material item = itemStack.getType();
            Block block = event.getClickedBlock();
            Player player = event.getPlayer();

            if (item.equals(Material.STICK) && block.getType().equals(Material.CARPET)) {

                if (playerMovementTasks.containsKey(player.getUniqueId())) {

                    playerMovementTasks.get(player.getUniqueId()).isFalling = true;

                    playerMovementTasks.remove(player.getUniqueId());
                }

                else {
                    ArrayList<Block> carpet = new ArrayList<>();

                    carpet.add(block);
                    boolean isCalculating = true;


                    while (isCalculating) {
                        int originalSize = carpet.size();

                        for (int i=0; i<originalSize; i++) {
                            Block selectedBlock = carpet.get(i);

                            int x = selectedBlock.getX();
                            int y = selectedBlock.getY();
                            int z = selectedBlock.getZ();

                            Block connectedBlock = player.getWorld().getBlockAt(x+1, y, z);
                            if (connectedBlock.getType().equals(Material.CARPET) && !carpet.contains(connectedBlock)) {
                                carpet.add(connectedBlock);
                            }

                            connectedBlock = player.getWorld().getBlockAt(x-1, y, z);
                            if (connectedBlock.getType().equals(Material.CARPET) && !carpet.contains(connectedBlock)) {
                                carpet.add(connectedBlock);
                            }

                            connectedBlock = player.getWorld().getBlockAt(x, y, z+1);
                            if (connectedBlock.getType().equals(Material.CARPET) && !carpet.contains(connectedBlock)) {
                                carpet.add(connectedBlock);
                            }

                            connectedBlock = player.getWorld().getBlockAt(x, y, z-1);
                            if (connectedBlock.getType().equals(Material.CARPET) && !carpet.contains(connectedBlock)) {
                                carpet.add(connectedBlock);
                            }
                        }

                        if (originalSize == carpet.size()) {
                            isCalculating = false;
                        }
                    }

                    Block[] carpetPassedIn = new Block[carpet.size()];

                    for (int i=0; i<carpet.size(); i++) {
                        carpetPassedIn[i] = carpet.get(i);
                    }

                    Location loc = player.getLocation();

                    CarpetMovement movement = new CarpetMovement(player, loc, carpetPassedIn);

                    movement.runTaskTimer(MagicCarpet.getPlugin(), 0, 2);

                    playerMovementTasks.put(player.getUniqueId(), movement);

                }
            }
        }
    }
}




//package endermcx.magiccarpet;
//
//        import org.bukkit.Bukkit;
//        import org.bukkit.Location;
//        import org.bukkit.Material;
//        import org.bukkit.block.Block;
//        import org.bukkit.entity.Player;
//        import org.bukkit.event.EventHandler;
//        import org.bukkit.event.Listener;
//        import org.bukkit.event.block.Action;
//        import org.bukkit.event.player.PlayerInteractEvent;
//        import org.bukkit.inventory.ItemStack;
//
//        import java.util.ArrayList;
//        import java.util.HashMap;
//        import java.util.UUID;
//
//public class CarpetTest implements Listener {
//
//    HashMap<UUID, Integer> playerCarpetMap = new HashMap<>();
//
//    @EventHandler
//    public void RightClick(PlayerInteractEvent event) {
//
//        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
//            ItemStack itemStack = event.getItem();
//            Material item = itemStack.getType();
//            Block block = event.getClickedBlock();
//            Player player = event.getPlayer();
//
//            player.sendMessage(String.valueOf(block.getData()));
//
//            if (item.equals(Material.STICK) && block.getType().equals(Material.CARPET)) {
//
//                if (playerCarpetMap.containsKey(player.getUniqueId())) {
//                    Bukkit.getScheduler().cancelTask(playerCarpetMap.get(player.getUniqueId()));
//
//                    playerCarpetMap.remove(player.getUniqueId());
//                }
//
//                else {
//                    ArrayList<Block> carpet = new ArrayList<>();
//
//                    carpet.add(block);
//                    boolean isCalculating = true;
//
//
//                    while (isCalculating) {
//                        int originalSize = carpet.size();
//
//                        for (int i=0; i<originalSize; i++) {
//                            Block selectedBlock = carpet.get(i);
//
//                            int x = selectedBlock.getX();
//                            int y = selectedBlock.getY();
//                            int z = selectedBlock.getZ();
//
//                            Block connectedBlock = player.getWorld().getBlockAt(x+1, y, z);
//                            if (connectedBlock.getType().equals(Material.CARPET) && !carpet.contains(connectedBlock)) {
//                                carpet.add(connectedBlock);
//                            }
//
//                            connectedBlock = player.getWorld().getBlockAt(x-1, y, z);
//                            if (connectedBlock.getType().equals(Material.CARPET) && !carpet.contains(connectedBlock)) {
//                                carpet.add(connectedBlock);
//                            }
//
//                            connectedBlock = player.getWorld().getBlockAt(x, y, z+1);
//                            if (connectedBlock.getType().equals(Material.CARPET) && !carpet.contains(connectedBlock)) {
//                                carpet.add(connectedBlock);
//                            }
//
//                            connectedBlock = player.getWorld().getBlockAt(x, y, z-1);
//                            if (connectedBlock.getType().equals(Material.CARPET) && !carpet.contains(connectedBlock)) {
//                                carpet.add(connectedBlock);
//                            }
//                        }
//
//                        if (originalSize == carpet.size()) {
//                            isCalculating = false;
//                        }
//                    }
//
//                    player.sendMessage(String.valueOf(carpet.size()));
//
//                    Block[] carpetPassedIn = new Block[carpet.size()];
//
//                    for (int i=0; i<carpet.size(); i++) {
//                        carpetPassedIn[i] = carpet.get(i);
//                    }
//
//                    Location loc = player.getLocation();
//
//                    int schedulerID = Bukkit.getScheduler().runTaskTimer(MagicCarpet.getPlugin(), new CarpetMovement(player, loc, carpetPassedIn), 0, 2).getTaskId();
//                    playerCarpetMap.put(player.getUniqueId(), schedulerID);
//                }
//            }
//        }
//    }
//}