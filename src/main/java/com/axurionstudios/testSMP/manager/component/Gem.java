package com.axurionstudios.testSMP.manager.component;

import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class Gem {
    public abstract Component getName();
    public abstract String getIdentifier();
    public abstract List<Ability> getAbilities();
    public abstract int getCustomModelData();
    public abstract ItemStack getItem();
}
