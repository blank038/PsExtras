package com.aiyostudio.psextras.command;

import com.aiyostudio.psextras.battle.CustomBattleController;
import com.aiyostudio.psextras.data.DataContainer;
import com.aiyostudio.psextras.plugin.PsExtras;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.species.aggression.Aggression;
import com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import com.pixelmonmod.pixelmon.battles.BattleRegistry;
import com.pixelmonmod.pixelmon.battles.controller.BattleController;
import com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.WildPixelmonParticipant;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * @author Blank038
 */
public class PsExtrasCommand implements CommandExecutor {
    private final PsExtras plugin = PsExtras.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender.hasPermission("psextras.admin")) {
            if (strings.length == 0) {
                if (commandSender.hasPermission("psextras.admin")) {
                    this.plugin.loadConfig();
                    commandSender.sendMessage(PsExtras.getString("message.reload", true));
                }
                return false;
            }
            this.startBattle(commandSender, strings);
        }
        return false;
    }

    private void startBattle(CommandSender sender, String[] params) {
        if (params.length == 1) {
            sender.sendMessage(PsExtras.getString("message.pls-enter-custom-key", true));
            return;
        }
        Player player = Bukkit.getPlayerExact(params[0]);
        if (player == null || !player.isOnline()) {
            sender.sendMessage(PsExtras.getString("message.player-offline", true));
            return;
        }
        PlayerPartyStorage storage = StorageProxy.getParty(player.getUniqueId());
        ServerPlayerEntity playerEntity = storage.getPlayer();
        // 判断是否在对战中
        BattleController battleController = BattleRegistry.getBattle(playerEntity);
        if (battleController != null && !battleController.battleEnded) {
            sender.sendMessage(PsExtras.getString("message.in-battle", true));
            return;
        }
        if (DataContainer.POKEMON_CACHE_MAP.containsKey(params[1])) {
            Pokemon pokemon = DataContainer.POKEMON_CACHE_MAP.get(params[1]).create();
            ServerPlayerEntity serverPlayer = storage.getPlayer();
            Vector3d vector = serverPlayer.position();
            PixelmonEntity entity = pokemon.getOrSpawnPixelmon(serverPlayer.level, vector.x, vector.y, vector.z);
            entity.aggression = Aggression.PASSIVE;
            entity.level.addFreshEntity(entity);

            PlayerParticipant playerParticipant = new PlayerParticipant(playerEntity, storage.getFirstAblePokemon());
            CustomBattleController controller = new CustomBattleController(playerParticipant,
                    new WildPixelmonParticipant(entity), DataContainer.POKEMON_CACHE_MAP.get(params[1]));
            BattleRegistry.registerBattle(controller);
            DataContainer.CAPTURE_SOURCE.put(pokemon.getUUID(), params[1]);
        } else {
            sender.sendMessage(PsExtras.getString("message.not-found", true));
        }
    }
}
