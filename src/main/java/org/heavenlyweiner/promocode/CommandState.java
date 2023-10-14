package org.heavenlyweiner.promocode;

import org.bukkit.entity.Player;

public class CommandState {
    private boolean used;

    public CommandState() {
        this.used = false;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}