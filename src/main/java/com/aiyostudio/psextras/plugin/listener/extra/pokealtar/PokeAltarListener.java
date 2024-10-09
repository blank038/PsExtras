package com.aiyostudio.psextras.plugin.listener.extra.pokealtar;

import com.aiyostudio.pokealtar.event.PokeAlterUnlockEvent;
import com.aiyostudio.psextras.handler.ModelHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * @author Blank038
 */
public class PokeAltarListener implements Listener {

    @EventHandler
    public void onPokeAltarUnlock(PokeAlterUnlockEvent event) {
        Player player = Bukkit.getPlayer(event.getName());
        if (player != null) {
            ModelHandler.submit(player, "poke_altar_unlock", "none", 1, null);
        }
    }
}