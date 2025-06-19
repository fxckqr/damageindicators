package dev.diamonddev.damageindicators;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private ConfigManager configManager;
    private HologramManager hologramManager;
    private DamageIndicator damageIndicator;
    @Override
    public void onEnable() {
        this.configManager = new ConfigManager(this);
        this.hologramManager = new HologramManager(this);
        this.damageIndicator = new DamageIndicator(this);

        new DamageListener(this);
        new DeathListener(this);
        getLogger().info("DamageIndicators loaded successfully!");
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public HologramManager getHologramManager() {
        return hologramManager;
    }
}