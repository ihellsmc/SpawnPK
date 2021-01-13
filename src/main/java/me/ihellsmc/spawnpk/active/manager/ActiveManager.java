package me.ihellsmc.spawnpk.active.manager;

import lombok.Getter;
import lombok.Setter;
import me.ihellsmc.spawnpk.SpawnPK;
import me.ihellsmc.spawnpk.active.data.ActiveData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Getter @Setter
public class ActiveManager {

    private final SpawnPK core = SpawnPK.getInstance();
    public HashMap<UUID, ActiveData> active = new HashMap<>();

    public void selectInitialJump(Player player) {
        Material material = core.getJumpManager().getRandomMaterial();
        player.getLocation().getBlock().getRelative(0, -1, 0).setType(material);

        if (active.containsKey(player.getUniqueId())) selectNewJump(active.get(player.getUniqueId()));

        ActiveData data = new ActiveData();
        data.setBlockFrom(player.getLocation().getBlock().getRelative(0, -1, 0));
        data.setBlock(material);

        List<int[]> potential = core.getJumpManager().getRandom();

        if (potential.size() == 1) {
            data.setBlockTo(data.getBlockFrom().getRelative(potential.get(0)[0], potential.get(0)[1], potential.get(0)[2]));
            data.setOtherBlocks(new ArrayList<>());
        } else {
            int[] last = potential.get(potential.size() - 1);
            data.setBlockTo(data.getBlockFrom().getRelative(last[0], last[1], last[2]));

            List<Block> otherToSet = new ArrayList<>();
            for (int i = 0; i < potential.size() - 2; i++) {
                otherToSet.add(data.getBlockFrom().getRelative(potential.get(i)[0], potential.get(i)[1], potential.get(i)[2]));
            }

            data.setOtherBlocks(otherToSet);
        }

        active.put(player.getUniqueId(), data);

        data.getBlockTo().setType(data.getBlock());
        data.getOtherBlocks().forEach(b -> b.setType(data.getBlock()));

    }

    public void selectNewJump(ActiveData data) {
        List<int[]> selected = core.getJumpManager().getRandom();

        data.setPoints(data.getPoints() + 1);

        data.getBlockFrom().setType(Material.AIR);
        data.getOtherBlocks().forEach(b -> b.setType(Material.AIR));
        data.getOtherBlocks().clear();
        data.setBlockFrom(data.getBlockTo());

        if (selected.size() == 1) {
            data.setBlockTo(data.getBlockFrom().getRelative(selected.get(0)[0], selected.get(0)[1], selected.get(0)[2]));
            data.setOtherBlocks(new ArrayList<>());
        } else {
            int[] last = selected.get(selected.size() - 1);
            data.setBlockTo(data.getBlockFrom().getRelative(last[0], last[1], last[2]));

            List<Block> otherToSet = new ArrayList<>();
            for (int i = 0; i < selected.size() - 1; i++) {
                otherToSet.add(data.getBlockFrom().getRelative(selected.get(i)[0], selected.get(i)[1], selected.get(i)[2]));
            }

            data.setOtherBlocks(otherToSet);
        }

        data.getBlockTo().setType(data.getBlock());
        data.getOtherBlocks().forEach(b -> b.setType(data.getBlock()));
    }

}
