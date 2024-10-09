package com.aiyostudio.psextras.plugin.listener.extra.psworldguarditem;

import com.aiyostudio.psextras.handler.ModelHandler;
import com.aiyostudio.psworldguarditem.api.event.PsWorldGuardGiveItemEvent;
import com.aiyostudio.psworldguarditem.api.event.PsWorldGuardUseItemEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * @author Blank038
 */
public class PsWorldGuardItemListener implements Listener {

    @EventHandler
    public void onPsWorldGuardUseItem(PsWorldGuardUseItemEvent event) {
        ModelHandler.submit(event.getPlayer(), "ps_world_guard_use", event.getItemKey(), 1, null);
    }

    @EventHandler
    public void onPsWorldGuardGiveItem(PsWorldGuardGiveItemEvent event) {
        ModelHandler.submit(event.getPlayer(), "ps_world_guard_give", event.getItemKey(), 1, null);
    }
}
