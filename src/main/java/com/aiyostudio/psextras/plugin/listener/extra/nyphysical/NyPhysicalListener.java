package com.aiyostudio.psextras.plugin.listener.extra.nyphysical;

import com.aiyostudio.psextras.handler.ModelHandler;
import com.mc9y.nyphysical.api.events.ConsumePhysicalEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * @author Blank038
 */
public class NyPhysicalListener implements Listener {

    @EventHandler
    public void onConsumePhysical(ConsumePhysicalEvent event) {
        ModelHandler.submit(event.getPlayer(), "ny_physical", "none", event.getAmount(), null);
    }
}
