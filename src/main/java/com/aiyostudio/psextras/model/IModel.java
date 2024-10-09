package com.aiyostudio.psextras.model;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import org.bukkit.entity.Player;

public interface IModel {
    
    void submit(Player player, String questType, String condition, int amount, Pokemon pokemon);
}