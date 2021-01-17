package me.ihellsmc.spawnpk.commands;

import me.ihellsmc.spawnpk.SpawnPK;
import me.ihellsmc.spawnpk.active.data.ActiveData;
import me.ihellsmc.spawnpk.framework.command.Command;
import me.ihellsmc.spawnpk.framework.command.CommandArgs;
import me.ihellsmc.spawnpk.framework.command.QuarkFramework;
import me.ihellsmc.spawnpk.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.Random;
import java.util.Set;

public class TestCommand {

    private final SpawnPK core = SpawnPK.getInstance();
    private final YamlConfiguration jumpsFile = core.getConfigManager().getFile("config").getConfig();

    public TestCommand(QuarkFramework framework) { framework.registerCommands(this); }

    @Command(name = "setstart")
    public void onTestCommand(CommandArgs cmd) {
        Player player = cmd.getPlayer();

        Block block = player.getTargetBlock((Set<Material>) null, 5);

        if (block.getType() == Material.GOLD_PLATE || block.getType() == Material.IRON_PLATE || block.getType() == Material.STONE_PLATE || block.getType() == Material.WOOD_PLATE) {
            if (!(block.getRelative(0, 1, 0).getType() != null && block.getRelative(0, 1, 0).getType() != Material.AIR)) {
                core.getJumpManager().setPlateLocation(block.getLocation());
                player.sendMessage(CC.trns("&aStart location set!"));
            } else {
                player.sendMessage(CC.trns("&cThere is no space to use that pressure plate!"));
            }
        } else {
            player.sendMessage(CC.trns("&cNot a valid pressure plate!"));
        }

    }

    @Command(name = "loc")
    public void onLocCommand(CommandArgs cmd) {
        if (jumpsFile.getString("start-location.coords") != null) {
            String[] split = jumpsFile.getString("start-location.coords").split(",");
            World world = Bukkit.getWorld(jumpsFile.getString("start-location.world"));

            cmd.getPlayer().sendMessage(CC.trns("&7World: &c" + world.getName()));
            cmd.getPlayer().sendMessage(CC.trns("&7X: &c" + Integer.parseInt(split[0])));
            cmd.getPlayer().sendMessage(CC.trns("&7Y: &c" + Integer.parseInt(split[1])));
            cmd.getPlayer().sendMessage(CC.trns("&7Z: &c" + Integer.parseInt(split[2])));

            cmd.getPlayer().teleport(world.getBlockAt(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2])).getLocation());
        }
    }

}
