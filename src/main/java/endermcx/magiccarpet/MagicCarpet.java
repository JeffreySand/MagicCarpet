package endermcx.magiccarpet;

import org.bukkit.plugin.java.JavaPlugin;

public final class MagicCarpet extends JavaPlugin {

    private static MagicCarpet plugin;

    @Override
    public void onEnable() {
        plugin = this;
        getServer().getPluginManager().registerEvents(new CarpetTest(), this);
        getServer().getPluginManager().registerEvents(new CancelCarpetPop(), this);

    }

    public static MagicCarpet getPlugin() {
        return plugin;
    }
}