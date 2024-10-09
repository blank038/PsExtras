package com.aiyostudio.psextras.plugin.listener.extra.litesignin;

import com.aiyostudio.psextras.handler.ModelHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import studio.trc.bukkit.litesignin.event.custom.PlayerSignInEvent;

/**
 * @author Blank038
 */
public class LiteSignInListener implements Listener {

    @EventHandler
    public void onPlayerSignIn(PlayerSignInEvent event) {
        ModelHandler.submit(event.getPlayer(), "lite_sign_in", "none", 1, null);
    }
}
