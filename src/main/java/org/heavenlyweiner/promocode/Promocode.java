package org.heavenlyweiner.promocode;

import org.bukkit.plugin.java.JavaPlugin;

public final class Promocode extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getCommand("happystart").setExecutor(new Promocodecmd());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
