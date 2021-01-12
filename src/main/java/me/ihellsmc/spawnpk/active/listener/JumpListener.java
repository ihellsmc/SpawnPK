package me.ihellsmc.spawnpk.active.listener;

import lombok.Getter;
import lombok.Setter;
import me.ihellsmc.spawnpk.SpawnPK;
import me.ihellsmc.spawnpk.active.data.ActiveData;
import me.ihellsmc.spawnpk.utils.CC;
import org.bukkit.Material;
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

            if (e.getPlayer().getLocation().getBlock().getRelative(0, -1, 0).getLocation().equals(data.getBlockTo().getLocation())) {

                core.getActiveManager().selectNewJump(data);

            } else if (e.getPlayer().getLocation().getBlockY() + 2 < data.getBlockTo().getY()) {
                e.getPlayer().sendMessage(CC.trns("&c&lYOU FAILED! &7You are dogshit!"));
                e.getPlayer().sendMessage(CC.trns("&eOn the bright side, you scored " + data.getPoints() + " jumps!"));

                data.getBlockFrom().setType(Material.AIR);
                data.getBlockTo().setType(Material.AIR);
                data.getOtherBlocks().forEach(b -> b.setType(Material.AIR));
                data.clear();

                core.getActiveManager().getActive().remove(e.getPlayer().getUniqueId());
            }

        }
    }

}
