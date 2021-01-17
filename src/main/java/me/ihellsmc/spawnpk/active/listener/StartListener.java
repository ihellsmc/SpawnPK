package me.ihellsmc.spawnpk.active.listener;

import me.ihellsmc.spawnpk.SpawnPK;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class StartListener implements Listener {

    private final SpawnPK core = SpawnPK.getInstance();

    @EventHandler
    public void onStart(PlayerInteractEvent e) {
        if (e.getAction() == Action.PHYSICAL) {
            if (core.getJumpManager().getPlateLocation() == null) return;
            if (e.getClickedBlock().getLocation().equals(core.getJumpManager().getPlateLocation())) {
                e.getPlayer().openInventory(core.getEffectGUI().getGui());
            }
        }
    }

}
