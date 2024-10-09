package com.aiyostudio.psextras.battle;

import com.aiyostudio.psextras.data.DataContainer;
import com.aiyostudio.psextras.data.cache.CommandStack;
import com.aiyostudio.psextras.data.cache.PokemonCache;
import com.pixelmonmod.pixelmon.api.battles.BattleType;
import com.pixelmonmod.pixelmon.battles.api.rules.BattleRules;
import com.pixelmonmod.pixelmon.battles.controller.BattleController;
import com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.WildPixelmonParticipant;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * @author Blank038
 */
public class CustomBattleController extends BattleController {
    private final PokemonCache cache;
    private boolean execute;

    public CustomBattleController(PlayerParticipant playerParticipant, WildPixelmonParticipant wildPixelmonParticipant, PokemonCache cache) {
        super(new PlayerParticipant[]{playerParticipant}, new WildPixelmonParticipant[]{wildPixelmonParticipant}, new BattleRules(BattleType.SINGLE));
        this.cache = cache;
    }

    public void callEnd(boolean win) {
        if (execute) {
            return;
        }
        this.execute = true;
        this.participants.forEach((k) -> {
            if (k instanceof PlayerParticipant) {
                Player player = Bukkit.getPlayer(((PlayerParticipant) k).player.getUUID());
                if (player == null || !player.isOnline()) {
                    return;
                }
                List<String> commands = win ? cache.getWinCommands() : cache.getLoseCommands();
                new CommandStack(player, commands).start();
            } else {
                k.getTeamPokemon().forEach((s) -> {
                    if (s.entity != null) {
                        s.entity.remove();
                    }
                    DataContainer.CAPTURE_SOURCE.remove(s.pokemon.getUUID());
                });
            }
        });
    }

    public void callCapture() {
        if (execute) {
            return;
        }
        this.execute = true;
        this.participants.forEach((k) -> {
            if (k instanceof PlayerParticipant) {
                Player player = Bukkit.getPlayer(((PlayerParticipant) k).player.getUUID());
                if (player == null || !player.isOnline()) {
                    return;
                }
                new CommandStack(player, cache.getCaptureCommands()).start();
            } else {
                k.getTeamPokemon().forEach((s) -> {
                    if (s.entity != null) {
                        s.entity.remove();
                    }
                    DataContainer.CAPTURE_SOURCE.remove(s.pokemon.getUUID());
                });
            }
        });
    }
}
