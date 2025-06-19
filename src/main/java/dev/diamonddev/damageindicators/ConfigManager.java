package dev.diamonddev.damageindicators;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigManager {
    private final JavaPlugin plugin;

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
    }

    public String getDamageMessage(double damage, String damageType) {
        String format = plugin.getConfig().getString("damage." + damageType, getDefaultMessage(damageType));
        return ChatColor.translateAlternateColorCodes('&', format.replace("{damage}", String.format("%.1f", damage)));
    }

    private String getDefaultMessage(String damageType) {
        return null;
    }

    public String getHealthMessage(double health, double maxHealth, double damage) {
        String format = plugin.getConfig().getString("health.format", "&c❤ {health}&7/&c{max_health} &8(▼ {damage})");
        return ChatColor.translateAlternateColorCodes('&',
                format.replace("{health}", String.format("%.1f", health))
                        .replace("{max_health}", String.format("%.1f", maxHealth))
                        .replace("{damage}", String.format("%.1f", damage))
        );
    }

    public String getDeathSkullText(String playerName) {
        String text = plugin.getConfig().getString("death.text", "&4☠ {player}");
        return ChatColor.translateAlternateColorCodes('&', text.replace("{player}", playerName));
    }

    public int getDeathSkullDisplayTime() {
        return plugin.getConfig().getInt("death.display-time", 100);
    }

    public int getDisplayTime() {
        return plugin.getConfig().getInt("settings.display-time", 30);
    }

    public double getYOffset() {
        return plugin.getConfig().getDouble("settings.y-offset", 1);
    }
}