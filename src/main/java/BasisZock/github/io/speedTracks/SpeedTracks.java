package BasisZock.github.io.speedTracks;

import BasisZock.github.io.speedTracks.command.SpeedTracksCommand;
import BasisZock.github.io.speedTracks.listener.MinecartSpeedListener;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;

public final class SpeedTracks extends JavaPlugin {

    public static final double MINECRAFT_DEFAULT_SPEED = 0.4;
    private final Map<Material, Double> speedMultiplierMap = new HashMap<>();

    @Override
    public void onEnable() {
        loadConfiguration();
        registerListeners();
        registerCommands();
        getLogger().info("SpeedTracks v" + getPluginMeta().getVersion() + " has been enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("SpeedTracks has been disabled.");
    }

    public void loadConfiguration() {
        saveDefaultConfig();
        reloadConfig();
        speedMultiplierMap.clear();

        ConfigurationSection speedBlocksSection = getConfig().getConfigurationSection("speed-blocks");
        if (speedBlocksSection == null) {
            getLogger().warning("The 'speed-blocks' section was not found in config.yml. No custom speeds will be applied.");
            return;
        }

        for (String key : speedBlocksSection.getKeys(false)) {
            try {
                Material material = Material.valueOf(key.toUpperCase());
                double multiplier = speedBlocksSection.getDouble(key);

                if (multiplier < 0) {
                    getLogger().warning("Speed multiplier for " + key + " is negative (" + multiplier + "). This is not allowed. Skipping.");
                    continue;
                }
                speedMultiplierMap.put(material, multiplier);
            } catch (IllegalArgumentException e) {
                getLogger().warning("Invalid material name in config.yml: '" + key + "'. Please check Paper Material names.");
            } catch (Exception e) {
                getLogger().log(Level.SEVERE, "An unexpected error occurred while parsing '" + key + "' from config.yml.", e);
            }
        }
        getLogger().info("Successfully loaded " + speedMultiplierMap.size() + " custom speed track blocks.");
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new MinecartSpeedListener(this), this);
    }

    private void registerCommands() {
        SpeedTracksCommand commandExecutor = new SpeedTracksCommand(this);
        Objects.requireNonNull(getCommand("speedtracks")).setExecutor(commandExecutor);
        Objects.requireNonNull(getCommand("speedtracks")).setTabCompleter(commandExecutor);
    }

    public Map<Material, Double> getSpeedMultiplierMap() {
        return speedMultiplierMap;
    }
}