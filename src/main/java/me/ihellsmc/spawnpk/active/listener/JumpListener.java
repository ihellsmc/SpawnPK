package me.ihellsmc.spawnpk.active.listener;

import lombok.Getter;
import lombok.Setter;
import me.ihellsmc.spawnpk.SpawnPK;
import me.ihellsmc.spawnpk.active.data.ActiveData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

@Getter @Setter
public class JumpListener implements Listener {

    private final SpawnPK core = SpawnPK.getInstance();

    @EventHandler
    public void onJump(PlayerMoveEvent e) {
        if (core.getActiveManager().getActive().containsKey(e.getPlayer().getUniqueId())) {

            ActiveData data = core.getActiveManager().getActive().get(e.getPlayer().getUniqueId());
            if (e.getPlayer().getLocation().getBlock().getRelative(0, -1, 0) == data.getBlockTo()) {
                core.getActiveManager().selectNewJump(data);
            }

        }
    }

}
