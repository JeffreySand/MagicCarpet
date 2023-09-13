package endermcx.magiccarpet;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CarpetMovement extends BukkitRunnable {
    Player player;
    Block[] blocks;
    Location playerStartLocation;
    boolean isFalling;

    public CarpetMovement(Player player, Location playerStartLocation, Block[] blocks) {
        this.player = player;
        this.blocks = blocks;
        this.playerStartLocation = playerStartLocation;
    }

    @Override
    public void run() {

        Material[] types = new Material[blocks.length];
        Byte[] data = new Byte[blocks.length];
        Location[] newLocations = new Location[blocks.length];
        boolean isAllowed = true;

        for (int i =0; i<blocks.length; i++) {
            types[i] = blocks[i].getType();
            data[i] = blocks[i].getData();
            player.getWorld().getBlockAt(blocks[i].getLocation()).setType(Material.AIR);
        }

        if (isFalling) {

            for (int i=0; i<blocks.length; i++) {
                Location originalLoc = blocks[i].getLocation();
                newLocations[i] = new Location(player.getWorld(),
                        originalLoc.getBlockX(),
                        originalLoc.getBlockY()-1,
                        originalLoc.getBlockZ()
                        );

                if (!newLocations[i].getBlock().getType().equals(Material.AIR)) {
                    isAllowed = false;
                    break;
                }

            }

            if (!isAllowed) {
                for (int i = 0; i < blocks.length; i++) {
                    player.getWorld().getBlockAt(blocks[i].getLocation()).setType(types[i]);
                    blocks[i].setData(data[i]);
                }
                Bukkit.getScheduler().cancelTask(this.getTaskId());
            }

            else {
                for (int i = 0; i < blocks.length; i++) {
                    player.getWorld().getBlockAt(newLocations[i]).setType(types[i]);
                    player.getWorld().getBlockAt(newLocations[i]).setData(data[i]);
                    blocks[i] = player.getWorld().getBlockAt(newLocations[i]);
                }
            }
        }

        else {

            for (int i = 0; i < blocks.length; i++) {
                Location originalLoc = blocks[i].getLocation();

                if (player.isSneaking()) {
                    newLocations[i] = new Location(player.getWorld(),
                            originalLoc.getX() + (player.getLocation().getBlockX() - playerStartLocation.getBlockX()),
                            originalLoc.getY() + (player.getLocation().getBlockY() - playerStartLocation.getBlockY() - 1),
                            originalLoc.getZ() + (player.getLocation().getBlockZ() - playerStartLocation.getBlockZ())
                    );
                }
                else {
                    newLocations[i] = new Location(player.getWorld(),
                            originalLoc.getX() + (player.getLocation().getBlockX() - playerStartLocation.getBlockX()),
                            originalLoc.getY() + (player.getLocation().getBlockY() - playerStartLocation.getBlockY()),
                            originalLoc.getZ() + (player.getLocation().getBlockZ() - playerStartLocation.getBlockZ())
                    );
                }

                if (!player.getWorld().getBlockAt(newLocations[i]).getType().equals(Material.AIR)) {
                    isAllowed = false;
                    break;
                }
            }

            if (isAllowed) {
                for (int i = 0; i < blocks.length; i++) {
                    player.getWorld().getBlockAt(newLocations[i]).setType(types[i]);
                    player.getWorld().getBlockAt(newLocations[i]).setData(data[i]);
                    blocks[i] = player.getWorld().getBlockAt(newLocations[i]);

                    if (player.isSneaking()) {
                        playerStartLocation = new Location(player.getWorld(),
                                player.getLocation().getBlockX(),
                                player.getLocation().getBlockY() - 1,
                                player.getLocation().getBlockZ());
                    } else {
                        playerStartLocation = player.getLocation();
                    }
                }
            }

            else {
                for (int i = 0; i < blocks.length; i++) {
                    player.getWorld().getBlockAt(blocks[i].getLocation()).setType(types[i]);
                    blocks[i].setData(data[i]);
                }
                playerStartLocation = player.getLocation();
            }
        }
    }
}




//package endermcx.magiccarpet;
//
//        import org.bukkit.Location;
//        import org.bukkit.Material;
//        import org.bukkit.block.Block;
//        import org.bukkit.entity.Player;
//        import org.bukkit.scheduler.BukkitRunnable;
//
//public class CarpetMovement extends BukkitRunnable {
//    Player player;
//    Block[] blocks;
//    Location playerStartLocation;
//
//    public CarpetMovement(Player player, Location playerStartLocation, Block[] blocks) {
//        this.player = player;
//        this.blocks = blocks;
//        this.playerStartLocation = playerStartLocation;
//    }
//
//    @Override
//    public void run() {
//        Material[] types = new Material[blocks.length];
//        Byte[] data = new Byte[blocks.length];
//        Location[] newLocations = new Location[blocks.length];
//        boolean isAllowed = true;
//
//        for (int i =0; i<blocks.length; i++) {
//            types[i] = blocks[i].getType();
//            data[i] = blocks[i].getData();
//            player.getWorld().getBlockAt(blocks[i].getLocation()).setType(Material.AIR);
//        }
//
//        for (int i=0; i<blocks.length; i++) {
//            Location originalLoc = blocks[i].getLocation();
//
//            if (player.isSneaking()) {
//                newLocations[i] = new Location(player.getWorld(),
//                        originalLoc.getX() + (player.getLocation().getBlockX() - playerStartLocation.getBlockX()),
//                        originalLoc.getY() + (player.getLocation().getBlockY() - playerStartLocation.getBlockY() - 1),
//                        originalLoc.getZ() + (player.getLocation().getBlockZ() - playerStartLocation.getBlockZ())
//                );
//            }
//            else {
//                newLocations[i] = new Location(player.getWorld(),
//                        originalLoc.getX() + (player.getLocation().getBlockX() - playerStartLocation.getBlockX()),
//                        originalLoc.getY() + (player.getLocation().getBlockY() - playerStartLocation.getBlockY()),
//                        originalLoc.getZ() + (player.getLocation().getBlockZ() - playerStartLocation.getBlockZ())
//                );
//            }
//
//            if (!player.getWorld().getBlockAt(newLocations[i]).getType().equals(Material.AIR)) {
//                isAllowed = false;
//                player.sendMessage("NOT ALLOWED");
//                break;
//            }
//        }
//
//        if (isAllowed) {
//            for (int i=0; i < blocks.length; i++) {
//                player.getWorld().getBlockAt(newLocations[i]).setType(types[i]);
//                player.getWorld().getBlockAt(newLocations[i]).setData(data[i]);
//                blocks[i] = player.getWorld().getBlockAt(newLocations[i]);
//
//                if (player.isSneaking()) {
//                    playerStartLocation = new Location(player.getWorld(),
//                            player.getLocation().getBlockX(),
//                            player.getLocation().getBlockY()-1,
//                            player.getLocation().getBlockZ());
//                }
//
//                else {
//                    playerStartLocation = player.getLocation();
//                }
//            }
//        }
//
//        else {
//            for (int i =0; i<blocks.length; i++) {
//                player.getWorld().getBlockAt(blocks[i].getLocation()).setType(types[i]);
//                blocks[i].setData(data[i]);
//
//                playerStartLocation = player.getLocation();
//            }
//        }
//    }
//}
