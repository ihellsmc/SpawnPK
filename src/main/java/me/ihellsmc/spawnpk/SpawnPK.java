package me.ihellsmc.spawnpk;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter @Setter
public class SpawnPK extends JavaPlugin {

    @Getter public static SpawnPK instance;

    @Override
    public void onEnable() {
        instance = this;

    }

    @Override
    public void onDisable() {

    }

}
