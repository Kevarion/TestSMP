package com.axurionstudios.testSMP.manager;

import com.axurionstudios.testSMP.TestSMP;
import com.axurionstudios.testSMP.manager.component.Ability;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {

    private final TestSMP instance;
    @Setter private GemManager gemManager;
    private final Map<UUID, Map<String, Long>> playerCooldowns = new HashMap<>();
    private final Map<UUID, BukkitTask> actionBarTasks = new HashMap<>();

    public CooldownManager(TestSMP instance){
        this.instance = instance;
        this.gemManager = TestSMP.getGemManager();
        loadCooldowns();
    }

    public boolean isOnCooldown(Player player, Ability ability){
        Map<String, Long> cooldowns = playerCooldowns.get(player.getUniqueId());
        if(cooldowns == null || !cooldowns.containsKey(ability.getName().toString())) return false;

        long cooldownEndTime = cooldowns.get(ability.getName().toString());
        return System.currentTimeMillis() < cooldownEndTime;
    }

    public int getRemainingCooldown(Player player, Ability ability){
        Map<String, Long> cooldowns = playerCooldowns.get(player.getUniqueId());
        if(cooldowns == null || !cooldowns.containsKey(ability.getName().toString())) return 0;

        long cooldownEndTime = cooldowns.get(ability.getName().toString());
        long remainingTime = cooldownEndTime - System.currentTimeMillis();
        return (int) (remainingTime / 1000L);
    }

    public void resetPlayerCooldown(Player player){
        if(playerCooldowns.containsKey(player.getUniqueId())) {
            playerCooldowns.remove(player.getUniqueId());
        }
    }

    public void startCooldown(Player player, Ability ability){
        playerCooldowns.putIfAbsent(player.getUniqueId(), new HashMap<>());
        long cooldownEndTime = System.currentTimeMillis() + (ability.getCooldown() * 1000L);
        playerCooldowns.get(player.getUniqueId()).put(ability.getName().toString(), cooldownEndTime);

        savePlayerCooldown(player.getUniqueId(), ability.getName().toString(), cooldownEndTime);
    }

    public void savePlayerCooldown(UUID playerUUID, String abilityName, long cooldownEndTime){
        TestSMP.getDataManager().getPlayerDataConfig().set("players." + playerUUID + ".cooldowns." + abilityName, cooldownEndTime);
        TestSMP.getDataManager().saveDataConfig();
    }

    public void loadCooldowns(){
        ConfigurationSection playersSection = TestSMP.getDataManager().getPlayerDataConfig().getConfigurationSection("players");
        if(playersSection == null) return;

        for (String playerId : playersSection.getKeys(false)){
            UUID playerUUID = UUID.fromString(playerId);
            ConfigurationSection cooldownSection = playersSection.getConfigurationSection(playerId + ".cooldowns");
            if(cooldownSection != null) {
                Map<String, Long> playerCooldownMap = new HashMap<>();
                for (String abilityNameStr : cooldownSection.getKeys(false)) {
                    long cooldown = cooldownSection.getLong(abilityNameStr);
                    playerCooldownMap.put(abilityNameStr, cooldown);
                }
                playerCooldowns.put(playerUUID, playerCooldownMap);
            }
        }
    }
}
