package me.ihellsmc.spawnpk;

import lombok.Getter;
import lombok.Setter;
import me.ihellsmc.spawnpk.active.listener.JumpListener;
import me.ihellsmc.spawnpk.active.manager.ActiveManager;
import me.ihellsmc.spawnpk.commands.TestCommand;
import me.ihellsmc.spawnpk.framework.command.QuarkFramework;
import me.ihellsmc.spawnpk.framework.config.manager.ConfigManager;
import me.ihellsmc.spawnpk.active.listener.StartListener;
import me.ihellsmc.spawnpk.gui.EffectGUI;
import me.ihellsmc.spawnpk.gui.listener.GUIListener;
import me.ihellsmc.spawnpk.jumps.manager.JumpManager;
import me.ihellsmc.spawnpk.playerdata.listener.ConnectionListener;
import me.ihellsmc.spawnpk.playerdata.manager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter @Setter
public class SpawnPK extends JavaPlugin {

    @Getter public static SpawnPK instance;

    private QuarkFramework framework;

    private ConfigManager configManager;
    private PlayerManager playerManager;
    private EffectGUI effectGUI;
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
        playerManager = new PlayerManager();
        effectGUI = new EffectGUI();
        activeManager = new ActiveManager();
        jumpManager = new JumpManager();
    }

    protected void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new StartListener(), this);
        Bukkit.getPluginManager().registerEvents(new JumpListener(), this);
        Bukkit.getPluginManager().registerEvents(new ConnectionListener(), this);
        Bukkit.getPluginManager().registerEvents(new GUIListener(), this);
    }

    protected void registerCommands() {
        new TestCommand(framework);
    }

}
