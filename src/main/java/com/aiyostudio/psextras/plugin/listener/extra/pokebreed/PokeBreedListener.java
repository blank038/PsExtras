package com.aiyostudio.psextras.plugin.listener.extra.pokebreed;

import com.aiyostudio.pokebreed.event.DayCareSuccessEvent;
import com.aiyostudio.psextras.handler.ModelHandler;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PokeBreedListener implements Listener {
    
    @EventHandler
    public void onDayCareEvent(DayCareSuccessEvent event) {
        Player player = event.getPlayer();
        if (player != null) {
            Pokemon pokemon = event.getChild();
            ModelHandler.submit(player, "day_care", pokemon.getSpecies().getName(), 1, pokemon);
            // 判断是否为梦特
            if (pokemon.hasHiddenAbility()) {
                ModelHandler.submit(player, "day_care_mt", pokemon.getSpecies().getName(), 1, pokemon);
            }
        }
    }
}