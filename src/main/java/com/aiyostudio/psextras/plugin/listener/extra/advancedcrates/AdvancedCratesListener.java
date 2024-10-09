package com.aiyostudio.psextras.plugin.listener.extra.advancedcrates;

import com.aiyostudio.psextras.handler.ModelHandler;
import me.PM2.AdvancedCrates.localevents.PreCrateDropEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * @author Blank038
 */
public class AdvancedCratesListener implements Listener {

    @EventHandler
    public void onPreRewardGive(PreCrateDropEvent event) {
        ModelHandler.submit(event.a(), "advanced_crates", "none", 1, null);
    }
}
