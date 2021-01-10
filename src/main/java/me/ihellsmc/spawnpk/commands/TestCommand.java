package me.ihellsmc.spawnpk.commands;

import me.ihellsmc.spawnpk.SpawnPK;
import me.ihellsmc.spawnpk.framework.command.Command;
import me.ihellsmc.spawnpk.framework.command.CommandArgs;
import me.ihellsmc.spawnpk.framework.command.QuarkFramework;
import me.ihellsmc.spawnpk.utils.CC;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Random;
import java.util.Set;

public class TestCommand {

    private final SpawnPK core = SpawnPK.getInstance();

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

    @Command(name = "random")
    public void onRandomCommand(CommandArgs cmd) {
        for (int[] arr : core.getJumpManager().getJumps().get(new Random().nextInt(core.getJumpManager().getJumps().size()))) {
            cmd.getPlayer().sendMessage(CC.trns("&e" + arr[0] + " " + arr[1] + " " + arr[2]));
        }
    }

}
