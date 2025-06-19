package dev.diamonddev.damageindicators;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class DamageListener implements Listener {
    private final Main plugin;
    private final HologramManager hologramManager;
    private final ConfigManager config;

    public DamageListener(Main plugin) {
        this.plugin = plugin;
        this.hologramManager = plugin.getHologramManager();
        this.config = plugin.getConfigManager();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof LivingEntity)) return;
        if (event.isCancelled()) return;

        LivingEntity victim = (LivingEntity) event.getEntity();
        double damage = event.getFinalDamage();
        if (damage < 0.5) return;

        String damageType = getDamageType(event);

        new BukkitRunnable() {
            @Override
            public void run() {
                showDamageIndicator(victim, damage, damageType);

                if (victim instanceof Player) {
                    showHealthIndicator((Player) victim, damage);
                }
            }
        }.runTaskLater(plugin, 1);
    }

    private void showDamageIndicator(LivingEntity entity, double damage, String damageType) {
        String message = config.getDamageMessage(damage, damageType);
        Location loc = entity.getLocation().add(0, config.getYOffset(), 0);
        hologramManager.createHologram(loc, message, config.getDisplayTime());
    }

    private void showHealthIndicator(Player player, double damageDealt) {
        String healthText = config.getHealthMessage(
                player.getHealth(),
                player.getMaxHealth(),
                damageDealt
        );
        hologramManager.createHologram(
                player.getEyeLocation(),
                healthText,
                20
        );
    }

    private String getDamageType(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && isCriticalHit((Player) event.getDamager())) {
            return "critical";
        }
        return "normal";
    }

    private boolean isCriticalHit(Player player) {
        return player.getFallDistance() > 0 && !player.isOnGround();
    }
}