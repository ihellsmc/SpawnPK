package me.ihellsmc.spawnpk.active.managers;

import lombok.Getter;
import lombok.Setter;
import me.ihellsmc.spawnpk.SpawnPK;
import me.ihellsmc.spawnpk.active.data.ActiveData;
import me.ihellsmc.spawnpk.parkourblock.ParkourBlock;
import me.ihellsmc.spawnpk.playerdata.PlayerData;
import me.ihellsmc.spawnpk.utils.CC;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter @Setter
public class ActiveManager {

    private final SpawnPK core = SpawnPK.getInstance();

    public List<UUID> current = new ArrayList<>();

    public void selectInitialJump(Player player, boolean speed) {
        PlayerData pd = core.getPlayerManager().get(player.getUniqueId());

        player.teleport(player.getLocation().add(distanced(15), 30, distanced(15)));

        Material material = core.getJumpManager().getMaterial(pd); // TODO: block selection

        if (pd.getData() != null) selectNewJump(pd.getData());

        ActiveData data = new ActiveData();

        player.getLocation().getBlock().getRelative(0, -1, 0).setType(material);
        data.setBlockFrom(player.getLocation().getBlock().getRelative(0, -1, 0));
        data.setBlock(material);

        setBlocks(data);

        data.setSpeed(speed);
        if (speed) player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1), true);

        pd.setData(data);
        current.add(player.getUniqueId());

    }

    public void selectNewJump(ActiveData data) {
        data.setPoints(data.getPoints() + 1);

        data.getBlockFrom().setType(Material.AIR);
        data.getOtherBlocks().forEach(b -> b.setType(Material.AIR));
        data.getOtherBlocks().clear();

        data.setBlockFrom(data.getBlockTo());

        setBlocks(data);
    }

    private int distanced(int blocks) {
        if (current.isEmpty()) return 0;
        int n = blocks * current.size();
        return current.size() % 2 == 0 ? n : -n;
    }

    private void setBlocks(ActiveData data) {

        List<ParkourBlock> blocks = core.getJumpManager().getRandom();

        if (blocks.size() == 1) {
            data.setBlockTo(data.getBlockFrom().getRelative(blocks.get(0).getRelative()[0], blocks.get(0).getRelative()[1], blocks.get(0).getRelative()[2]));
            set(data, data.getBlockFrom().getRelative(blocks.get(0).getRelative()[0], blocks.get(0).getRelative()[1], blocks.get(0).getRelative()[2]), blocks.get(0));
            data.setOtherBlocks(new ArrayList<>());
        } else {

            ParkourBlock last = blocks.get(blocks.size() - 1);
            data.setBlockTo(data.getBlockFrom().getRelative(last.getRelative()[0], last.getRelative()[1], last.getRelative()[2]));
            set(data, data.getBlockFrom().getRelative(last.getRelative()[0], last.getRelative()[1], last.getRelative()[2]), last);

            List<Block> otherToSet = new ArrayList<>();
            for (int i = 0; i < blocks.size() - 2; i++) {
                otherToSet.add(data.getBlockFrom().getRelative(blocks.get(i).getRelative()[0], blocks.get(i).getRelative()[1], blocks.get(i).getRelative()[2]));
                set(data, data.getBlockFrom().getRelative(blocks.get(i).getRelative()[0], blocks.get(i).getRelative()[1], blocks.get(i).getRelative()[2]), blocks.get(i));
            }

            data.setOtherBlocks(otherToSet);
        }

        if (data.getBlockTo().getType() == Material.AIR) data.getBlockTo().setType(data.getBlock());
        for (Block block : data.getOtherBlocks()) {
            if (block.getType() == Material.AIR) block.setType(data.getBlock());
        }

        for (ParkourBlock block : blocks) { block.clear(); } blocks.clear();

    }

    private void set(ActiveData data, Block block, ParkourBlock pkBlock) {
        switch (pkBlock.getType()) {
            case ICE:
                block.setType(Material.ICE); return;
            case FENCE:
                block.setType(Material.FENCE); return;
            case BLOCK:
                block.setType(data.getBlock());
        }
    }

}
