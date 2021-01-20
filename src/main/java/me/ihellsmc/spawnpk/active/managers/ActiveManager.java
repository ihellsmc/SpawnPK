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
        if (pd.getData() != null) selectNewJump(pd.getData());
        Material material = core.getJumpManager().getMaterial(pd);

        player.getLocation().getBlock().getRelative(0, -1, 0).setType(material);

        ActiveData data = new ActiveData();

        setRandomJump(player, data);

        if (speed) {
            data.setSpeed(true);
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1), true);
        }

    }

    public void selectNewJump(ActiveData data) {
        List<ParkourBlock> blocks = core.getJumpManager().getRandom();
        data.getBlockFrom().setType(Material.AIR);
        data.setBlockFrom(data.getBlockTo());
        data.getOtherBlocks().forEach(b -> b.setType(Material.AIR));

        ParkourBlock block;
        if (blocks.size() == 1) {

            block = blocks.get(0);
            Block bl = data.getBlockFrom().getRelative(block.getRelative()[0], block.getRelative()[1], block.getRelative()[2]);
            data.setBlockTo(bl);
            data.setOtherBlocks(new ArrayList<>());

            set(data, bl, block);

        } else {

            block = blocks.get(blocks.size() - 1);
            Block bl = data.getBlockFrom().getRelative(block.getRelative()[0], block.getRelative()[1], block.getRelative()[2]);
            data.setBlockTo(bl);

            set(data, bl, block);

            List<Block> otherBlocks = new ArrayList<>();
            for (int i = 0; i < blocks.size() - 1; i++) {
                ParkourBlock pk = blocks.get(i);
                Block blk = data.getBlockFrom().getRelative(pk.getRelative()[0], pk.getRelative()[1], pk.getRelative()[2]);
                otherBlocks.add(blk);
                set(data, blk, pk);

                pk.clear();
            }

            data.setOtherBlocks(otherBlocks);

        }
        block.clear();

    }

    private void setRandomJump(Player player, ActiveData data) {
        List<ParkourBlock> blocks = core.getJumpManager().getRandom();

        ParkourBlock block;
        if (blocks.size() == 1) {

            block = blocks.get(0);
            Block bl = player.getLocation().getBlock().getRelative(block.getRelative()[0], block.getRelative()[1], block.getRelative()[2]);
            data.setBlockTo(bl);
            data.setOtherBlocks(new ArrayList<>());

            set(data, bl, block);

        } else {

            block = blocks.get(blocks.size() - 1);
            Block bl = player.getLocation().getBlock().getRelative(block.getRelative()[0], block.getRelative()[1], block.getRelative()[2]);
            data.setBlockTo(bl);

            set(data, bl, block);

            List<Block> otherBlocks = new ArrayList<>();
            for (int i = 0; i < blocks.size() - 1; i++) {
                ParkourBlock pk = blocks.get(i);
                Block blk = player.getLocation().getBlock().getRelative(pk.getRelative()[0], pk.getRelative()[1], pk.getRelative()[2]);
                otherBlocks.add(blk);
                set(data, blk, pk);

                pk.clear();
            }

            data.setOtherBlocks(otherBlocks);

        }
        block.clear();
    }

    private void set(ActiveData data, Block block, ParkourBlock pkBlock) {
        switch (pkBlock.getType()) {
            case ICE:
                block.setType(Material.ICE); break;
            case FENCE:
                block.setType(Material.FENCE); break;
            case BLOCK:
                block.setType(data.getBlock());
        }
    }

    private int distanced(int blocks) {
        if (current.isEmpty()) return 0;
        int n = blocks * current.size();
        return current.size() % 2 == 0 ? n : -n;
    }

}
