package com.aiyostudio.psextras.plugin.listener.extra.servermarket;

import com.aiyostudio.psextras.handler.ModelHandler;
import com.blank038.servermarket.api.event.PlayerSaleEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

/**
 * @author Blank038
 */
public class ServerMarketListener implements Listener {

    @EventHandler
    public void onSaleSell(PlayerSaleEvent.Sell event) {
        ItemStack itemStack = event.getSaleCache().getSaleItem();
        if (itemStack == null) {
            return;
        }
        ModelHandler.submit(event.getPlayer(), "server_market", this.getItemName(itemStack), 1, null);
    }

    private String getItemName(ItemStack itemStack) {
        if (itemStack == null) {
            return "AIR";
        }
        if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName()) {
            return itemStack.getItemMeta().getDisplayName();
        }
        return itemStack.getType().name();
    }
}
