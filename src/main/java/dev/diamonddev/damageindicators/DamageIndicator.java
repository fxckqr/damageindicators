package dev.diamonddev.damageindicators;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class DamageIndicator {
    private final Main plugin;
    private final ConfigManager config;
    private final HologramManager hologramManager;

    public DamageIndicator(Main plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfigManager();
        this.hologramManager = plugin.getHologramManager();
    }

    public void displayDamage(LivingEntity entity, double damage, String damageType) {
        String message = config.getDamageMessage(damage, damageType);
        Location loc = entity.getLocation().add(0, config.getYOffset(), 0);
        hologramManager.createHologram(loc, message, config.getDisplayTime());
    }

    public void showHealthAfterHit(Player player, double damageDealt) {
        double newHealth = player.getHealth();
        double maxHealth = player.getMaxHealth();
        String healthText = config.getHealthMessage(newHealth, maxHealth, damageDealt);

        hologramManager.createHologram(
                player.getEyeLocation(),
                healthText,
                20
        );
    }
}