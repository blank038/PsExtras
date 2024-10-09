package com.aiyostudio.psextras.plugin.listener.extra.pokemarket;

import com.aiyostudio.psextras.handler.ModelHandler;
import com.aystudio.core.bukkit.event.CustomEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * @author Blank038
 */
public class PokeMarketListener implements Listener {

    @EventHandler
    public void onCustomEvent(CustomEvent event) {
        if ("MarketSellEvent".equalsIgnoreCase(event.getCustomEventName())) {
            Pokemon pokemon = (Pokemon) event.getArgs();
            ModelHandler.submit(event.getPlayer(), "poke_market", pokemon.getSpecies().getName(), 1, pokemon);
        }
    }
}
