package com.aiyostudio.psextras.plugin.listener.extra.residence;

import com.aiyostudio.psextras.handler.ModelHandler;
import com.bekvon.bukkit.residence.event.ResidenceCreationEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * @author Blank038
 */
public class ResidenceListener implements Listener {

    @EventHandler
    public void onResidenceCreate(ResidenceCreationEvent event) {
        ModelHandler.submit(event.getPlayer(), "residence", event.getResidenceName(), 1, null);
    }
}
