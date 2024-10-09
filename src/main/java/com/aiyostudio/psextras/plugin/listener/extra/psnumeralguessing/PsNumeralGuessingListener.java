package com.aiyostudio.psextras.plugin.listener.extra.psnumeralguessing;

import com.aiyostudio.psextras.handler.ModelHandler;
import com.aiyostudio.psnumeralguessing.api.event.NumeralGuessingAddBetRecordEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * @author Blank038
 */
public class PsNumeralGuessingListener implements Listener {

    @EventHandler
    public void onNumeralAddBet(NumeralGuessingAddBetRecordEvent event) {
        Player player = Bukkit.getPlayer(event.getSourcer());
        if (player != null && player.isOnline()) {
            ModelHandler.submit(player, "numeral", event.getBet().getEcoType().name(), 1, null);
        }
    }
}
