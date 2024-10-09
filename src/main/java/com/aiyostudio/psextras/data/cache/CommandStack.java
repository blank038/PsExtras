package com.aiyostudio.psextras.data.cache;

import com.aiyostudio.psextras.plugin.PsExtras;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.logging.Level;

/**
 * @author Blank038
 */
public class CommandStack {
    private final String playerName;
    private final List<String> commands;
    private int index;

    public CommandStack(Player player, List<String> commands) {
        this.playerName = player.getName();
        this.commands = commands;
    }

    private void perform(String command) {
        Bukkit.getScheduler().runTask(PsExtras.getInstance(), () -> {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", playerName));
        });
    }

    public void start() {
        Bukkit.getScheduler().runTaskAsynchronously(PsExtras.getInstance(), () -> {
            try {
                for (String command : commands) {
                    if (command.startsWith("delay ")) {
                        Thread.sleep(1000L * Integer.parseInt(command.replace("delay ", "")));
                    } else {
                        this.perform(command);
                    }
                }
            } catch (java.lang.InterruptedException exception) {
                PsExtras.getInstance().getLogger().log(Level.WARNING, exception, () -> "An error occurred while executing commands.");
            }
        });
    }
}
