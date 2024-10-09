package com.aiyostudio.psextras.plugin.listener.extra.pixelmonpvp;

import com.aiyostudio.psextras.handler.ModelHandler;
import com.mc9y.pixelmonpvpplugin.api.battle.BattleContainer;
import com.mc9y.pixelmonpvpplugin.api.event.PmpEndEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.stream.Stream;

/**
 * @author Blank038
 */
public class PixelmonPvpListener implements Listener {

    @EventHandler
    public void onPixelmonPvpEnd(PmpEndEvent event) {
        BattleContainer battleContainer = event.getBattleContainer();
        Stream.of(battleContainer.getTeam1(), battleContainer.getTeam2()).forEach((strings) -> {
            for (String playerName : strings) {
                Player player = Bukkit.getPlayerExact(playerName);
                if (player == null || !player.isOnline()) {
                    continue;
                }
                ModelHandler.submit(player, "pixelmon_pvp", "none", 1, null);
            }
        });
    }
}
