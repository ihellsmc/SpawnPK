package me.ihellsmc.spawnpk.active.listener;

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

                e.getPlayer().teleport(e.getPlayer().getLocation().add(distanced(15), 30, distanced(15)));
                e.getPlayer().getLocation().getBlock().getRelative(0, -1, 0).setType(Material.BOOKSHELF);

                core.getActiveManager().selectInitialJump(e.getPlayer(), e.getPlayer().getLocation());
            }
        }
    }

    private int distanced(int blocks) {
        if (core.getActiveManager().getActive().keySet().isEmpty()) return 0;
        int n = blocks * core.getActiveManager().getActive().keySet().size();
        return core.getActiveManager().getActive().keySet().size() % 2 == 0 ? n : -n;
    }

}
