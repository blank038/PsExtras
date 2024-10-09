package com.aiyostudio.psextras.plugin.listener;

import com.aiyostudio.psextras.plugin.PsExtras;
import com.aiyostudio.psextras.plugin.listener.container.ForgeEventExecutorContainer;
import com.aystudio.core.forge.ForgeInject;
import com.aystudio.core.forge.IForgeListenHandler;
import net.minecraftforge.eventbus.api.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ForgeListener implements Listener {
    private final Map<Object, EventExecutor> eventExecutorMap = new HashMap<>();
    
    public ForgeListener() {
        ForgeInject.getInstance().getForgeListener().registerListener(PsExtras.getInstance(), this, EventPriority.NORMAL);
        try {
            Class<ForgeEventExecutorContainer> c = ForgeEventExecutorContainer.class;
            for (Field field : c.getFields()) {
                Type t = field.getGenericType();
                if (t instanceof ParameterizedType) {
                    eventExecutorMap.put(((ParameterizedType) t).getActualTypeArguments()[0], (EventExecutor) field.get(null));
                }
            }
        } catch (IllegalAccessException e) {
            PsExtras.getInstance().getLogger().severe(e.toString());
        }
    }
    
    @IForgeListenHandler.SubscribeEvent
    public void onForge(Event event) {
        if (this.eventExecutorMap.containsKey(event.getClass())) {
            this.eventExecutorMap.get(event.getClass()).run(event);
        }
    }
}