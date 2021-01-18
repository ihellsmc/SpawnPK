package me.ihellsmc.spawnpk.active.listener;

import lombok.Getter;
import lombok.Setter;
import me.ihellsmc.spawnpk.SpawnPK;
import me.ihellsmc.spawnpk.active.data.ActiveData;
import me.ihellsmc.spawnpk.playerdata.PlayerData;
import me.ihellsmc.spawnpk.utils.CC;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;

@Getter @Setter
public class JumpListener implements Listener {

    private final SpawnPK core = SpawnPK.getInstance();

    @EventHandler
    public void onJump(PlayerMoveEvent e) {
        if (core.getActiveManager().getCurrent().contains(e.getPlayer().getUniqueId())) {
            PlayerData pd = core.getPlayerManager().get(e.getPlayer().getUniqueId());
            ActiveData data = pd.getData();

            if (e.getPlayer().getLocation().getBlock().getRelative(0, -1, 0).getLocation().equals(data.getBlockTo().getLocation())) {

                core.getActiveManager().selectNewJump(data);
                e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ITEM_PICKUP, 1, 2);
                e.getPlayer().playEffect(data.getBlockTo().getLocation(), Effect.MOBSPAWNER_FLAMES, Integer.MAX_VALUE);

            } else if (e.getPlayer().getLocation().getBlockY() + 2 < data.getBlockTo().getY()) {
                e.getPlayer().sendMessage(CC.trns("&c&lYOU DIED! &7You scored a total of &e" + data.getPoints() + "&7 jumps!"));

                data.getBlockFrom().setType(Material.AIR);
                data.getBlockTo().setType(Material.AIR);
                data.getOtherBlocks().forEach(b -> b.setType(Material.AIR));

                for (PotionEffect effect : e.getPlayer().getActivePotionEffects()) e.getPlayer().removePotionEffect(effect.getType());

                pd.clearActiveData();
                core.getActiveManager().getCurrent().remove(e.getPlayer().getUniqueId());
            }

        }
    }

}
