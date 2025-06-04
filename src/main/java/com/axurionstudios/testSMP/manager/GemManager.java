package com.axurionstudios.testSMP.manager;

import com.axurionstudios.testSMP.TestSMP;
import com.axurionstudios.testSMP.manager.component.Gem;

import java.util.HashMap;
import java.util.Map;

public class GemManager {

    private static final Map<String, Gem> registeredGems = new HashMap<>();
    private final CooldownManager cooldownManager;

    public GemManager(CooldownManager cooldownManager) {
        this.cooldownManager = cooldownManager;
    }

    public void registerAvailableGems(){

    }

    public void registerGem(Gem gem){
        if (registeredGems.containsKey(gem.getIdentifier())) {
            TestSMP.getInstance().getLogger().warning("Gem " + gem.getIdentifier() + " is already registered!");
            return;
        }
        registeredGems.put(gem.getIdentifier(), gem);
        TestSMP.getDataManager().getPlayerDataConfig().set("gems." + gem.getIdentifier(), gem.getIdentifier());
        TestSMP.getDataManager().saveDataConfig();
    }


}
