package com.aiyostudio.psextras.handler;

import com.aiyostudio.psextras.model.IModel;
import com.aiyostudio.psextras.model.battlepass.BattlePassModule;
import com.aiyostudio.psextras.model.questengine.QuestEngineModel;
import com.aiyostudio.psextras.plugin.PsExtras;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ModelHandler {
    private static final Map<ModelEnums, IModel> MODEL_MAP = new HashMap<>();

    public static void initAllModel() {
        // 注册模块
        for (ModelEnums modelEnum : ModelEnums.values()) {
            if (Bukkit.getPluginManager().getPlugin(modelEnum.getPluginName()) != null) {
                try {
                    MODEL_MAP.put(modelEnum, (IModel) modelEnum.getModelClass().newInstance());
                } catch (InstantiationException | IllegalAccessException e) {
                    PsExtras.getInstance().getLogger().severe(e.toString());
                }
            }
        }
    }

    public static void submit(Player player, String questType, String condition, int amount, Pokemon pokemon) {
        MODEL_MAP.forEach((k, v) -> v.submit(player, questType, condition, amount, pokemon));
    }

    public enum ModelEnums {
        QUEST_ENGINE("QuestEngine", QuestEngineModel.class),
        BATTLE_PASS("BattlePass", BattlePassModule.class);

        private final String pluginName;
        private final Class<?> modelClass;

        ModelEnums(String plugin, Class<?> c) {
            this.pluginName = plugin;
            this.modelClass = c;
        }

        public String getPluginName() {
            return pluginName;
        }

        public Class<?> getModelClass() {
            return modelClass;
        }
    }
}