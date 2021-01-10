package me.ihellsmc.spawnpk.managers;

import lombok.Getter;
import lombok.Setter;
import me.ihellsmc.spawnpk.SpawnPK;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.*;

@Getter @Setter
public class JumpManager {

    private final SpawnPK core = SpawnPK.getInstance();
    private final YamlConfiguration jumpsFile = core.getConfigManager().getFile("config").getConfig();

    public List<Set<int[]>> jumps = new ArrayList<>();
    public Set<UUID> activePlayers = new HashSet<>();

    public Location plateLocation;

    public JumpManager() {

        for (String jump : jumpsFile.getStringList("jumps")) {
            jumps.add(genRelative(jump));
        }

        if (jumpsFile.getString("start-location.coords") != null) {
            String[] split = jumpsFile.getString("start-location.coords").split(",");
            World world = Bukkit.getWorld(jumpsFile.getString("start-location.world"));
            try {
                plateLocation = world.getBlockAt(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2])).getLocation();
            } catch (NumberFormatException e) {
                core.getLogger().severe(e.getMessage());
            }
        }

    }

    private Set<int[]> genRelative(String jump) {
        Set<int[]> toReturn = new HashSet<>();

        for (String theFuckingJump : jump.split("/")) {
            int[] jumpArray = new int[3];
            String[] relative = theFuckingJump.split(",");

            for (int i = 0; i < relative.length; i++) {
                try {
                    jumpArray[i] = Integer.parseInt(relative[i]);
                } catch (NumberFormatException e) {
                    jumpArray[i] = 1;
                    core.getLogger().severe(e.getMessage());
                }
            }

            toReturn.add(jumpArray);
        }

        return toReturn;
    }

}
