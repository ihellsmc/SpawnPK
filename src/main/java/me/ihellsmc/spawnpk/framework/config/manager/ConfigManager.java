package me.ihellsmc.spawnpk.framework.config.manager;

import me.ihellsmc.spawnpk.framework.config.ConfigurationFile;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Getter @Setter
public class ConfigManager {

    private final Set<ConfigurationFile> configs = new HashSet<>();

    public ConfigManager() {
        configs.add(new ConfigurationFile("config"));
        configs.add(new ConfigurationFile("player-data"));
    }

    public ConfigurationFile getFile(String name) {
        Optional<ConfigurationFile> opt = configs.stream().filter(config -> config.getName().equalsIgnoreCase(name)).findFirst();
        return opt.orElse(null);
    }

    public void saveAll() {
        for (ConfigurationFile config : this.configs) {
            config.saveConfig();
        }
    }

    public void reloadAll() {
        for (ConfigurationFile config : this.configs) {
            config.reloadConfig();
        }
    }

}
