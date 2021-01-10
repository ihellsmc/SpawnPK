package me.ihellsmc.spawnpk.framework.config;

import me.ihellsmc.spawnpk.SpawnPK;
import me.ihellsmc.spawnpk.utils.CC;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class ConfigurationFile  {

    private final File file;
    private final YamlConfiguration configuration;
    private final String name;

    public ConfigurationFile(final JavaPlugin plugin, final String name, final boolean overwrite) {
        file = new File(plugin.getDataFolder(), name + ".yml");
        plugin.saveResource(name + ".yml", overwrite);
        configuration = YamlConfiguration.loadConfiguration(file);
        this.name = name;
    }

    public ConfigurationFile(final String name) {
        this(SpawnPK.getInstance(), name, false);
    }

    public String getString(final String path) {
        if (configuration.contains(path)) {
            return CC.trns(Objects.requireNonNull(configuration.getString(path)));
        }
        return null;
    }

    public String getStringOrDefault(final String path, final String or) {
        final String toReturn = getString(path);
        return (toReturn == null) ? or : toReturn;
    }

    public int getInteger(final String path) {
        if (configuration.contains(path)) {
            return configuration.getInt(path);
        }
        return 0;
    }

    public boolean getBoolean(final String path) {
        return configuration.contains(path) && configuration.getBoolean(path);
    }

    public double getDouble(final String path) {
        if (configuration.contains(path)) {
            return configuration.getDouble(path);
        }
        return 0.0;
    }

    public Object get(final String path) {
        if (configuration.contains(path)) {
            return configuration.get(path);
        }
        return null;
    }

    public void set(final String path, final Object value) {
        configuration.set(path, value);
    }

    public List<String> getStringList(final String path) {
        if (configuration.contains(path)) {
            return CC.trns(configuration.getStringList(path));
        }
        return null;
    }

    public Set<String> getKeys(final boolean deep) {
        return configuration.getKeys(deep);
    }

    public File getFile() {
        return file;
    }

    public YamlConfiguration getConfig() {
        return configuration;
    }

    public void reloadConfig() {
        try {
            configuration.load(file);
        } catch (IOException | InvalidConfigurationException ignored) {}
    }

    public void saveConfig() {
        try {
            configuration.save(file);
        } catch (IOException ignored) {}
    }

    public String getName() {
        return name;
    }
}
