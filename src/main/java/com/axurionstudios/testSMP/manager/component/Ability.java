package com.axurionstudios.testSMP.manager.component;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public abstract class Ability {
    protected boolean active = false;

    public abstract Component getName();
    public abstract String getIdentifier();
    public abstract String getId();
    public abstract String getUnicode();

    public abstract int getCooldown();
    public abstract Slot getSlot();
    public abstract void activate(Player player);

    public void setActive(boolean active){
        this.active = active;
    }

    public boolean isActive(){
        return active;
    }

    public enum Slot {
        PRIMARY, SECONDARY
    }
}
