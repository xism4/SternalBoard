package com.xism4.sternalboard.listener.module;

import com.xism4.sternalboard.listener.ScoreboardListener;
import org.bukkit.event.Listener;
import team.unnamed.inject.AbstractModule;

public class ListenerModule extends AbstractModule {

    @Override
    protected void configure() {
        super.multibind(Listener.class).asSet().to(ScoreboardListener.class).singleton();
    }
}
