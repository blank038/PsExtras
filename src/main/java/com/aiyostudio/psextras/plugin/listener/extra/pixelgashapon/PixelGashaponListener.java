package com.aiyostudio.psextras.plugin.listener.extra.pixelgashapon;

import com.aiyostudio.pixelgashapon.event.GashaponEvent;
import com.aiyostudio.psextras.handler.ModelHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * @author Blank038
 */
public class PixelGashaponListener implements Listener {

    @EventHandler
    public void onPixelGashapon(GashaponEvent event) {
        event.getPokemons().forEach(s -> {
            ModelHandler.submit(event.getPlayer(), "gashapon", s.getSpecies().getName(), event.getGashaponCounts(), s);
        });
    }
}
