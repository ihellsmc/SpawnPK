package me.ihellsmc.spawnpk.jumps.manager;

import lombok.Getter;
import lombok.Setter;
import me.ihellsmc.spawnpk.SpawnPK;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.*;

@Getter @Setter
public class JumpManager {

    private final SpawnPK core = SpawnPK.getInstance();
    private final YamlConfiguration jumpsFile = core.getConfigManager().getFile("config").getConfig();

    private List<List<int[]>> jumps = new ArrayList<>();
    private List<Material> types = new ArrayList<>();

    public Location plateLocation;

    public JumpManager() {

        for (String jump : jumpsFile.getStringList("jumps")) {
            jumps.add(genRelative(jump));
        }

        for (String mat : jumpsFile.getStringList("blocks")) {
            try {
                types.add(Material.getMaterial(mat.toUpperCase()));
            } catch (Exception ignored) {}
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

    private List<int[]> genRelative(String jump) {
        List<int[]> toReturn = new ArrayList<>();

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

    public List<int[]> getRandom() { return jumps.get(new Random().nextInt(jumps.size())); }

    public Material getRandomMaterial() { return types.get(new Random().nextInt(types.size())); }

}
