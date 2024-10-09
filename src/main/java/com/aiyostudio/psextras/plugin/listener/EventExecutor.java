package com.aiyostudio.psextras.plugin.listener;

@FunctionalInterface
public interface EventExecutor<T> {
    
    void run(T t);
}