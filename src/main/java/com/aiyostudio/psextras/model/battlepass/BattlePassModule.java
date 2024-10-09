package com.aiyostudio.psextras.model.battlepass;

import com.aiyostudio.psextras.model.IModel;
import com.aiyostudio.psextras.plugin.PsExtras;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import io.github.battlepass.BattlePlugin;
import io.github.battlepass.api.events.server.PluginReloadEvent;
import net.advancedplugins.bp.impl.actions.external.executor.ActionQuestExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BattlePassModule implements IModel, Listener {
    private static BattlePassQuestExecutor battlePassQuestExecutor;

    @EventHandler
    public void onPluginReload(PluginReloadEvent event) {
        BattlePlugin.getApi().getActionRegistry().hook("PsExtras", (instance) -> {
            battlePassQuestExecutor = new BattlePassQuestExecutor();
            return battlePassQuestExecutor;
        });
    }

    @Override
    public void submit(Player player, String questType, String condition, int amount, Pokemon pokemon) {
        if (battlePassQuestExecutor != null) {
            battlePassQuestExecutor.submit(player, questType, condition, amount);
        }
    }

    public static class BattlePassQuestExecutor extends ActionQuestExecutor {

        public BattlePassQuestExecutor() {
            super(PsExtras.getInstance(), "psextras");
        }

        public void submit(Player player, String questType, String condition, int amount) {
            super.execute(questType, player, amount, (action) -> action.root(condition));
        }
    }
}