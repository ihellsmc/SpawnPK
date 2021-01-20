package me.ihellsmc.spawnpk.gui;

import lombok.Getter;
import lombok.Setter;
import me.ihellsmc.spawnpk.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

@Getter @Setter
public class EffectGUI {

    public Inventory gui;

    public EffectGUI() {

        gui = Bukkit.createInventory(null, 27, CC.trns("&c&lEnable effects?"));

        ItemStack filler = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7);
        ItemMeta fillerMeta = filler.getItemMeta();
        fillerMeta.setDisplayName(" ");
        filler.setItemMeta(fillerMeta);

        ItemStack yes = new ItemStack(Material.INK_SACK, 1, (byte) 10);
        ItemMeta yesMeta = yes.getItemMeta();
        yesMeta.setDisplayName(CC.trns("&aClick to enable speed effect!"));
        yesMeta.setLore(Collections.singletonList(CC.trns("&cYou will only get half points!")));
        yes.setItemMeta(yesMeta);

        ItemStack no = new ItemStack(Material.INK_SACK, 1, (byte) 1);
        ItemMeta noMeta = no.getItemMeta();
        noMeta.setDisplayName(CC.trns("&cClick to disable speed effect!"));
        noMeta.setLore(Collections.singletonList(CC.trns("&7You will get the full amount of points!")));
        no.setItemMeta(noMeta);

        gui.setItem(12, yes);
        gui.setItem(14, no);

        for (int i = 0; i < gui.getSize(); i++) {
            if (gui.getItem(i) == null || gui.getItem(i).getType() == Material.AIR) gui.setItem(i, filler);
        }

    }

}
