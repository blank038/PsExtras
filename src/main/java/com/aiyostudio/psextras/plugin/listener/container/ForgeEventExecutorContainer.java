package com.aiyostudio.psextras.plugin.listener.container;

import com.aiyostudio.psextras.battle.CustomBattleController;
import com.aiyostudio.psextras.data.DataContainer;
import com.aiyostudio.psextras.handler.ModelHandler;
import com.aiyostudio.psextras.plugin.PsExtras;
import com.aiyostudio.psextras.plugin.listener.EventExecutor;
import com.pixelmonmod.pixelmon.api.battles.BattleResults;
import com.pixelmonmod.pixelmon.api.enums.ExperienceGainType;
import com.pixelmonmod.pixelmon.api.events.*;
import com.pixelmonmod.pixelmon.api.events.battles.BattleEndEvent;
import com.pixelmonmod.pixelmon.api.events.pokemon.EVsGainedEvent;
import com.pixelmonmod.pixelmon.api.events.pokemon.SetNicknameEvent;
import com.pixelmonmod.api.events.raids.DenInitialEvent;
import com.pixelmonmod.pixelmon.api.events.raids.EndRaidEvent;
import com.pixelmonmod.pixelmon.api.events.raids.StartRaidEvent;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant;
import com.pixelmonmod.pixelmon.battles.raids.RaidData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.UUID;

public class ForgeEventExecutorContainer {
    private static Field raidDataOwnerUniqueIdField;

    static {
        try {
            raidDataOwnerUniqueIdField = RaidData.class.getDeclaredField("uuid");
            raidDataOwnerUniqueIdField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            PsExtras.getInstance().getLogger().severe(e.toString());
        }
    }

