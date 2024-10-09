package com.aiyostudio.psextras.data.cache;

import com.pixelmonmod.api.registry.RegistryValue;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonFactory;
import com.pixelmonmod.pixelmon.api.pokemon.species.Species;
import com.pixelmonmod.pixelmon.api.pokemon.stats.BattleStatsType;
import com.pixelmonmod.pixelmon.api.pokemon.stats.EVStore;
import com.pixelmonmod.pixelmon.api.pokemon.stats.IVStore;
import com.pixelmonmod.pixelmon.api.registries.PixelmonSpecies;
import com.pixelmonmod.pixelmon.battles.attacks.Attack;
import com.pixelmonmod.pixelmon.battles.attacks.ImmutableAttack;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Blank038
 */
@Getter
public class PokemonCache {
    private final RegistryValue<Species> species;
    private final int level, chance;
    private final List<String> winCommands, loseCommands, captureCommands;
    private final IVStore ivStore = new IVStore();
    private final EVStore evStore = new EVStore();
    private final Map<Integer, ImmutableAttack> attackMap = new HashMap<>();

    public PokemonCache(ConfigurationSection section) {
        this.species = PixelmonSpecies.get(section.getString("species")).orElse(null);
        this.level = section.getInt("level");
        if (section.contains("ivs")) {
            for (String key : section.getConfigurationSection("ivs").getKeys(false)) {
                this.ivStore.setStat(BattleStatsType.valueOf(key.toUpperCase()), section.getInt("ivs." + key));
            }
        }
        if (section.contains("evs")) {
            for (String key : section.getConfigurationSection("evs").getKeys(false)) {
                this.evStore.setStat(BattleStatsType.valueOf(key.toUpperCase()), section.getInt("evs." + key));
            }
        }
        this.winCommands = section.getStringList("commands.win");
        this.loseCommands = section.getStringList("commands.lose");
        this.captureCommands = section.getStringList("commands.capture");
        if (section.contains("skill")) {
            for (String key : section.getConfigurationSection("skill").getKeys(false)) {
                this.attackMap.put(Integer.parseInt(key), Attack.getAttacks(new String[]{section.getString("skill." + key)})[0]);
            }
        }
        this.chance = section.getInt("capture-chance");
    }

    public Pokemon create() {
        if (species == null) {
            return null;
        }
        Pokemon pokemon = PokemonFactory.create(species.getValueUnsafe());
        pokemon.setLevel(level);
        pokemon.getIVs().copyIVs(this.ivStore);
        pokemon.getEVs().fillFromArray(this.evStore.getArray());
        for (int i = 0; i < 4; i++) {
            if (this.attackMap.containsKey(i)) {
                pokemon.getMoveset().set(i, new Attack(this.attackMap.get(i)));
            } else {
                pokemon.getMoveset().set(i, null);
            }
        }
        return pokemon;
    }
}
