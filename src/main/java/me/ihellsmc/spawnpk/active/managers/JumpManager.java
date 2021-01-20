package me.ihellsmc.spawnpk.active.managers;

import lombok.Getter;
import lombok.Setter;
import me.ihellsmc.spawnpk.SpawnPK;
import me.ihellsmc.spawnpk.parkourblock.ParkourBlock;
import me.ihellsmc.spawnpk.parkourblock.ParkourBlockType;
import me.ihellsmc.spawnpk.playerdata.PlayerData;
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

    private List<List<ParkourBlock>> jumps = new ArrayList<>();

    public LinkedHashMap<Material, Integer> blockTypes = new LinkedHashMap<>();

    public Location plateLocation;

    public JumpManager() {

        for (String jump : jumpsFile.getStringList("jumps")) {
            jumps.add(genRelative(jump));
        }

        for (String mat : jumpsFile.getStringList("blocks")) {
            try {
                Material material = Material.getMaterial(mat.split(":")[0].toUpperCase());
                int req = Integer.parseInt(mat.split(":")[1]);
                if (!blockTypes.containsKey(material)) blockTypes.put(material, req);
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

    private List<ParkourBlock> genRelative(String jump) {
        List<ParkourBlock> toReturn = new ArrayList<>();
        for (String j : jump.split("/")) {
            // BLOCK:0,0,0
            String[] data = j.split(":");

            ParkourBlock pk = new ParkourBlock();
            pk.setType(ParkourBlockType.valueOf(data[0].toUpperCase()));

            int[] relative = new int[3];
            String[] s = data[1].split(",");
            for (int i = 0; i < s.length; i++) {
                relative[i] = Integer.parseInt(s[i]);
            }

            pk.setRelative(relative);
            toReturn.add(pk);
        }
        return toReturn;
    }

    public List<ParkourBlock> getRandom() {
        return jumps.get(new Random().nextInt(jumps.size()));
    }

    public Material getMaterial(PlayerData pd) {
        Material toReturn = (Material) blockTypes.keySet().toArray()[0];
        for (Material material : blockTypes.keySet()) {
            if (pd.getHighscore() >= blockTypes.get(material)) toReturn = material;
        }
        return toReturn;
    }

}