    /**
     * 玩家击败野生精灵
     */
    public static final EventExecutor<BeatWildPixelmonEvent> BEAT_WILD_PIXELMON = (event) -> {
        Player player = Bukkit.getPlayer(event.player.getUUID());
        ModelHandler.submit(player, "beat_wild_pixelmon", event.wpp.allPokemon[0].pokemon.getSpecies().getName(), 1, event.wpp.allPokemon[0].pokemon);
    };
    /**
     * 捕捉野外精灵
     */
    public static final EventExecutor<CaptureEvent.SuccessfulCapture> CAPTURE_PIXELMON = (event) -> {
        UUID uuid = event.getPlayer().getUUID();
        Player player = Bukkit.getPlayer(uuid);
        ModelHandler.submit(player, "capture_pixelmon", event.getPokemon().getSpecies().getName(), 1, event.getPokemon().getPokemon());
        // 判断对战容器是否为 CustomBattleController
        if (event.getPokemon().battleController instanceof CustomBattleController) {
            ((CustomBattleController) event.getPokemon().battleController).callCapture();
        }
    };
    /**
     * 捕捉巢穴精灵
     */
    public static final EventExecutor<CaptureEvent.SuccessfulRaidCapture> CAPTURE_RAID_PIXELMON = (event) -> {
        Player player = Bukkit.getPlayer(event.getPlayer().getUUID());
        ModelHandler.submit(player, "capture_raid_pixelmon", event.getRaidPokemon().getSpecies().getName(), 1, event.getRaidPokemon());
    };
    /**
     * 击败训练师
     */
    public static final EventExecutor<BeatTrainerEvent> BEAT_TRAINER = (event) -> {
        Player player = Bukkit.getPlayer(event.player.getUUID());
        ModelHandler.submit(player, "beat_trainer", event.trainer.getName().getString(), 1, null);
    };
    /**
     * 钓鱼
     */
    public static final EventExecutor<FishingEvent.Catch> FISHING_CATCH = (event) -> {
        Player player = Bukkit.getPlayer(event.player.getUUID());
        ModelHandler.submit(player, "fishing_catch", event.plannedSpawn.getOrCreateEntity().getName().getString(), 1, null);
    };
    /**
     * 采摘树果
     */
    public static final EventExecutor<ApricornEvent.Pick> PICK_APRICORN = (event) -> {
        Player player = Bukkit.getPlayer(event.getPlayer().getUUID());
        ModelHandler.submit(player, "pick_apricorn", event.getApricorn().name(), 1, null);
    };
    /**
     * 精灵升级
     */
    public static final EventExecutor<LevelUpEvent.Post> LEVEL_UP = (event) -> {
        if (event.getPlayer() != null) {
            Player player = Bukkit.getPlayer(event.getPlayer().getUUID());
            ModelHandler.submit(player, "pixelmon_level_up", event.getPokemon().getSpecies().getName(), 1, event.getPokemon());
        }
    };
    /**
     * 玩家交易精灵
     */
    public static final EventExecutor<PixelmonTradeEvent> TRADE_POKEMON = (event) -> {
        Player player1 = Bukkit.getPlayer(event.getPlayer1().getUUID()), player2 = Bukkit.getPlayer(event.getPlayer2().getUUID());
        ModelHandler.submit(player1, "trade_pokemon", event.getPokemon1().getSpecies().getName(), 1, event.getPokemon1());
        ModelHandler.submit(player2, "trade_pokemon", event.getPokemon2().getSpecies().getName(), 1, event.getPokemon2());
    };
    /**
     * 设置精灵名
     */
    public static final EventExecutor<SetNicknameEvent> SET_NICK_NAME = (event) -> {
        Player player = Bukkit.getPlayer(event.player.getUUID());
        ModelHandler.submit(player, "set_nick_name", event.pokemon.getSpecies().getName(), 1, event.pokemon);
    };
    /**
     * 玩家精灵 EVs 增加
     */
    public static final EventExecutor<EVsGainedEvent> EVS_GAINED = (event) -> {
        if (event.pokemon.getOwnerPlayer() != null) {
            Player player = Bukkit.getPlayer(event.pokemon.getOwnerPlayer().getUUID());
            ModelHandler.submit(player, "evs_gained", event.pokemon.getSpecies().getName(), Arrays.stream(event.evYields.toArray()).sum(), event.pokemon);
        }
    };
    /**
     * 玩家精灵经验增加
     */
    public static final EventExecutor<ExperienceGainEvent> EXPERIENCE_GAIN = (event) -> {
        if (event.pokemon.getPokemon().getOwnerPlayer() != null) {
            Player player = Bukkit.getPlayer(event.pokemon.getPokemon().getOwnerPlayerUUID());
            ModelHandler.submit(player, "experience_gain", event.pokemon.getSpecies().getName(), event.getExperience(), event.pokemon.getPokemon());
            if (event.getType() == ExperienceGainType.RARE_CANDY) {
                ModelHandler.submit(player, "use_rare_candy", event.pokemon.getSpecies().getName(), event.getExperience(), event.pokemon.getPokemon());
            }
        }
    };
    /**
     * 进化前事件
     */
    public static final EventExecutor<EvolveEvent.Pre> EVOLVE_PRE = (event) -> {
        Player player = Bukkit.getPlayer(event.getPlayer().getUUID());
        ModelHandler.submit(player, "poke_pre_evolve", event.getPokemon().getSpecies().getName(), 1, event.getPokemon());
    };
    /**
     * 进化后事件
     */
    public static final EventExecutor<EvolveEvent.Post> EVOLVE_POST = (event) -> {
        Player player = Bukkit.getPlayer(event.getPlayer().getUUID());
        ModelHandler.submit(player, "poke_post_evolve", event.getPokemon().getSpecies().getName(), 1, event.getPokemon());
    };
    /**
     * 跟商人购买物品
     */
    public static final EventExecutor<ShopkeeperEvent.Purchase> SHOPKEEPER_PURCHASE = (event) -> {
        Player player = Bukkit.getPlayer(event.getEntityPlayer().getUUID());
        ModelHandler.submit(player, "shopkeeper_purchase", "none", 1, null);
    };
    /**
     * 跟商人购买物品
     */
    public static final EventExecutor<ShopkeeperEvent.Sell> SHOPKEEPER_SELL = (event) -> {
        Player player = Bukkit.getPlayer(event.getEntityPlayer().getUUID());
        ModelHandler.submit(player, "shopkeeper_sell", "none", 1, null);
    };
    /**
     * 跟商人购买物品
     */
    public static final EventExecutor<PlayerActivateShrineEvent.Post> ACTIVATE_SHRINE = (event) -> {
        Player player = Bukkit.getPlayer(event.getPlayer().getUUID());
        ModelHandler.submit(player, "activate_shrine", event.getShrineType().name(), 1, null);
    };
    /**
     * 精灵蛋孵化
     */
    public static final EventExecutor<EggHatchEvent.Post> EGG_HATCH = (event) -> {
        Player player = Bukkit.getPlayer(event.getPlayer().getUUID());
        ModelHandler.submit(player, "egg_hatch", event.getPokemon().getSpecies().getName(), 1, event.getPokemon());
    };
    /**
     * 精灵化石孵化
     */
    public static final EventExecutor<PokemonReceivedEvent> POKEMON_RECEIVED = (event) -> {
        Player player = Bukkit.getPlayer(event.getPlayer().getUUID());
        ModelHandler.submit(player, "pokemon_received", event.getCause() + ":" + event.getPokemon().getSpecies().getName(), 1, event.getPokemon());
        ModelHandler.submit(player, "pokemon_received", event.getCause() + ":none", 1, event.getPokemon());
    };
    /**
     * 检测巢穴对战开始
     */
    public static final EventExecutor<StartRaidEvent> START_RAID = (event) -> {
        for (BattleParticipant participant : event.getAllyParticipants()) {
            if (participant instanceof PlayerParticipant) {
                PlayerParticipant playerParticipant = (PlayerParticipant) participant;
                Player player = Bukkit.getPlayer(playerParticipant.player.getUUID());
                ModelHandler.submit(player, "raid_start", event.getRaid().getSpecies().getName(), 1, null);
            }
        }
    };
    /**
     * 检测巢穴初始化
     */
    public static final EventExecutor<DenInitialEvent> DEN_INITIAL = (event) -> {
        if (event.getRaidData() == null) {
            return;
        }
        event.getRaidData().ifPresent((raidData) -> {
            try {
                UUID uuid = (UUID) raidDataOwnerUniqueIdField.get(raidData);
                if (uuid == null) {
                    return;
                }
                Player player = Bukkit.getPlayer(uuid);
                if (player == null || !player.isOnline()) {
                    return;
                }
                if (event.isStandard()) {
                    ModelHandler.submit(player, "den_initial_standard", raidData.getSpecies().getName(), 1, null);
                } else {
                    ModelHandler.submit(player, "den_initial_legendary", raidData.getSpecies().getName(), 1, null);
                }
            } catch (IllegalAccessException e) {
                PsExtras.getInstance().getLogger().severe(e.toString());
            }
        });
    };
    /**
     * 巢穴对战胜利与对战结束
     */
    public static final EventExecutor<EndRaidEvent> END_RAID = (event) -> {
        event.getAllyParticipants().forEach((participant) -> {
            if (participant instanceof PlayerParticipant) {
                PlayerParticipant playerParticipant = (PlayerParticipant) participant;
                Player player = Bukkit.getPlayer(playerParticipant.player.getUUID());
                // 唤起对战结束事件
                ModelHandler.submit(player, "raid_end", event.getRaid().getSpecies().getName(), 1, null);
                // 唤起巢穴对战胜利事件
                if (event.didRaidersWin()) {
                    ModelHandler.submit(player, "raid_win", event.getRaid().getSpecies().getName(), 1, null);
                }
            }
        });
    };
    /**
     * START_CAPTURE_POKEMON && BATTLE_END 为 PokemonCache 监听事件
     */
    public static final EventExecutor<CaptureEvent.StartCapture> START_CAPTURE_POKEMON = (event) -> {
        if (DataContainer.CAPTURE_SOURCE.containsKey(event.getPokemon().getUUID())) {
            String source = DataContainer.CAPTURE_SOURCE.get(event.getPokemon().getUUID());
            if (DataContainer.POKEMON_CACHE_MAP.containsKey(source)) {
                int chance = (int) (DataContainer.POKEMON_CACHE_MAP.get(source).getChance() * 2.55);
                event.getCaptureValues().setCatchRate(chance);
            }
        }
    };
    public static final EventExecutor<BattleEndEvent> BATTLE_END = (event) -> {
        if (event.getBattleController() instanceof CustomBattleController) {
            boolean win = event.getBattleController().participants.stream().anyMatch((s) -> {
                if (s instanceof PlayerParticipant) {
                    return event.getResults().get(s) == BattleResults.VICTORY;
                }
                return false;
            });
            ((CustomBattleController) event.getBattleController()).callEnd(win);
        }
    };
}