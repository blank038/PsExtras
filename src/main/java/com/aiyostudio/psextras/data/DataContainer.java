package com.aiyostudio.psextras.data;

import com.aiyostudio.psextras.data.cache.PokemonCache;
import com.aiyostudio.psextras.plugin.PsExtras;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Blank038
 */
public class DataContainer {
    public static final Map<String, PokemonCache> POKEMON_CACHE_MAP = new HashMap<>();
    public static final Map<UUID, String> CAPTURE_SOURCE = new HashMap<>();

    public static void init() {
        DataContainer.POKEMON_CACHE_MAP.clear();
        for (String key : PsExtras.getInstance().getConfig().getConfigurationSection("custom-pokemons").getKeys(false)) {
            ConfigurationSection section = PsExtras.getInstance().getConfig().getConfigurationSection("custom-pokemons." + key);
            DataContainer.POKEMON_CACHE_MAP.put(key, new PokemonCache(section));
        }
    }
}
