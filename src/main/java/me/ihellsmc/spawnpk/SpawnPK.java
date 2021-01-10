package me.ihellsmc.spawnpk;

import lombok.Getter;
import lombok.Setter;
import me.ihellsmc.spawnpk.active.manager.ActiveManager;
import me.ihellsmc.spawnpk.commands.TestCommand;
import me.ihellsmc.spawnpk.framework.command.QuarkFramework;
import me.ihellsmc.spawnpk.framework.config.manager.ConfigManager;
import me.ihellsmc.spawnpk.jumps.listener.StartListener;
import me.ihellsmc.spawnpk.jumps.manager.JumpManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter @Setter
public class SpawnPK extends JavaPlugin {

    @Getter public static SpawnPK instance;

    private QuarkFramework framework;

    private ConfigManager configManager;
    private ActiveManager activeManager;
    private JumpManager jumpManager;

    @Override
    public void onEnable() {
        instance = this;
        framework = new QuarkFramework(this);

        registerManagers();
        registerListeners();
        registerCommands();

    }

    @Override
    public void onDisable() {

    }

    protected void registerManagers() {
        configManager = new ConfigManager();
        activeManager = new ActiveManager();
        jumpManager = new JumpManager();
    }

    protected void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new StartListener(), this);
    }

    protected void registerCommands() {
        new TestCommand(framework);
    }

}
