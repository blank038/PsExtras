package com.aiyostudio.psextras.plugin;

import com.aiyostudio.psextras.command.PsExtrasCommand;
import com.aiyostudio.psextras.data.DataContainer;
import com.aiyostudio.psextras.handler.ModelHandler;
import com.aiyostudio.psextras.plugin.listener.ForgeListener;
import com.aiyostudio.psextras.plugin.listener.extra.advancedcrates.AdvancedCratesListener;
import com.aiyostudio.psextras.plugin.listener.extra.litesignin.LiteSignInListener;
import com.aiyostudio.psextras.plugin.listener.extra.nyphysical.NyPhysicalListener;
import com.aiyostudio.psextras.plugin.listener.extra.pixelgashapon.PixelGashaponListener;
import com.aiyostudio.psextras.plugin.listener.extra.pokealtar.PokeAltarListener;
import com.aiyostudio.psextras.plugin.listener.extra.pokebreed.PokeBreedListener;
import com.aiyostudio.psextras.plugin.listener.extra.pokeitem.PokeItemListener;
import com.aiyostudio.psextras.plugin.listener.extra.pokemarket.PokeMarketListener;
import com.aiyostudio.psextras.plugin.listener.extra.poketoken.PokeTokenListener;
import com.aiyostudio.psextras.plugin.listener.extra.psnumeralguessing.PsNumeralGuessingListener;
import com.aiyostudio.psextras.plugin.listener.extra.psworldguarditem.PsWorldGuardItemListener;
import com.aiyostudio.psextras.plugin.listener.extra.residence.ResidenceListener;
import com.aiyostudio.psextras.plugin.listener.extra.servermarket.ServerMarketListener;
import com.aystudio.core.bukkit.plugin.AyPlugin;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PsExtras extends AyPlugin {
    private static final Pattern PATTERN = Pattern.compile("#[A-f0-9]{6}");
    private static PsExtras instance;

    @Override
    public void onEnable() {
        instance = this;
        this.loadConfig();
        ModelHandler.initAllModel();
        // 注册监听
        this.registerListeners();
        // 注册命令
        this.getCommand("cpoke").setExecutor(new PsExtrasCommand());
    }

    private void registerListeners() {
        new ForgeListener();

        Map<String, Class<? extends Listener>> plugins = new HashMap<>();
        plugins.put("PokeBreed", PokeBreedListener.class);
        plugins.put("PokeAltar", PokeAltarListener.class);
        plugins.put("PokeToken", PokeTokenListener.class);
        plugins.put("PokeItem", PokeItemListener.class);
        plugins.put("NyPhysical", NyPhysicalListener.class);
        plugins.put("PsNumeralGuessing", PsNumeralGuessingListener.class);
        plugins.put("PokeMarket", PokeMarketListener.class);
        plugins.put("ServerMarket", ServerMarketListener.class);
        plugins.put("PixelGashapon", PixelGashaponListener.class);
        plugins.put("Residence", ResidenceListener.class);
        plugins.put("PsWorldGuardItem", PsWorldGuardItemListener.class);
        plugins.put("PixelmonPvp", PixelGashaponListener.class);
        plugins.put("AdvancedCrates", AdvancedCratesListener.class);
        plugins.put("LiteSignIn", LiteSignInListener.class);

        plugins.forEach((k, v) -> {
            if (Bukkit.getPluginManager().getPlugin(k) != null) {
                try {
                    Bukkit.getPluginManager().registerEvents(v.newInstance(), this);
                } catch (InstantiationException | IllegalAccessException e) {
                    this.getLogger().severe(e.toString());
                }
            }
        });
    }

    public void loadConfig() {
        this.saveDefaultConfig();
        this.reloadConfig();
        DataContainer.init();
    }

    public static String getString(String key, boolean... prefix) {
        String message = instance.getConfig().getString(key, "");
        if (prefix.length > 0 && prefix[0]) {
            message = instance.getConfig().getString("message.prefix") + message;
        }
        return PsExtras.formatHexColor(message);
    }

    public static String formatHexColor(String message) {
        String copy = message;
        Matcher matcher = PATTERN.matcher(copy);
        while (matcher.find()) {
            String color = message.substring(matcher.start(), matcher.end());
            copy = copy.replace(color, String.valueOf(ChatColor.of(color)));
        }
        return ChatColor.translateAlternateColorCodes('&', copy);
    }

    public static PsExtras getInstance() {
        return instance;
    }
}