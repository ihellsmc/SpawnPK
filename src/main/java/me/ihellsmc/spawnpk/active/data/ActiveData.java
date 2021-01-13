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

    public void clear() {
        this.blockFrom = null; this.blockTo = null; this.otherBlocks = null; this.points = 0; this.block = null;
    }

}
