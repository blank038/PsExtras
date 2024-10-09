package com.aiyostudio.psextras.plugin.listener.extra.poketoken;

import com.aiyostudio.poketoken.event.MixPokemonEvent;
import com.aiyostudio.psextras.handler.ModelHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * @author Blank038
 */
public class PokeTokenListener implements Listener {

    @EventHandler
    public void onMixPokemonEvent(MixPokemonEvent event) {
        ModelHandler.submit(event.getPlayer(), "poketoken", event.getPokemon().getSpecies().getName(), 1, event.getPokemon());
    }
}
