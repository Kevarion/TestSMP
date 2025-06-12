package com.axurionstudios.testSMP.manager;

import com.axurionstudios.testSMP.TestSMP;
import com.axurionstudios.testSMP.manager.component.Ability;
import com.axurionstudios.testSMP.manager.component.Gem;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class GemManager {

    @Getter private static final Map<String, Gem> registeredGems = new HashMap<>();
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

    public Collection<Gem> getAllGems(){
        return registeredGems.values();
    }

    public static Gem getGemByIdentifier(String id){
        return registeredGems.get(id);
    }

    public boolean hasSelectedGem(Player player, String id){
        String selectedGemId = TestSMP.getDataManager().getPlayerDataConfig().getString("players." + player.getUniqueId() + ".gem");
        return selectedGemId != null && selectedGemId.equals(id);
    }

    public Map<Ability.Slot, Ability> getGemAbilities(String id){
        Gem gem = getGemByIdentifier(id);
        if(gem == null) return null;

        Map<Ability.Slot, Ability> abilitiesMap = new HashMap<>();
        for (Ability ability : gem.getAbilities()) {
            abilitiesMap.put(ability.getSlot(), ability);
        }
        return abilitiesMap;
    }

    public static Collection<Ability> getAllAbilities(){
        return registeredGems.values().stream()
                .flatMap(gem -> gem.getAbilities().stream())
                .collect(Collectors.toList());
    }

}
