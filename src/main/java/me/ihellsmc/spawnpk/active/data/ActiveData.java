package me.ihellsmc.spawnpk.active.data;

import lombok.Data;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.List;

@Data
public class ActiveData {

    private Block blockFrom;
    private Block blockTo;
    private List<Block> otherBlocks;
    private int points = 0;
    private Material block;
    private boolean speed = false;

    public void clear() {
        blockFrom = null; blockTo = null; otherBlocks = null; points = 0; block = null; speed = false;
    }

}
