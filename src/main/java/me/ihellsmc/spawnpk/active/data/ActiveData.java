package me.ihellsmc.spawnpk.active.data;

import lombok.Data;
import org.bukkit.block.Block;

import java.util.List;

@Data
public class ActiveData {

    private Block blockFrom;
    private Block blockTo;
    private List<Block> otherBlocks;

}
