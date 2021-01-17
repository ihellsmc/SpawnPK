package me.ihellsmc.spawnpk.playerdata.listener;

import me.ihellsmc.spawnpk.SpawnPK;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionListener implements Listener {

    private final SpawnPK core = SpawnPK.getInstance();

    @EventHandler
    public void onConnect(PlayerJoinEvent e) {
        core.getPlayerManager().register(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onDisconnect(PlayerQuitEvent e) {
        core.getPlayerManager().remove(e.getPlayer().getUniqueId());
    }

}
