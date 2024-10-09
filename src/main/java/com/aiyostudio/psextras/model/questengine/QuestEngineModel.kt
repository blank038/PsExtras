package com.aiyostudio.psextras.model.questengine

import cn.inrhor.questengine.api.target.util.TriggerUtils.triggerTarget
import com.aiyostudio.psextras.model.IModel
import com.aiyostudio.psextras.plugin.PsExtras
import com.pixelmonmod.pixelmon.api.pokemon.Element
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon
import org.bukkit.entity.Player

class QuestEngineModel : IModel {

    @JvmSuppressWildcards
    override fun submit(player: Player, questType: String, condition: String, amount: Int, pokemon: Pokemon?) {
        if (PsExtras.getInstance().config.getBoolean("debug")) {
            println("QuestEngineModel: {player=${player.name}, questType=$questType, condition=$condition, amount=$amount}")
        }
        player.triggerTarget(questType.replace("_", " ")) { _, pass ->
            val conditions = pass.id
            conditions.isEmpty() || conditions.any {
                if (it.contains(",")) {
                    it.split(",").all { subCondition -> this.checkPokemonCondition(pokemon, subCondition, condition) }
                } else {
                    this.checkPokemonCondition(pokemon, it, condition)
                }
            }
        }
    }

    private fun checkPokemonCondition(targetPokemon: Pokemon?, conditionLine: String, condition: String): Boolean {
        if (conditionLine.contains(":")) {
            return targetPokemon?.let { pokemon ->
                val split = conditionLine.split(":")
                when (split[0]) {
                    "type" -> {
                        pokemon.form.types.contains(Element.valueOf(split[1]))
                    }

                    "mt" -> pokemon.hasHiddenAbility()

                    "shiny" -> pokemon.isShiny

                    "ivs" -> (pokemon.iVs.total / 186.0 * 100.0).toInt() >= split[1].toInt()

                    else -> false
                }
            } ?: false
        } else {
            return conditionLine == condition || conditionLine == "*"
        }
    }
}