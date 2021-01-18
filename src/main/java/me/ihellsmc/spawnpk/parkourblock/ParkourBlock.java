package me.ihellsmc.spawnpk.parkourblock;

import lombok.Data;

@Data
public class ParkourBlock {

    private int[] relative;
    private ParkourBlockType type;

    public void clear() { relative = null; type = null; }

}
