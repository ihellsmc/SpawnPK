package me.ihellsmc.spawnpk.listeners;

import me.ihellsmc.spawnpk.SpawnPK;
import me.ihellsmc.spawnpk.utils.CC;
import org.bukkit.Material;
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
                e.getPlayer().sendMessage(CC.trns("&eTeleporting..."));

                e.getPlayer().teleport(e.getPlayer().getLocation().add(20 * core.getJumpManager().getActivePlayers().size(), 30, 0));
                e.getPlayer().getLocation().getBlock().getRelative(0, -1, 0).setType(Material.BOOKSHELF);

                core.getJumpManager().getActivePlayers().add(e.getPlayer().getUniqueId());

            }
        }
    }

}
