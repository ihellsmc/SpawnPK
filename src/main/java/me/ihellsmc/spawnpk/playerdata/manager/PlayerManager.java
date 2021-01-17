package me.ihellsmc.spawnpk.playerdata.manager;

import lombok.Getter;
import lombok.Setter;
import me.ihellsmc.spawnpk.SpawnPK;
import me.ihellsmc.spawnpk.playerdata.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter @Setter
public class PlayerManager {

    private final SpawnPK core = SpawnPK.getInstance();

    public Set<PlayerData> registered = new HashSet<>();
    private final YamlConfiguration data = core.getConfigManager().getFile("player-data").getConfig();

    public PlayerManager() {
        for (Player player : Bukkit.getOnlinePlayers()) register(player.getUniqueId());
    }

    public void register(UUID uuid) {
        PlayerData pd = get(uuid) != null ? get(uuid) : new PlayerData(uuid);
        if (data.getConfigurationSection("").getKeys(false).contains(uuid.toString())) {
            pd.setHighscore(data.getInt(uuid.toString()));
        }
        registered.add(pd);
    }

    public void remove(UUID uuid) {
        PlayerData pd = get(uuid); pd.clear();
        registered.remove(pd);
    }

    public PlayerData get(UUID uuid) {
        for (PlayerData data : registered) if (data.getUuid().equals(uuid)) return data;
        return null;
    }

}
