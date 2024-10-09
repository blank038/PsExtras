package com.aiyostudio.psextras.plugin.listener.extra.pokeitem;

import com.aiyostudio.pokeitem.event.UseItemEvent;
import com.aiyostudio.psextras.handler.ModelHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * @author Blank038
 */
public class PokeItemListener implements Listener {

    @EventHandler
    public void onPokeItem(UseItemEvent event) {
        ModelHandler.submit(event.getPlayer(), "pokeitem", "none", 1, null);
    }
}
