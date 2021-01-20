package me.ihellsmc.spawnpk.commands;

import me.ihellsmc.spawnpk.SpawnPK;
import me.ihellsmc.spawnpk.active.data.ActiveData;
import me.ihellsmc.spawnpk.framework.command.Command;
import me.ihellsmc.spawnpk.framework.command.CommandArgs;
import me.ihellsmc.spawnpk.framework.command.QuarkFramework;
import me.ihellsmc.spawnpk.playerdata.PlayerData;
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

        core.getConfigManager().getFile("config").saveConfig();

    }

    @Command(name = "mat")
    public void onMat(CommandArgs cmd) {
        Player player = cmd.getPlayer();
        PlayerData data = core.getPlayerManager().get(player.getUniqueId());

        for (Material material : core.getJumpManager().getBlockTypes().keySet()) {
            if (data.getHighscore() >= core.getJumpManager().getBlockTypes().get(material)) player.sendMessage(CC.trns(material.toString()));
        }

    }

}
