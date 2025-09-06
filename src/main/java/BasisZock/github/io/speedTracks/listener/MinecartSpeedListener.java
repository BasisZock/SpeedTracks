package BasisZock.github.io.speedTracks.listener;

import BasisZock.github.io.speedTracks.SpeedTracks;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Minecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleMoveEvent;

public class MinecartSpeedListener implements Listener {

    private final SpeedTracks plugin;

    public MinecartSpeedListener(SpeedTracks plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void onMinecartMove(VehicleMoveEvent event) {
        if (!(event.getVehicle() instanceof Minecart minecart)) {
            return;
        }

        Material materialUnderRail = event.getTo().getBlock().getRelative(BlockFace.DOWN).getType();
        double speedMultiplier = plugin.getSpeedMultiplierMap().getOrDefault(materialUnderRail, 1.0);
        double targetSpeed = SpeedTracks.MINECRAFT_DEFAULT_SPEED * speedMultiplier;

        if (minecart.getMaxSpeed() != targetSpeed) {
            minecart.setMaxSpeed(targetSpeed);
        }
    }
}