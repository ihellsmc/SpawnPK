package me.ihellsmc.spawnpk.gui.listener;

import me.ihellsmc.spawnpk.SpawnPK;
import me.ihellsmc.spawnpk.utils.CC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GUIListener implements Listener {

    private final SpawnPK core = SpawnPK.getInstance();

    @EventHandler
    public void onGUIClick(InventoryClickEvent e) {
        if (e.getClickedInventory().getTitle().equals(CC.trns("&c&lEnable effects?"))) {
            e.setCancelled(true);
            switch (e.getSlot()) {
                case 12:
                    core.getActiveManager().selectInitialJump((Player) e.getWhoClicked(), true);
                    break;
                case 14:
                    core.getActiveManager().selectInitialJump((Player) e.getWhoClicked(), false);
                    break;
                default:
                    return;
            }
            e.getWhoClicked().closeInventory();
        }
    }

}
