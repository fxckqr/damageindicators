package dev.diamonddev.damageindicators;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {
    private final Main plugin;
    private final HologramManager hologramManager;
    private final ConfigManager config;

    public DeathListener(Main plugin) {
        this.plugin = plugin;
        this.hologramManager = plugin.getHologramManager();
        this.config = plugin.getConfigManager();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Location deathLocation = player.getLocation().add(0, 0.5, 0); // Поднимаем немного выше

        hologramManager.createHologram(
                deathLocation,
                config.getDeathSkullText(player.getName()),
                config.getDeathSkullDisplayTime()
        );
    }
}