package com.axurionstudios.testSMP;

import co.aikar.commands.PaperCommandManager;
import com.axurionstudios.testSMP.manager.CooldownManager;
import com.axurionstudios.testSMP.manager.GemManager;
import com.axurionstudios.testSMP.storage.DataManager;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("LombokGetterMayBeUsed")
public final class TestSMP extends JavaPlugin {

    @Getter private static TestSMP instance;
    @Getter private static DataManager dataManager;
    @Getter private static CooldownManager cooldownManager;
    @Getter private static GemManager gemManager;

    private PaperCommandManager commandManager;

    public static TestSMP getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(Component.text("Enabled!", NamedTextColor.GREEN));
        instance = this;
        dataManager = new DataManager(this);
        cooldownManager = new CooldownManager(this);
        gemManager = new GemManager(cooldownManager);

        cooldownManager.setGemManager(gemManager);
        commandManager = new PaperCommandManager(this);

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        dataManager.initializePlayerDataConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
