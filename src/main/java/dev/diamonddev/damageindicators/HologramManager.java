package dev.diamonddev.damageindicators;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;

public class HologramManager {
    private final Main plugin;

    public HologramManager(Main plugin) {
        this.plugin = plugin;
    }

    public void createHologram(Location location, String text, int displayTicks) {
        try {
            ArmorStand hologram = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);

            hologram.setVisible(false);
            hologram.setGravity(false);
            hologram.setCustomNameVisible(true);
            hologram.setCustomName(text);
            hologram.setInvulnerable(true);
            hologram.setSmall(true);
            hologram.setMarker(true);
            hologram.setCollidable(false);
            hologram.setSilent(true);

            new BukkitRunnable() {
                @Override
                public void run() {
                    if (hologram.isValid()) {
                        hologram.remove();
                    }
                }
            }.runTaskLater(plugin, displayTicks);

        } catch (Exception e) {
            plugin.getLogger().warning("Ошибка при создании голограммы: " + e.getMessage());
        }
    }
}