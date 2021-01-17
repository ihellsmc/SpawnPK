package me.ihellsmc.spawnpk.playerdata;

import lombok.Data;
import me.ihellsmc.spawnpk.active.data.ActiveData;

import java.util.UUID;

@Data
public class PlayerData {

    private final UUID uuid;
    private ActiveData data;
    private int highscore = 0;

    public void clear() {
        clearActiveData();
        highscore = 0;
    }

    public void clearActiveData() {
        if (data != null) data.clear(); data = null;
    }

}
